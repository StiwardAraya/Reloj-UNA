package cr.ac.una.relojunaws.model.dto;

import cr.ac.una.relojunaws.model.DetallePlanilla;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@XmlRootElement(name = "detalleplanilla")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetallePlanillaDTO", propOrder = {
    "id", "totalHorasOrdinarias", "totalHorasExtras",
    "totalAPagar", "totalHorasDobles", "totalHorasNocturnas",
    "version", "empleadoId", "planillaId"
})
public class DetallePlanillaDTO implements Serializable {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "totalHorasOrdinarias")
    private Double totalHorasOrdinarias;

    @XmlElement(name = "totalHorasExtras")
    private Double totalHorasExtras;

    @XmlElement(name = "totalAPagar")
    private BigDecimal totalAPagar;

    @XmlElement(name = "totalHorasDobles")
    private Double totalHorasDobles;

    @XmlElement(name = "totalHorasNocturnas")
    private Double totalHorasNocturnas;

    @XmlElement(name = "version")
    private Long version;

    @XmlElement(name = "empleadoId")
    private Long empleadoId;

    @XmlElement(name = "planillaId")
    private Long planillaId;

    public DetallePlanillaDTO() {
    }

    public DetallePlanillaDTO(Long id) {
        this.id = id;
    }

    public DetallePlanillaDTO(DetallePlanilla dp) {
        this();
        this.id = dp.getId();
        this.totalHorasOrdinarias = dp.getTotalHorasOrdinarias();
        this.totalHorasExtras = dp.getTotalHorasExtras();
        this.totalAPagar = dp.getTotalAPagar();
        this.totalHorasDobles = dp.getTotalHorasDobles();
        this.totalHorasNocturnas = dp.getTotalHorasNocturnas();
        this.version = dp.getVersion();
        this.empleadoId = dp.getEmpleado().getId();
        this.planillaId = dp.getPlanilla().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalHorasOrdinarias() {
        return totalHorasOrdinarias;
    }

    public void setTotalHorasOrdinarias(Double totalHorasOrdinarias) {
        this.totalHorasOrdinarias = totalHorasOrdinarias;
    }

    public Double getTotalHorasExtras() {
        return totalHorasExtras;
    }

    public void setTotalHorasExtras(Double totalHorasExtras) {
        this.totalHorasExtras = totalHorasExtras;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public Double getTotalHorasDobles() {
        return totalHorasDobles;
    }

    public void setTotalHorasDobles(Double totalHorasDobles) {
        this.totalHorasDobles = totalHorasDobles;
    }

    public Double getTotalHorasNocturnas() {
        return totalHorasNocturnas;
    }

    public void setTotalHorasNocturnas(Double totalHorasNocturnas) {
        this.totalHorasNocturnas = totalHorasNocturnas;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Long getPlanillaId() {
        return planillaId;
    }

    public void setPlanillaId(Long planillaId) {
        this.planillaId = planillaId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DetallePlanillaDTO other = (DetallePlanillaDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "DetallePlanillaDTO{" + "id=" + id + ", totalHorasOrdinarias=" + totalHorasOrdinarias + ", totalHorasExtras=" + totalHorasExtras + ", totalAPagar=" + totalAPagar + ", totalHorasDobles=" + totalHorasDobles + ", version=" + version + ", empleadoId=" + empleadoId + ", planillaId=" + planillaId + '}';
    }

}
