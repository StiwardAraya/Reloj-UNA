package cr.ac.una.relojunaws.controller;

import cr.ac.una.relojunaws.model.dto.JornadaDTO;
import cr.ac.una.relojunaws.model.dto.JornadaListDTO;
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

@WebService(
        name = "MarcaSOAP",
        targetNamespace = "http://controller.una.ac.cr/marcas"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@XmlSeeAlso({MarcaDTO.class, MarcaListDTO.class, JornadaDTO.class, JornadaListDTO.class, ResumenMarcasDTO.class})
public interface MarcaSOAP {

    @WebMethod(operationName = "registrarMarca")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<MarcaDTO> registrarMarca(
            @WebParam(name = "folio") String folio
    );

    @WebMethod(operationName = "obtenerPorFechas")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<MarcaListDTO> obtenerPorFechas(
            @WebParam(name = "desde")
            @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate desde,
            @WebParam(name = "hasta")
            @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate hasta);

    @WebMethod(operationName = "guardarMarca")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<MarcaDTO> guardarMarca(
            @WebParam(name = "marca") MarcaDTO marca);

    @WebMethod(operationName = "eliminarMarca")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<MarcaDTO> eliminarMarca(
            @WebParam(name = "id") Long id);

    @WebMethod(operationName = "obtenerMarcasInconsistentes")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<MarcaListDTO> obtenerMarcasInconsistentes(
            @WebParam(name = "desde")
            @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate desde,
            @WebParam(name = "hasta")
            @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate hasta);

    @WebMethod(operationName = "consultarResumen")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<ResumenMarcasDTO> consultarResumen(
            @WebParam(name = "desde")
            @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate desde,
            @WebParam(name = "hasta")
            @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate hasta, @WebParam(name = "folioEmpleado") String folioEmpleado);

    @WebMethod(operationName = "obtenerJornadas")
    @WebResult(name = "SOAPResponse")
    SOAPResponse<JornadaListDTO> obtenerJornadas(
            @WebParam(name = "desde")
            @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate desde,
            @WebParam(name = "hasta")
            @XmlJavaTypeAdapter(LocalDateAdapter.class) LocalDate hasta, @WebParam(name = "folioEmpleado") String folioEmpleado
    );
}
