package cr.ac.una.relojunaws.model;

import cr.ac.una.relojunaws.model.dto.MarcaDTO;
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
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "MARCA", schema = "relojUNA")
@NamedQueries({
    @NamedQuery(name = "Marca.findAll", query = "SELECT m FROM Marca m"),
    @NamedQuery(name = "Marca.findById", query = "SELECT m FROM Marca m WHERE m.id = :id"),
    @NamedQuery(name = "Marca.findByEmpleado", query = "SELECT m FROM Marca m WHERE m.empleado.folio = :folio ORDER BY m.fechaHora DESC"),
    @NamedQuery(name = "Marca.findByRangoFechas", query = "SELECT m FROM Marca m WHERE m.fechaHora BETWEEN :desde AND :hasta ORDER BY m.fechaHora")
})
public class Marca implements Serializable {

    @Id
    @SequenceGenerator(name = "MRC_ID_GENERATOR", sequenceName = "relojUNA.MRC_SEQ_01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MRC_ID_GENERATOR")
    @Basic(optional = true)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;

    @Basic(optional = false)
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Version
    @Basic(optional = false)
    @Column(name = "version_marca")
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    public Marca() {
    }

    public Marca(Long id) {
        this.id = id;
    }

    public Marca(MarcaDTO marcaDto) {
        this.id = marcaDto.getId();
        actualizar(marcaDto);
    }

    public final void actualizar(MarcaDTO marcaDto) {
        this.tipo = marcaDto.getTipo();
        this.fechaHora = marcaDto.getFechaHora();
        this.version = marcaDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
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
        final Marca other = (Marca) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Marca{" + "id=" + id + '}';
    }

}
