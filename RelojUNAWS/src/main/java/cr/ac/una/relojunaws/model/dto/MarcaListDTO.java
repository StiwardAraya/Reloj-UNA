package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MarcaListDTO")
public class MarcaListDTO implements Serializable {

    @XmlElement(name = "marcas")
    private List<MarcaDTO> marcas;

    public MarcaListDTO() {
    }

    public MarcaListDTO(List<MarcaDTO> marcas) {
        this.marcas = marcas;
    }

    public List<MarcaDTO> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<MarcaDTO> marcas) {
        this.marcas = marcas;
    }

}
