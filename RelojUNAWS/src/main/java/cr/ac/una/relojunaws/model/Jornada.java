package cr.ac.una.relojunaws.model;

import cr.ac.una.relojunaws.model.dto.JornadaDTO;
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
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "JORNADA", schema = "relojUNA")
@NamedQueries({
    @NamedQuery(name = "Jornada.findAll", query = "SELECT j FROM Jornada j"),
    @NamedQuery(name = "Jornada.findById", query = "SELECT j FROM Jornada j WHERE j.id = :id")
})
public class Jornada {

    @Id
    @SequenceGenerator(name = "JRN_ID_GENERATOR", sequenceName = "relojUNA.JRN_SEQ_01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JRN_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "fecha")
    private LocalDate fecha;

    @Basic(optional = false)
    @Column(name = "es_dia_libre")
    private String esDiaLibre;

    @Basic(optional = false)
    @Column(name = "tipo_jornada")
    private String tipoJornada;

    @Basic(optional = false)
    @Column(name = "horas_ordinarias")
    private Integer horasOrdinarias;

    @Column(name = "horas_extras")
    private Integer horasExtras;

    @Basic(optional = false)
    @Column(name = "monto_pagar")
    private BigDecimal montoPagar;

    @Basic(optional = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalle_planilla")
    private DetallePlanilla detallePlanilla;

    public Jornada() {
    }

    public Jornada(Long id) {
        this.id = id;
    }

    public Jornada(JornadaDTO dto) {
        this.id = dto.getId();
        actualizar(dto);
    }

    public final void actualizar(JornadaDTO dto) {
        this.fecha = dto.getFecha();
        this.esDiaLibre = dto.getEsDiaLibre();
        this.tipoJornada = dto.getTipoJornada();
        this.horasOrdinarias = dto.getHorasOrdinarias();
        this.horasExtras = dto.getHorasExtras();
        this.montoPagar = dto.getMontoPagar();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEsDiaLibre() {
        return esDiaLibre;
    }

    public void setEsDiaLibre(String esDiaLibre) {
        this.esDiaLibre = esDiaLibre;
    }

    public String getTipoJornada() {
        return tipoJornada;
    }

    public void setTipoJornada(String tipoJornada) {
        this.tipoJornada = tipoJornada;
    }

    public Integer getHorasOrdinarias() {
        return horasOrdinarias;
    }

    public void setHorasOrdinarias(Integer horasOrdinarias) {
        this.horasOrdinarias = horasOrdinarias;
    }

    public Integer getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(Integer horasExtras) {
        this.horasExtras = horasExtras;
    }

    public BigDecimal getMontoPagar() {
        return montoPagar;
    }

    public void setMontoPagar(BigDecimal montoPagar) {
        this.montoPagar = montoPagar;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public DetallePlanilla getDetallePlanilla() {
        return detallePlanilla;
    }

    public void setDetallePlanilla(DetallePlanilla detallePlanilla) {
        this.detallePlanilla = detallePlanilla;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final Jornada other = (Jornada) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Jornada{" + "id=" + id + '}';
    }

}
