package cr.ac.una.relojunaws.controller;

import cr.ac.una.relojunaws.model.dto.EmpleadoDTO;
import cr.ac.una.relojunaws.model.dto.EmpleadoListDTO;
import cr.ac.una.relojunaws.model.dto.LoginRequestDTO;
import cr.ac.una.relojunaws.util.SOAPResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@WebService(
        name = "EmpleadoSOAP",
        targetNamespace = "http://controller.una.ac.cr/empleados"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@XmlSeeAlso({EmpleadoDTO.class, EmpleadoListDTO.class})
public interface EmpleadoSOAP {

    @WebMethod(operationName = "autenticarEmpleado")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoDTO> autenticarEmpleado(
            @WebParam(name = "loginRequest") LoginRequestDTO loginRequest
    );

    @WebMethod(operationName = "getEmpleado")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoDTO> getEmpleado(
            @WebParam(name = "id") String id
    );

    @WebMethod(operationName = "getEmpleados")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoListDTO> getEmpleados();

    @WebMethod(operationName = "getEmpleadosActivos")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoListDTO> getEmpleadosActivos();

    @WebMethod(operationName = "guardarEmpleado")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoDTO> guardarEmpleado(
            @WebParam(name = "empleado") EmpleadoDTO empleado
    );

    @WebMethod(operationName = "eliminarEmpleado")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoDTO> eliminarEmpleado(
            @WebParam (name="id") String id
    );

}
