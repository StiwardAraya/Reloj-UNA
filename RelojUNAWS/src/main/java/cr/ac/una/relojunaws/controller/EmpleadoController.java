package cr.ac.una.relojunaws.controller;

import cr.ac.una.relojunaws.model.dto.EmpleadoDTO;
import cr.ac.una.relojunaws.model.dto.EmpleadoListDTO;
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
        try {
            Respuesta respuesta = empleadoService.getEmpleado(Long.valueOf(id));
            if (!respuesta.getEstado()) {
                return SOAPResponse.error(respuesta.getMensaje(), respuesta.getMensajeInterno());
            }
            return SOAPResponse.exito((EmpleadoDTO) respuesta.getResultado("Empleado"), respuesta.getMensaje());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "EmpleadoController.getEmpleado", e);
            return SOAPResponse.error("", "");
        }
    }

    @Override
    public SOAPResponse<EmpleadoDTO> getEmpleadoIdFolio(String id, String folio) {
        try {
            Long idEmpleado;
            if (id.isBlank()) {
                idEmpleado = 0L;
            } else {
                idEmpleado = Long.valueOf(id);
            }

            Respuesta respuesta = empleadoService.getEmpleadoIdFolio(idEmpleado, folio);
            if (!respuesta.getEstado()) {
                return SOAPResponse.error(respuesta.getMensaje(), respuesta.getMensajeInterno());
            }
            return SOAPResponse.exito((EmpleadoDTO) respuesta.getResultado("Empleado"), respuesta.getMensaje());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "EmpleadoController.getEmpleadoIdFolio", e);
            return SOAPResponse.error("", "");
        }
    }

    @Override
    public SOAPResponse<EmpleadoListDTO> getEmpleados() {
        try {
            Respuesta respuesta = empleadoService.getEmpleados();
            if (!respuesta.getEstado()) {
                return SOAPResponse.error(respuesta.getMensaje(), respuesta.getMensajeInterno());
            }
            return SOAPResponse.exito((EmpleadoListDTO) respuesta.getResultado(), respuesta.getMensaje());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "EmpleadoController.getEmpleados", e);
            return SOAPResponse.error("", "");
        }

    }

    @Override
    public SOAPResponse<EmpleadoListDTO> getEmpleadosByFilters(String folio, String cedula, String nombre, String primerApellido, String segundoApellido) {
        try {
            Respuesta respuesta = empleadoService.getEmpleadosByFilters(folio, cedula, nombre, primerApellido, segundoApellido);
            if (!respuesta.getEstado()) {
                return SOAPResponse.error(respuesta.getMensaje(), respuesta.getMensajeInterno());
            }
            return SOAPResponse.exito((EmpleadoListDTO) respuesta.getResultado(), respuesta.getMensaje());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "EmpleadoController.getEmpleadosByFilters", e);
            return SOAPResponse.error("", "");
        }
    }

    @Override
    public SOAPResponse<EmpleadoListDTO> getEmpleadosActivos() {
        try {
            Respuesta respuesta = empleadoService.getEmpleadosActivos();
            if (!respuesta.getEstado()) {
                return SOAPResponse.error(respuesta.getMensaje(), respuesta.getMensajeInterno());
            }
            return SOAPResponse.exito((EmpleadoListDTO) respuesta.getResultado(), respuesta.getMensaje());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "EmpleadoController.getEmpleadoActivos", e);
            return SOAPResponse.error("", "");
        }
    }

    @Override
    public SOAPResponse<EmpleadoDTO> guardarEmpleado(EmpleadoDTO empleado) {
        try {
            Respuesta respuesta = empleadoService.guardarEmpleado(empleado);
            if (!respuesta.getEstado()) {
                return SOAPResponse.error(respuesta.getMensaje(), respuesta.getMensajeInterno());
            }
            return SOAPResponse.exito((EmpleadoDTO) respuesta.getResultado("Empleado"), respuesta.getMensaje());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "EmpleadoController.guardarEmpleado", e);
            return SOAPResponse.error("", "");
        }
    }

    @Override
    public SOAPResponse<EmpleadoDTO> eliminarEmpleado(String id) {
        try {
            Respuesta respuesta = empleadoService.eliminarEmpleado(Long.valueOf(id));
            if (!respuesta.getEstado()) {
                return SOAPResponse.error(respuesta.getMensaje(), respuesta.getMensajeInterno());
            }
            return SOAPResponse.exito(respuesta.getMensaje(), respuesta.getMensajeInterno());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "EmpleadoController.eliminarEmpleado", e);
            return SOAPResponse.error("", "");
        }
    }

}
