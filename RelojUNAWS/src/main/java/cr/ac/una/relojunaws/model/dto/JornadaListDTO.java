package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JornadaListDTO")
public class JornadaListDTO {

    @XmlElement(name = "jornadas")
    private List<JornadaDTO> jornadas;

    public JornadaListDTO() {
    }

    public JornadaListDTO(List<JornadaDTO> jornadas) {
        this.jornadas = jornadas;
    }

    public List<JornadaDTO> getJornadas() {
        return jornadas;
    }

    public void setJornadas(List<JornadaDTO> jornadas) {
        this.jornadas = jornadas;
    }

}
