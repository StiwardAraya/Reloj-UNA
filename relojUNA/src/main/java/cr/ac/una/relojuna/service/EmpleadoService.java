package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.EmpleadoSOAP;
import cr.ac.una.relojuna.ws.EmpleadoSOAP_Service;
import cr.ac.una.relojuna.ws.LoginRequestDTO;
import cr.ac.una.relojuna.ws.SOAPResponse;

public class EmpleadoService {

    private final EmpleadoSOAP port;

    public EmpleadoService() {
        EmpleadoSOAP_Service service = new EmpleadoSOAP_Service();
        this.port = service.getEmpleadoControllerPort();
    }

    public Respuesta autenticarEmpleado(LoginRequestDTO loginRequest) {
        SOAPResponse resultado = port.autenticarEmpleado(loginRequest);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Empleado", resultado.getResultado());
    }

}
