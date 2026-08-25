package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetallePlanillaListDTO")
public class DetallePlanillaListDTO {

    @XmlElement(name = "detallesPlanilla")
    private List<DetallePlanillaDTO> detallesPlanilla;

    public DetallePlanillaListDTO() {
    }

    public DetallePlanillaListDTO(List<DetallePlanillaDTO> detallesPlanilla) {
        this.detallesPlanilla = detallesPlanilla;
    }

    public List<DetallePlanillaDTO> getDetallesPlanilla() {
        return detallesPlanilla;
    }

    public void setDetallesPlanilla(List<DetallePlanillaDTO> detallesPlanilla) {
        this.detallesPlanilla = detallesPlanilla;
    }

}
