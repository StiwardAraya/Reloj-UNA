package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.DashboardDTO;
import cr.ac.una.relojuna.ws.RelojUNASOAP;
import cr.ac.una.relojuna.ws.RelojUNASOAPService;
import cr.ac.una.relojuna.ws.SOAPResponse;
import jakarta.xml.ws.WebServiceException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardService {

    private static final Logger LOG
            = Logger.getLogger(DashboardService.class.getName());

    private final RelojUNASOAP port;

    public DashboardService() {
        RelojUNASOAPService service = new RelojUNASOAPService();
        port = service.getRelojUNASOAPPort();
    }
    public Respuesta getDashboard() {
        try {
            SOAPResponse resultado = port.getDashboard();
            if (resultado == null) {
                return new Respuesta( false, "El servidor no devolvió una respuesta.", "getDashboard: SOAPResponse nulo" );
            }
            if (!resultado.isExito()) {
                return new Respuesta( false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico() );
            }
            DashboardDTO dashboard = (DashboardDTO) resultado.getResultado();
            return new Respuesta( true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(),  "Dashboard", dashboard  );
        } catch (WebServiceException ex) {
            LOG.log(Level.SEVERE,  "Error al consultar el dashboard.", ex);
            return new Respuesta( false, "No se pudo consultar el dashboard.", ex.toString() );
        } catch (ClassCastException ex) {
            LOG.log(Level.SEVERE, "Tipo de respuesta incorrecto en getDashboard.", ex);
            return new Respuesta( false,   "El servidor devolvió una respuesta no válida.", ex.toString() );
        }
    }
}