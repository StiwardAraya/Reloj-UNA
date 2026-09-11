/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.ws.JornadaDTO;
import cr.ac.una.relojuna.ws.ResumenMarcasDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Tames
 */
public class ExcelService {
    
    public void generarExcel(File archivo, List<JornadaDTO> jornadas, LocalDate desde, 
            LocalDate hasta, String folio, ResumenMarcasDTO resumen) throws IOException {
        
        try (Workbook libroEXCEL = new XSSFWorkbook();
             FileOutputStream salida = new FileOutputStream(archivo)) {

            Sheet hoja = libroEXCEL.createSheet("Consulta de marcas");

            CellStyle estiloTitulo = crearEstiloTitulo(libroEXCEL);
            CellStyle estiloEncabezado = crearEstiloEncabezado(libroEXCEL);
            CellStyle estiloTexto = crearEstiloTexto(libroEXCEL);
            CellStyle estiloHoras = crearEstiloHoras(libroEXCEL);
            CellStyle estiloResumen = crearEstiloResumen(libroEXCEL);

            int numeroFila = 0;

            // Título principal
            Row filaTitulo = hoja.createRow(numeroFila++);
            filaTitulo.setHeightInPoints(28);

            Cell celdaTitulo = filaTitulo.createCell(0);
            celdaTitulo.setCellValue(
                    "Universidad Nacional - Consulta de marcas"
            );
            celdaTitulo.setCellStyle(estiloTitulo);

            hoja.addMergedRegion(
                    new CellRangeAddress(0, 0, 0, 6)
            );

            // Filtros utilizados
            crearFilaInformacion(
                    hoja,
                    numeroFila++,
                    "Fecha inicial:",
                    desde.toString(),
                    estiloTexto
            );

            crearFilaInformacion(
                    hoja,
                    numeroFila++,
                    "Fecha final:",
                    hasta.toString(),
                    estiloTexto
            );

            String empleadoConsultado =
                    folio == null || folio.isBlank()
                    ? "Todos los empleados"
                    : folio.trim();

            crearFilaInformacion(
                    hoja,
                    numeroFila++,
                    "Empleado:",
                    empleadoConsultado,
                    estiloTexto
            );

            numeroFila++;

            // Encabezados de la tabla
            int filaEncabezado = numeroFila;

            Row encabezado = hoja.createRow(numeroFila++);
            String[] columnas = {
                "Fecha",
                "Folio",
                "Empleado",
                "Entrada",
                "Salida",
                "Horas trabajadas",
                "Estado"
            };

            for (int columna = 0; columna < columnas.length; columna++) {
                Cell celda = encabezado.createCell(columna);
                celda.setCellValue(columnas[columna]);
                celda.setCellStyle(estiloEncabezado);
            }

            // Contenido de las jornadas
            for (JornadaDTO jornada : jornadas) {
                Row fila = hoja.createRow(numeroFila++);

                crearCelda(
                        fila,
                        0,
                        valorSeguro(jornada.getFecha()),
                        estiloTexto
                );

                crearCelda(
                        fila,
                        1,
                        valorSeguro(jornada.getFolioEmpleado()),
                        estiloTexto
                );

                crearCelda(
                        fila,
                        2,
                        valorSeguro(jornada.getNombreEmpleado()),
                        estiloTexto
                );

                crearCelda(
                        fila,
                        3,
                        obtenerHoraEntrada(jornada),
                        estiloTexto
                );

                crearCelda(
                        fila,
                        4,
                        obtenerHoraSalida(jornada),
                        estiloTexto
                );

                Cell celdaHoras = fila.createCell(5);
                if (jornada.getHorasTrabajadas() != null) {
                    celdaHoras.setCellValue(
                            jornada.getHorasTrabajadas()
                    );
                } else {
                    celdaHoras.setCellValue("-");
                }
                celdaHoras.setCellStyle(estiloHoras);

                crearCelda(
                        fila,
                        6,
                        Boolean.TRUE.equals(jornada.isCompleta())
                        ? "Completa"
                        : "Incompleta",
                        estiloTexto
                );
            }

            // Filtro automático en los encabezados
            if (!jornadas.isEmpty()) {
                hoja.setAutoFilter(
                        new CellRangeAddress(
                                filaEncabezado,
                                numeroFila - 1,
                                0,
                                6
                        )
                );
            }

            numeroFila++;

            // Resumen final
            crearFilaResumen(
                    hoja,
                    numeroFila++,
                    "Cantidad de empleados:",
                    String.valueOf(resumen.getCantidadEmpleados()),
                    estiloResumen
            );

            crearFilaResumen(
                    hoja,
                    numeroFila++,
                    "Total de marcas:",
                    String.valueOf(resumen.getTotalMarcas()),
                    estiloResumen
            );

            crearFilaResumen(
                    hoja,
                    numeroFila,
                    "Total de horas:",
                    resumen.getTotalHorasTrabajadas()
                    + " h "
                    + resumen.getTotalMinutosTrabajados()
                    + " min",
                    estiloResumen
            );

            // Mantiene visibles los encabezados al desplazarse
            hoja.createFreezePane(0, filaEncabezado + 1);

            // Ajusta automáticamente el ancho de las columnas
            for (int columna = 0; columna < columnas.length; columna++) {
                hoja.autoSizeColumn(columna);
                hoja.setColumnWidth(
                        columna,
                        Math.min(
                                hoja.getColumnWidth(columna) + 1200,
                                15000
                        )
                );
            }

            libroEXCEL.write(salida);
        }
    }

    private void crearFilaInformacion(
            Sheet hoja,
            int numeroFila,
            String titulo,
            String valor,
            CellStyle estilo) {

        Row fila = hoja.createRow(numeroFila);

        crearCelda(fila, 0, titulo, estilo);
        crearCelda(fila, 1, valor, estilo);
    }

    private void crearFilaResumen(
            Sheet hoja,
            int numeroFila,
            String titulo,
            String valor,
            CellStyle estilo) {

        Row fila = hoja.createRow(numeroFila);

        crearCelda(fila, 0, titulo, estilo);
        crearCelda(fila, 1, valor, estilo);
    }

    private void crearCelda(
            Row fila,
            int columna,
            String valor,
            CellStyle estilo) {

        Cell celda = fila.createCell(columna);
        celda.setCellValue(valor);
        celda.setCellStyle(estilo);
    }

    private CellStyle crearEstiloTitulo(Workbook libro) {
        CellStyle estilo = libro.createCellStyle();

        Font fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeightInPoints((short) 16);
        fuente.setColor(IndexedColors.WHITE.getIndex());

        estilo.setFont(fuente);
        estilo.setFillForegroundColor(
                IndexedColors.DARK_BLUE.getIndex()
        );
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);

        return estilo;
    }

    private CellStyle crearEstiloEncabezado(Workbook libro) {
        CellStyle estilo = libro.createCellStyle();

        Font fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setColor(IndexedColors.WHITE.getIndex());

        estilo.setFont(fuente);
        estilo.setFillForegroundColor(
                IndexedColors.BLUE.getIndex()
        );
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);
        agregarBordes(estilo);

        return estilo;
    }

    private CellStyle crearEstiloTexto(Workbook libro) {
        CellStyle estilo = libro.createCellStyle();

        estilo.setVerticalAlignment(VerticalAlignment.CENTER);
        agregarBordes(estilo);

        return estilo;
    }

    private CellStyle crearEstiloHoras(Workbook libro) {
        CellStyle estilo = crearEstiloTexto(libro);

        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setDataFormat(
                libro.createDataFormat().getFormat("0.00")
        );

        return estilo;
    }

    private CellStyle crearEstiloResumen(Workbook libro) {
        CellStyle estilo = libro.createCellStyle();

        Font fuente = libro.createFont();
        fuente.setBold(true);

        estilo.setFont(fuente);
        estilo.setFillForegroundColor(
                IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex()
        );
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        agregarBordes(estilo);

        return estilo;
    }

    private void agregarBordes(CellStyle estilo) {
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
    }

    private String obtenerHoraEntrada(JornadaDTO jornada) {
        if (jornada.getMarcaEntrada() == null) {
            return "-";
        }

        return extraerHora(
                jornada.getMarcaEntrada().getFechaHora()
        );
    }

    private String obtenerHoraSalida(JornadaDTO jornada) {
        if (jornada.getMarcaSalida() == null) {
            return "-";
        }

        return extraerHora(
                jornada.getMarcaSalida().getFechaHora()
        );
    }

    private String extraerHora(String fechaHora) {
        if (fechaHora == null || fechaHora.isBlank()) {
            return "-";
        }

        if (fechaHora.length() >= 16 && fechaHora.contains("T")) {
            return fechaHora.substring(11, 16);
        }

        return fechaHora;
    }

    private String valorSeguro(Object valor) {
        return valor == null ? "-" : valor.toString();
    }
}

