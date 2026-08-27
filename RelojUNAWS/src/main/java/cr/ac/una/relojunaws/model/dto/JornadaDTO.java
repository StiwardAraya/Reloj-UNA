package cr.ac.una.relojunaws.model.dto;

import cr.ac.una.relojunaws.model.Jornada;
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

@XmlRootElement(name = "jornada")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JornadaDTO", propOrder = {
    "id", "fecha", "esDiaLibre", "tipoJornada", "horasOrdinarias",
    "horasExtras", "montoPagar", "version", "empleadoId", "detallePlanillaId"
})
public class JornadaDTO implements Serializable {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "fecha")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha;

    @XmlElement(name = "esDiaLibre")
    private String esDiaLibre;

    @XmlElement(name = "tipoJornada")
    private String tipoJornada;

    @XmlElement(name = "horasOrdinarias")
    private Integer horasOrdinarias;

    @XmlElement(name = "horasExtras")
    private Integer horasExtras;

    @XmlElement(name = "montoPagar")
    private BigDecimal montoPagar;

    @XmlElement(name = "version")
    private Long version;

    @XmlElement(name = "empleadoId")
    private Long empleadoId;

    @XmlElement(name = "detallePlanillaId")
    private Long detallePlanillaId;

    public JornadaDTO() {
    }

    public JornadaDTO(Long id) {
        this.id = id;
    }

    public JornadaDTO(Jornada jornada) {
        this();
        this.id = jornada.getId();
        this.fecha = jornada.getFecha();
        this.esDiaLibre = jornada.getEsDiaLibre();
        this.tipoJornada = jornada.getTipoJornada();
        this.horasOrdinarias = jornada.getHorasOrdinarias();
        this.horasExtras = jornada.getHorasExtras();
        this.montoPagar = jornada.getMontoPagar();
        this.version = jornada.getVersion();
        this.empleadoId = jornada.getEmpleado().getId();
        this.detallePlanillaId = jornada.getDetallePlanilla().getId();
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

    public Long getDetallePlanillaId() {
        return detallePlanillaId;
    }

    public void setDetallePlanillaId(Long detallePlanillaId) {
        this.detallePlanillaId = detallePlanillaId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final JornadaDTO other = (JornadaDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "JornadaDTO{" + "id=" + id + ", fecha=" + fecha + ", esDiaLibre=" + esDiaLibre + ", tipoJornada=" + tipoJornada + ", horasOrdinarias=" + horasOrdinarias + ", horasExtras=" + horasExtras + ", montoPagar=" + montoPagar + ", empleadoId=" + empleadoId + ", detallePlanillaId=" + detallePlanillaId + '}';
    }

}
