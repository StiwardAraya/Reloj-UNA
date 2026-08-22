package cr.ac.una.relojunaws.controller;

import cr.ac.una.relojunaws.model.dto.EmpleadoDTO;
import cr.ac.una.relojunaws.model.dto.LoginRequestDTO;
import cr.ac.una.relojunaws.service.EmpleadoService;
import cr.ac.una.relojunaws.util.Respuesta;
import cr.ac.una.relojunaws.util.SOAPResponse;
import jakarta.ejb.EJB;
import jakarta.jws.WebService;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebService(
        endpointInterface = "cr.ac.una.relojunaws.controller.EmpleadoSOAP",
        serviceName = "EmpleadoSOAP",
        targetNamespace = "http://controller.una.ac.cr/empleados"
)
public class EmpleadoController implements EmpleadoSOAP {

    @EJB
    private EmpleadoService empleadoService;

    private static final Logger LOG = Logger.getLogger(EmpleadoController.class.getName());

    @Override
    public SOAPResponse<EmpleadoDTO> autenticarEmpleado(LoginRequestDTO loginRequest) {
        try {
            Respuesta respuesta = empleadoService.autenticarEmpleado(loginRequest.getFolio(), loginRequest.getClave());
            if (!respuesta.getEstado()) {
                return SOAPResponse.error(respuesta.getMensaje(), respuesta.getMensajeInterno());
            }
            return SOAPResponse.exito((EmpleadoDTO) respuesta.getResultado("Empleado"), respuesta.getMensaje());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "EmpleadoController.autenticarEmpleado", e);
            return SOAPResponse.error("", "");
        }
    }

    @Override
    public SOAPResponse<EmpleadoDTO> getEmpleado(String id) {
        // TODO: Implementar solicitud al servicio
        return null; // <- eliminar una vez implementada la funcionalidad
    }

}
