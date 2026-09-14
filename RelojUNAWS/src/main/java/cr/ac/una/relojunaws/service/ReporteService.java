package cr.ac.una.relojunaws.service;

import cr.ac.una.relojunaws.model.dto.JornadaDTO;
import cr.ac.una.relojunaws.model.dto.JornadaListDTO;
import cr.ac.una.relojunaws.model.dto.ResumenMarcasDTO;
import cr.ac.una.relojunaws.util.Respuesta;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import cr.ac.una.relojunaws.model.dto.ArchivoDTO;
import cr.ac.una.relojunaws.model.dto.EmpleadoListDTO;
import java.io.InputStream;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.util.ArrayList;
import java.util.Comparator;

@Stateless
@LocalBean
public class ReporteService {

    @EJB
    private MarcaService marcaService;//necesito reutilizar metodos de marca
    @EJB
    private EmpleadoService empleadoService;
    private static final Logger LOG = Logger.getLogger(ReporteService.class.getName());

    public Respuesta generarExcelMarcas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        try {
            Respuesta respuestaJornadas = marcaService.obtenerJornadas(desde, hasta, folioEmpleado);
            if (!respuestaJornadas.getEstado()) {
                return respuestaJornadas;
            }
            JornadaListDTO jornadasDTO = (JornadaListDTO) respuestaJornadas.getResultado();
            Respuesta respuestaResumen = marcaService.consultarResumen(desde, hasta, folioEmpleado);
            if (!respuestaResumen.getEstado()) {
                return respuestaResumen;
            }
            ResumenMarcasDTO resumen = (ResumenMarcasDTO) respuestaResumen.getResultado("ResumenMarcas");
            byte[] contenido = crearExcelMarcas(jornadasDTO, resumen, desde, hasta, folioEmpleado);

            String nombreArchivo
                    = "ConsultaMarcas_"
                    + desde
                    + "_"
                    + hasta
                    + ".xlsx";

            ArchivoDTO archivo = new ArchivoDTO(
                    nombreArchivo, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", contenido);
            return new Respuesta(true, "reporte.excel.exito", "", "Archivo", archivo);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al generar el Excel de marcas", ex);
            return new Respuesta(false, "reporte.excel.error", "generarExcelMarcas Exception " + ex.getMessage()
            );
        }

    }

    private byte[] crearExcelMarcas(JornadaListDTO jornadasDTO, ResumenMarcasDTO resumen,
            LocalDate desde, LocalDate hasta, String folioEmpleado) throws Exception {
        try (XSSFWorkbook libroTrabajo = new XSSFWorkbook(); ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            Sheet hoja = libroTrabajo.createSheet("Consulta de marcas");

            Font fuenteTitulo = libroTrabajo.createFont();
            fuenteTitulo.setBold(true);
            fuenteTitulo.setFontHeightInPoints((short) 14);

            CellStyle estiloTitulo = libroTrabajo.createCellStyle();
            estiloTitulo.setFont(fuenteTitulo);
            Font fuenteEncabezado = libroTrabajo.createFont();
            fuenteEncabezado.setBold(true);
            CellStyle estiloEncabezado = libroTrabajo.createCellStyle();
            estiloEncabezado.setFont(fuenteEncabezado);
            int filaActual = 0;

            Row filaTitulo = hoja.createRow(filaActual++);
            Cell celdaTitulo = filaTitulo.createCell(0);
            celdaTitulo.setCellValue("Consulta y exportación de marcas");
            celdaTitulo.setCellStyle(estiloTitulo);
            filaActual++;

            Row filaDesde = hoja.createRow(filaActual++);
            filaDesde.createCell(0).setCellValue("Fecha inicial");
            filaDesde.createCell(1).setCellValue(desde != null ? desde.toString() : "");

            Row filaHasta = hoja.createRow(filaActual++);
            filaHasta.createCell(0).setCellValue("Fecha final");
            filaHasta.createCell(1).setCellValue(hasta != null ? hasta.toString() : "");

            Row filaFolio = hoja.createRow(filaActual++);
            filaFolio.createCell(0).setCellValue("Folio");
            filaFolio.createCell(1).setCellValue(folioEmpleado == null || folioEmpleado.isBlank()
                    ? "Todos" : folioEmpleado.trim());
            filaActual++;

            Row filaEmpleados = hoja.createRow(filaActual++);
            filaEmpleados.createCell(0).setCellValue("Empleados que marcaron");
            filaEmpleados.createCell(1).setCellValue(resumen.getCantidadEmpleados());

            Row filaMarcas = hoja.createRow(filaActual++);
            filaMarcas.createCell(0).setCellValue("Total de marcas");
            filaMarcas.createCell(1).setCellValue(resumen.getTotalMarcas());

            Row filaHoras = hoja.createRow(filaActual++);
            filaHoras.createCell(0).setCellValue("Total de horas trabajadas");
            filaHoras.createCell(1).setCellValue(resumen.getTotalHorasTrabajadas() + " h "
                    + resumen.getTotalMinutosTrabajados() + " min");
            filaActual++;
            Row encabezado = hoja.createRow(filaActual++);

            String[] columnas = {
                "Fecha",
                "Folio",
                "Empleado",
                "Entrada",
                "Salida",
                "Horas",
                "Estado"
            };
            for (int i = 0; i < columnas.length; i++) {
                Cell celda = encabezado.createCell(i);
                celda.setCellValue(columnas[i]);
                celda.setCellStyle(estiloEncabezado);
            }
            List<JornadaDTO> jornadas = jornadasDTO.getJornadas();
            if (jornadas != null) {
                for (JornadaDTO jornada : jornadas) {
                    Row fila = hoja.createRow(filaActual++);
                    fila.createCell(0).setCellValue(valorSeguro(jornada.getFecha()));
                    fila.createCell(1).setCellValue(valorSeguro(jornada.getFolioEmpleado()));
                    fila.createCell(2).setCellValue(valorSeguro(jornada.getNombreEmpleado()));
                    fila.createCell(3).setCellValue(obtenerHoraEntrada(jornada));
                    fila.createCell(4).setCellValue(obtenerHoraSalida(jornada));
                    Cell celdaHoras = fila.createCell(5);
                    if (jornada.getHorasTrabajadas() != null) {
                        celdaHoras.setCellValue(jornada.getHorasTrabajadas());
                    } else {
                        celdaHoras.setCellValue("-");
                    }
                    fila.createCell(6).setCellValue(
                            Boolean.TRUE.equals(jornada.getCompleta())
                            ? "Completa"
                            : "Incompleta"
                    );
                }
            }
            for (int i = 0; i < columnas.length; i++) {
                hoja.autoSizeColumn(i);
            }
            libroTrabajo.write(salida);
            return salida.toByteArray();
        }
    }

    private String obtenerHoraEntrada(
            JornadaDTO jornada) {
        if (jornada.getMarcaEntrada() == null || jornada.getMarcaEntrada().getFechaHora() == null) {
            return "-";
        }
        return jornada.getMarcaEntrada().getFechaHora().toLocalTime().toString();
    }

    private String obtenerHoraSalida(JornadaDTO jornada) {
        if (jornada.getMarcaSalida() == null || jornada.getMarcaSalida().getFechaHora() == null) {
            return "-";
        }

        return jornada.getMarcaSalida()
                .getFechaHora()
                .toLocalTime()
                .toString();
    }

    private String valorSeguro(Object valor) {
        return valor == null ? "-" : valor.toString();
    }

    public Respuesta generarReporteEmpleados() {
        try {
            Respuesta respuestaEmpleados = empleadoService.getEmpleados();
            if (!respuestaEmpleados.getEstado()) {
                return respuestaEmpleados;
            }

            EmpleadoListDTO empleadosDTO = (EmpleadoListDTO) respuestaEmpleados.getResultado();
            if (empleadosDTO == null || empleadosDTO.getEmpleados() == null) {
                return new Respuesta(false, "reporte.empleados.error", "No se obtuvo la lista de empleados");
            }
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleadosDTO.getEmpleados());
            InputStream archivoJrxml = ReporteService.class.getResourceAsStream("/reportes/ReporteEmpleados.jrxml");

            if (archivoJrxml == null) {
                return new Respuesta(false, "reporte.empleados.error", "No se encontró ReporteEmpleados.jrxml");
            }
            JasperReport reporte = JasperCompileManager.compileReport(archivoJrxml);
            JasperPrint impresion = JasperFillManager.fillReport(reporte, new HashMap<>(), dataSource);

            byte[] contenido = JasperExportManager.exportReportToPdf(impresion);

            ArchivoDTO archivo = new ArchivoDTO("ReporteEmpleados.pdf", "application/pdf", contenido);
            return new Respuesta(true, "reporte.empleados.exito", "", "Archivo", archivo);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al generar " + "el reporte de empleados", ex);
            return new Respuesta(false, "reporte.empleados.error", "generarReporteEmpleados Exception " + ex.getMessage());
        }
    }

    public Respuesta generarReporteMarcas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        try {
            Respuesta respuestaJornadas = marcaService.obtenerJornadas(desde, hasta, folioEmpleado);
            if (!respuestaJornadas.getEstado()) {
                return respuestaJornadas;
            }
            JornadaListDTO jornadasDTO = (JornadaListDTO) respuestaJornadas.getResultado();
            if (jornadasDTO == null || jornadasDTO.getJornadas() == null) {
                return new Respuesta(false, "reporte.marcas.error", "No se obtuvo la lista de jornadas");
            }

            //1-necesito obtener las jornads,ordenar folio y rango de fechas
            List<JornadaDTO> jornadas = new ArrayList<>(jornadasDTO.getJornadas());
            Comparator<LocalDate> ordenarFecha = Comparator.nullsLast(Comparator.naturalOrder());
            Comparator<String> ordenarFolio = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);
            jornadas.sort(Comparator.comparing(JornadaDTO::getFolioEmpleado,ordenarFolio).thenComparing(JornadaDTO::getFecha,ordenarFecha));
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jornadas);
            
            InputStream archivoJrxml = ReporteService.class.getResourceAsStream("/reportes/ReporteMarcas.jrxml");
            if (archivoJrxml == null) {
                return new Respuesta(false, "reporte.marcas.error", "No se encontró ReporteMarcas.jrxml");
            }
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("FECHA_INICIO", desde != null ? desde.toString() : "");
            parametros.put("FECHA_FIN", hasta != null ? hasta.toString() : "");
            parametros.put("FOLIO", folioEmpleado == null || folioEmpleado.isBlank() ? "Todos" : folioEmpleado.trim());
            JasperReport reporte = JasperCompileManager.compileReport(archivoJrxml);
            JasperPrint impresion = JasperFillManager.fillReport(reporte, parametros, dataSource);
            byte[] contenido = JasperExportManager.exportReportToPdf(impresion);
            String nombreArchivo = "ReporteMarcas_" + desde + "_" + hasta + ".pdf";
            ArchivoDTO archivo = new ArchivoDTO(nombreArchivo, "application/pdf", contenido);
            return new Respuesta(true, "reporte.marcas.exito", "", "Archivo", archivo);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al generar " + "el reporte de marcas", ex);
            return new Respuesta(false, "reporte.marcas.error", "generarReporteMarcas Exception " + ex.getMessage());
        }
    }
}
