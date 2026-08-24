package cr.ac.una.relojunaws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "DETALLE_PLANILLA", schema = "relojUNA")
@NamedQueries({
    @NamedQuery(name = "DetallePlanilla.findAll", query = "SELECT dp FROM DetallePlanilla dp"),
    @NamedQuery(name = "DetallePlanilla.findById", query = "SELECT dp FROM DetallePlanilla dp WHERE dp.id = :id")
})
public class DetallePlanilla {

    @Id
    @SequenceGenerator(name = "DPL_ID_GENERATOR", sequenceName = "relojUNA.DPL_SEQ_01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DPL_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "total_horas_ordinarias")
    private Integer totalHorasOrdinarias;

    @Basic(optional = false)
    @Column(name = "total_horas_extras")
    private Integer totalHorasExtras;

    @Basic(optional = false)
    @Column(name = "total_a_pagar")
    private BigDecimal totalAPagar;

    @Basic(optional = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_planilla")
    private Planilla planilla;

    public DetallePlanilla() {
    }

    public DetallePlanilla(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalHorasOrdinarias() {
        return totalHorasOrdinarias;
    }

    public void setTotalHorasOrdinarias(Integer totalHorasOrdinarias) {
        this.totalHorasOrdinarias = totalHorasOrdinarias;
    }

    public Integer getTotalHorasExtras() {
        return totalHorasExtras;
    }

    public void setTotalHorasExtras(Integer totalHorasExtras) {
        this.totalHorasExtras = totalHorasExtras;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Planilla getPlanilla() {
        return planilla;
    }

    public void setPlanilla(Planilla planilla) {
        this.planilla = planilla;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final DetallePlanilla other = (DetallePlanilla) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "DetallePlanilla{" + "id=" + id + ", totalHorasOrdinarias=" + totalHorasOrdinarias + ", totalHorasExtras=" + totalHorasExtras + ", totalAPagar=" + totalAPagar + ", empleado=" + empleado + ", planilla=" + planilla + '}';
    }

}
