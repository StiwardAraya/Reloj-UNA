package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.ArchivoDTO;
import cr.ac.una.relojuna.ws.ArchivoResponse;
import cr.ac.una.relojuna.ws.RelojUNASOAP;
import cr.ac.una.relojuna.ws.RelojUNASOAPService;
import jakarta.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tames
 */
public class ReporteService {

    private static final Logger LOG = Logger.getLogger(ReporteService.class.getName());

    private final RelojUNASOAP port;

    public ReporteService() {
        RelojUNASOAPService service = new RelojUNASOAPService();
        port = service.getRelojUNASOAPPort();
    }

    public Respuesta obtenerReporteEmpleados() {
        try {
            ArchivoResponse respuesta = port.generarReporteEmpleados();
            if (respuesta == null) {
                return new Respuesta(false, "reporte.empleados.error", "La respuesta SOAP fue nula");
            }
            if (!respuesta.isExito()) {
                return new Respuesta(false, respuesta.getMensajeUsuario(), respuesta.getMensajeTecnico());
            }
            ArchivoDTO archivo = respuesta.getResultado();
            if (archivo == null || archivo.getContenido() == null || archivo.getContenido().length == 0) {
                return new Respuesta(false, "reporte.empleados.error", "El archivo PDF recibido está vacío");
            }
            return new Respuesta(true, respuesta.getMensajeUsuario(), respuesta.getMensajeTecnico(), "Archivo", archivo);
        } catch (WebServiceException ex) {
            LOG.log(Level.SEVERE, "Error al obtener el reporte de empleados", ex);
            return new Respuesta(false, "reporte.empleados.error", ex.toString());
        }
    }

    public Respuesta obtenerReporteMarcas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        try {

            ArchivoResponse respuesta = port.generarReporteMarcas(
                    desde != null ? desde.toString() : null,
                    hasta != null ? hasta.toString() : null, folioEmpleado);
            if (respuesta == null) {
                return new Respuesta(false, "reporte.marcas.error", "La respuesta SOAP fue nula");
            }
            if (!respuesta.isExito()) {
                return new Respuesta(false, respuesta.getMensajeUsuario(), respuesta.getMensajeTecnico());
            }
            ArchivoDTO archivo = respuesta.getResultado();
            if (archivo == null || archivo.getContenido() == null || archivo.getContenido().length == 0) {
                return new Respuesta(false, "reporte.marcas.error", "El archivo PDF recibido está vacío");
            }
            return new Respuesta(true, respuesta.getMensajeUsuario(), respuesta.getMensajeTecnico(), "Archivo", archivo);
        } catch (WebServiceException ex) {
            LOG.log(Level.SEVERE, "Error al obtener el reporte de marcas", ex);
            return new Respuesta(false, "reporte.marcas.error", ex.toString());
        }
    }
}
