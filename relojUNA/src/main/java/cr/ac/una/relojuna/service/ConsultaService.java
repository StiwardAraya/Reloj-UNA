package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.JornadaListDTO;
import cr.ac.una.relojuna.ws.ResumenMarcasDTO;
import cr.ac.una.relojuna.ws.RelojUNASOAP;
import cr.ac.una.relojuna.ws.RelojUNASOAPService;
import cr.ac.una.relojuna.ws.SOAPResponse;
import jakarta.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import cr.ac.una.relojuna.ws.ArchivoDTO;
import cr.ac.una.relojuna.ws.ArchivoResponse;

public class ConsultaService {

    private static final Logger LOG = Logger.getLogger(ConsultaService.class.getName());

    private final RelojUNASOAP port;

    public ConsultaService() {
        RelojUNASOAPService service = new RelojUNASOAPService();
        port = service.getRelojUNASOAPPort();
    }

    public Respuesta consultarResumen(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        try {
            SOAPResponse resultado = port.consultarResumen(
                    desde != null ? desde.toString() : null,
                    hasta != null ? hasta.toString() : null,
                    folioEmpleado
            );
            if (resultado == null) {
                return new Respuesta(false, "El servidor no devolvió una respuesta.", "consultarResumen: SOAPResponse nulo");
            }
            if (!resultado.isExito()) {
                return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
            }
            ResumenMarcasDTO resumen = (ResumenMarcasDTO) resultado.getResultado();
            return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "ResumenMarcas", resumen);

        } catch (WebServiceException ex) {
            LOG.log(Level.SEVERE, "Error al consultar resumen", ex);
            return new Respuesta(false, "No se pudo consultar el resumen.", ex.toString());
        } catch (ClassCastException ex) {
            LOG.log(Level.SEVERE, "Tipo de respuesta incorrecto en consultarResumen", ex);
            return new Respuesta(false, "El servidor devolvió una respuesta no válida.", ex.toString());
        }
    }

    public Respuesta obtenerJornadas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        try {
            SOAPResponse resultado = port.obtenerJornadas(desde != null ? desde.toString() : null,
                    hasta != null ? hasta.toString() : null, folioEmpleado);

            if (resultado == null) {
                return new Respuesta(false, "El servidor no devolvió una respuesta.", "consultarResumen: SOAPResponse nulo");
            }
            if (!resultado.isExito()) {
                return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
            }
            JornadaListDTO jornadas = (JornadaListDTO) resultado.getResultado();
            return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Jornadas", jornadas);

        } catch (WebServiceException ex) {
            LOG.log(Level.SEVERE, "Error al obtener jornadas", ex);
            return new Respuesta(false, "No se pudieron obtener las jornadas.", ex.toString());
        } catch (ClassCastException ex) {
            LOG.log(Level.SEVERE, "Tipo de respuesta incorrecto en obtenerJornadas", ex);
            return new Respuesta(false, "El servidor devolvió una respuesta no válida.", ex.toString());
        }
    }

    public Respuesta obtenerExcelMarcas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        try {
            ArchivoResponse respuesta = port.generarExcelMarcas(desde != null ? desde.toString() : null, hasta != null ? hasta.toString() : null, folioEmpleado);
            if (!respuesta.isExito()) {
                return new Respuesta(false, respuesta.getMensajeUsuario(), respuesta.getMensajeTecnico());
            }
            ArchivoDTO archivo = respuesta.getResultado();

            if (archivo == null || archivo.getContenido() == null) {
                return new Respuesta(false, "No se recibió el archivo Excel.", "generarExcelMarcas resultado nulo");
            }
            return new Respuesta(true, respuesta.getMensajeUsuario(), respuesta.getMensajeTecnico(), "Archivo", archivo);
        } catch (WebServiceException ex) {
            LOG.log(Level.SEVERE, "Error al solicitar el Excel de marcas", ex);
            return new Respuesta(false, "No se pudo generar el Excel. " + "Verifique la conexión con el servidor.", ex.toString());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error inesperado al solicitar el Excel", ex);
            return new Respuesta(false, "No se pudo generar el Excel.", ex.toString());
        }
    }
}
