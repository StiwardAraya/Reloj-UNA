package cr.ac.una.relojuna.service;

import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.EmpleadoDTO;
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

    public Respuesta getEmpleado(String id) {
        SOAPResponse resultado = port.getEmpleado(id);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Empleado", resultado.getResultado());
    }

    public Respuesta getEmpleadoIdFolio(String id, String folio) {
        // TODO: Implementar este metodo
        return null;
    }

    public Respuesta getEmpleados() {
        // TODO: Implementar este metodo
        return null;
    }

    public Respuesta getEmpleadosByFilters(String folio, String cedula, String nombre, String pApellido, String sApellido) {
        // TODO: Implementar este metodo
        return null;
    }

    public Respuesta guardarEmpleado(EmpleadoDTO empleadoDto) {
        // TODO: Implementar este metodo
        return null;
    }

    public Respuesta eliminarEmpleado(String id) {
        // TODO: Implementar este metodo
        return null;
    }

}
