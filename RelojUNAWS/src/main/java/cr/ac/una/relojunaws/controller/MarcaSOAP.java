package cr.ac.una.relojunaws.controller;

import cr.ac.una.relojunaws.model.dto.JornadaDTO;
import cr.ac.una.relojunaws.model.dto.JornadaListDTO;
import cr.ac.una.relojunaws.model.dto.MarcaDTO;
import cr.ac.una.relojunaws.model.dto.MarcaListDTO;
import cr.ac.una.relojunaws.model.dto.ResumenMarcasDTO;
import cr.ac.una.relojunaws.util.SOAPResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlSeeAlso;

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

    // TODO: Endpoint para obtenerPorFechas
    // TODO: Endpoint para guardarMarca
    // TODO: Endpoint para eliminarMarca
    // TODO: Endpoint para obtenerMarcasInconsistentes
    // TODO: Endpoint para consultarResumen
    // TODO: Endpoint para obtenerJornadas
}
