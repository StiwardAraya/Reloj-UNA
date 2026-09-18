package cr.ac.una.relojunaws.model;

import cr.ac.una.relojunaws.model.dto.DetallePlanillaDTO;
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
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "DETALLE_PLANILLA", schema = "relojUNA")
@NamedQueries({
    @NamedQuery(name = "DetallePlanilla.findAll", query = "SELECT dp FROM DetallePlanilla dp"),
    @NamedQuery(name = "DetallePlanilla.findById", query = "SELECT dp FROM DetallePlanilla dp WHERE dp.id = :id"),
    @NamedQuery(name = "DetallePlanilla.findByPlanilla", query = "SELECT d FROM DetallePlanilla d WHERE d.planilla.id = :idPlanilla")
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
    private Double totalHorasOrdinarias;

    @Basic(optional = false)
    @Column(name = "total_horas_extras")
    private Double totalHorasExtras;

    @Basic(optional = false)
    @Column(name = "total_a_pagar")
    private BigDecimal totalAPagar;

    @Basic(optional = false)
    @Column(name = "total_horas_dobles")
    private Double totalHorasDobles;

    @Basic(optional = false)
    @Column(name = "total_horas_nocturnas")
    private Double totalHorasNocturnas;

    @Version
    @Basic(optional = false)
    @Column(name = "version_detalle_planilla")
    private Long version;

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

    public DetallePlanilla(DetallePlanillaDTO dto) {
        this.id = dto.getId();
        actualizar(dto);
    }

    public final void actualizar(DetallePlanillaDTO dto) {
        this.totalHorasOrdinarias = dto.getTotalHorasOrdinarias();
        this.totalHorasExtras = dto.getTotalHorasExtras();
        this.totalHorasDobles = dto.getTotalHorasDobles();
        this.totalHorasNocturnas = dto.getTotalHorasNocturnas();
        this.totalAPagar = dto.getTotalAPagar();
        this.version = dto.getVersion();
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
        hash = 97 * hash + Objects.hashCode(this.id);
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
        return "DetallePlanilla{" + "id=" + id + '}';
    }

}
