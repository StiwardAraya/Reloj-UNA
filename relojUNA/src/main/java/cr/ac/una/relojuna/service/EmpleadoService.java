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
        SOAPResponse resultado = port.getEmpleadoIdFolio(id, folio);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Empleado", resultado.getResultado());
    }

    public Respuesta getEmpleados() {
        SOAPResponse resultado = port.getEmpleados();
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Empleados", resultado.getResultado());
    }

    public Respuesta getEmpleadosByFilters(String folio, String cedula, String nombre, String pApellido, String sApellido) {
        SOAPResponse resultado = port.getEmpleadosByFilters(
                "%" + folio.toUpperCase() + "%",
                "%" + cedula.toUpperCase() + "%",
                "%" + nombre.toUpperCase() + "%",
                "%" + pApellido.toUpperCase() + "%",
                "%" + sApellido.toUpperCase() + "%");
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Empleados", resultado.getResultado());
    }

    public Respuesta guardarEmpleado(EmpleadoDTO empleadoDto) {
        SOAPResponse resultado = port.guardarEmpleado(empleadoDto);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Empleado", resultado.getResultado());
    }

    public Respuesta eliminarEmpleado(String id) {
        SOAPResponse resultado = port.eliminarEmpleado(id);
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Empleado", resultado.getResultado());
    }

    public Respuesta getEmpleadosActivos() {
        SOAPResponse resultado = port.getEmpleadosActivos();
        if (!resultado.isExito()) {
            return new Respuesta(false, resultado.getMensajeUsuario(), resultado.getMensajeTecnico());
        }
        return new Respuesta(true, resultado.getMensajeUsuario(), resultado.getMensajeTecnico(), "Empleados", resultado.getResultado());
    }
}
