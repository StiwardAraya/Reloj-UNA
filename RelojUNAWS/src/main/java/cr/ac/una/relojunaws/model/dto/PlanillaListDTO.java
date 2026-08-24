package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanillaListDTO")
public class PlanillaListDTO {

    @XmlElement(name = "planillas")
    private List<PlanillaDTO> planillas;

    public PlanillaListDTO() {
    }

    public PlanillaListDTO(List<PlanillaDTO> planillas) {
        this.planillas = planillas;
    }

    public List<PlanillaDTO> getPlanillas() {
        return planillas;
    }

    public void setPlanillas(List<PlanillaDTO> planillas) {
        this.planillas = planillas;
    }

}
