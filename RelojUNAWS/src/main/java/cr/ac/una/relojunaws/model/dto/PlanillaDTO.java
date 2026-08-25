package cr.ac.una.relojunaws.model.dto;

import cr.ac.una.relojunaws.model.Planilla;
import cr.ac.una.relojunaws.util.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@XmlRootElement(name = "planilla")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanillaDTO", propOrder = {"id", "mes", "anio", "fechaGeneracion", "totalPagado"})
public class PlanillaDTO implements Serializable {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "mes")
    private Integer mes;

    @XmlElement(name = "anio")
    private Integer anio;

    @XmlElement(name = "fechaGeneracion")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaGeneracion;

    @XmlElement(name = "totalPagado")
    private BigDecimal totalPagado;

    public PlanillaDTO() {
    }

    public PlanillaDTO(Long id) {
        this.id = id;
    }

    public PlanillaDTO(Planilla planilla) {
        this();
        this.id = planilla.getId();
        this.mes = planilla.getMes();
        this.anio = planilla.getAnio();
        this.fechaGeneracion = planilla.getFechaGeneracion();
        this.totalPagado = planilla.getTotalPagado();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public BigDecimal getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
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
        final PlanillaDTO other = (PlanillaDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "PlanillaDTO{" + "id=" + id + ", mes=" + mes + ", anio=" + anio + ", fechaGeneracion=" + fechaGeneracion + ", totalPagado=" + totalPagado + '}';
    }

}
