package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.RelojUNASOAP;
import cr.ac.una.relojuna.ws.RelojUNASOAPService;
import cr.ac.una.relojuna.ws.SOAPResponse;
import jakarta.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import cr.ac.una.relojuna.ws.JornadaDTO;
import cr.ac.una.relojuna.ws.JornadaListDTO;
import java.util.List;

public class ConsultasService {

    private static final Logger LOG = Logger.getLogger(ConsultasService.class.getName());

    public Respuesta consultarResumen(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        Respuesta validacion = validarFechas(desde, hasta);
        if (validacion != null) {
            return validacion;
        }
        try {
            RelojUNASOAP port = crearPuerto();
            SOAPResponse resultado = port.consultarResumen(desde.toString(), hasta.toString(), normalizarFolio(folioEmpleado));
            if (!resultado.isExito()) {
                return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
            }
            return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "ResumenMarcas", resultado.getResultado());
        } catch (WebServiceException ex) {
            LOG.log(Level.SEVERE, "Error al consultar el resumen", ex);
            return new Respuesta(false, "No se pudo consultar el resumen. " + "Verifique la conexión con el servidor.", ex.toString());
        }
    }

    public Respuesta obtenerJornadas(LocalDate desde, LocalDate hasta, String folioEmpleado) {
        Respuesta validacion = validarFechas(desde, hasta);
        if (validacion != null) {
            return validacion;
        }
        try {
            RelojUNASOAP port = crearPuerto();
            SOAPResponse resultado = port.obtenerJornadas(desde.toString(), hasta.toString(), normalizarFolio(folioEmpleado));
            if (!resultado.isExito()) {
                return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
            }

            if (!(resultado.getResultado() instanceof JornadaListDTO jornadasDTO)) {
                return new Respuesta(false, "El servidor no devolvió las jornadas esperadas.",
                        "obtenerJornadas: resultado nulo o de tipo incorrecto");
            }
            List<JornadaDTO> jornadas = jornadasDTO.getJornadas();
            return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Jornadas", jornadas);
        } catch (WebServiceException ex) {
            LOG.log(Level.SEVERE, "Error al obtener las jornadas", ex);
            return new Respuesta(false, "No se pudieron obtener las jornadas. " + "Verifique la conexión con el servidor.", ex.toString());
        }
    }

    private RelojUNASOAP crearPuerto() {
        RelojUNASOAPService service = new RelojUNASOAPService();
        return service.getRelojUNASOAPPort();
    }

    private String normalizarFolio(String folioEmpleado) {
        return folioEmpleado == null || folioEmpleado.isBlank() ? null : folioEmpleado.trim();
    }

    private Respuesta validarFechas(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) {
            return new Respuesta(false, "Por favor debe seleccionar la fecha inicial y la fecha final.", "ReporteService: fechas incompletas");
        }
        if (hasta.isBefore(desde)) {
            return new Respuesta(false, "Recuerde que la fecha final no puede ser anterior a la inicial.", "ReporteService: rango de fechas inválido");
        }
        return null;
    }
}
