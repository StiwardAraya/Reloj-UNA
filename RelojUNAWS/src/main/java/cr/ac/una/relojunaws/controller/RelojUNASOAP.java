package cr.ac.una.relojunaws.controller;

import cr.ac.una.relojunaws.model.dto.EmpleadoDTO;
import cr.ac.una.relojunaws.model.dto.EmpleadoListDTO;
import cr.ac.una.relojunaws.model.dto.JornadaDTO;
import cr.ac.una.relojunaws.model.dto.JornadaListDTO;
import cr.ac.una.relojunaws.model.dto.LoginRequestDTO;
import cr.ac.una.relojunaws.model.dto.MarcaDTO;
import cr.ac.una.relojunaws.model.dto.MarcaListDTO;
import cr.ac.una.relojunaws.model.dto.ResumenMarcasDTO;
import cr.ac.una.relojunaws.util.LocalDateAdapter;
import cr.ac.una.relojunaws.util.SOAPResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import cr.ac.una.relojunaws.util.ArchivoResponse;
import cr.ac.una.relojunaws.model.dto.ArchivoDTO;

@WebService(
        name = "RelojUNASOAP",
        targetNamespace = "http://controller.una.ac.cr/relojuna"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@XmlSeeAlso({
    EmpleadoDTO.class, EmpleadoListDTO.class, LoginRequestDTO.class,
    MarcaDTO.class, MarcaListDTO.class, JornadaDTO.class, JornadaListDTO.class,
    ResumenMarcasDTO.class, ArchivoDTO.class, ArchivoResponse.class
})
public interface RelojUNASOAP {

    @WebMethod(operationName = "autenticarEmpleado")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoDTO> autenticarEmpleado(@WebParam(name = "loginRequest") LoginRequestDTO loginRequest);

    @WebMethod(operationName = "getEmpleado")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoDTO> getEmpleado(@WebParam(name = "id") String id);

    @WebMethod(operationName = "getEmpleadoIdFolio")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoDTO> getEmpleadoIdFolio(@WebParam(name = "id") String id, @WebParam(name = "folio") String folio);

    @WebMethod(operationName = "getEmpleados")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoListDTO> getEmpleados();

    @WebMethod(operationName = "getEmpleadosByFilters")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoListDTO> getEmpleadosByFilters(@WebParam(name = "correo") String correo,
            @WebParam(name = "cedula") String cedula, @WebParam(name = "nombre") String nombre, @WebParam(name = "primerApellido") String primerApellido, @WebParam(name = "segundoApellido") String segundoApellido);

    @WebMethod(operationName = "getEmpleadosActivos")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoListDTO> getEmpleadosActivos();

    @WebMethod(operationName = "guardarEmpleado")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoDTO> guardarEmpleado(@WebParam(name = "empleado") EmpleadoDTO empleado);

    @WebMethod(operationName = "eliminarEmpleado")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<EmpleadoDTO> eliminarEmpleado(@WebParam(name = "id") String id);

    @WebMethod(operationName = "registrarMarca")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<MarcaDTO> registrarMarca(@WebParam(name = "folio") String folio);

    @WebMethod(operationName = "obtenerPorFechas")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<MarcaListDTO> obtenerPorFechas(
            @WebParam(name = "desde") @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate desde,
            @WebParam(name = "hasta") @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate hasta);

    @WebMethod(operationName = "guardarMarca")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<MarcaDTO> guardarMarca(@WebParam(name = "marca") MarcaDTO marca);

    @WebMethod(operationName = "eliminarMarca")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<MarcaDTO> eliminarMarca(@WebParam(name = "id") Long id);

    @WebMethod(operationName = "obtenerMarcasInconsistentes")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<MarcaListDTO> obtenerMarcasInconsistentes(
            @WebParam(name = "desde") @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate desde,
            @WebParam(name = "hasta") @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate hasta);

    @WebMethod(operationName = "consultarResumen")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<ResumenMarcasDTO> consultarResumen(
            @WebParam(name = "desde") @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate desde,
            @WebParam(name = "hasta") @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate hasta,
            @WebParam(name = "folioEmpleado") String folioEmpleado);

    @WebMethod(operationName = "obtenerJornadas")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<JornadaListDTO> obtenerJornadas(
            @WebParam(name = "desde") @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate desde,
            @WebParam(name = "hasta") @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate hasta,
            @WebParam(name = "folioEmpleado") String folioEmpleado);

    @WebMethod(operationName = "generarExcelMarcas")
    @WebResult(name = "ArchivoResponse")
    ArchivoResponse generarExcelMarcas(
            @WebParam(name = "desde") @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate desde,
            @WebParam(name = "hasta") @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate hasta,
            @WebParam(name = "folioEmpleado") String folioEmpleado);

    @WebMethod(operationName = "generarReporteEmpleados")
    @WebResult(name = "ArchivoResponse", targetNamespace = "")
    ArchivoResponse generarReporteEmpleados();

}
