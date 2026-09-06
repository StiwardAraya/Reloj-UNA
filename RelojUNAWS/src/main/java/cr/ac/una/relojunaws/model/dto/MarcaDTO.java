package cr.ac.una.relojunaws.model.dto;

import cr.ac.una.relojunaws.model.Marca;
import cr.ac.una.relojunaws.util.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement(name = "marca")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MarcaDTO", propOrder = {"id", "tipo", "fechaHora", "version", "idEmpleado"})
public class MarcaDTO implements Serializable {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "tipo")
    private String tipo;

    @XmlElement(name = "fechaHora")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fechaHora;

    @XmlElement(name = "version")
    private Long version;

    @XmlElement(name = "folioEmpleado")
    private String folioEmpleado;

    public MarcaDTO() {
    }

    public MarcaDTO(Long id) {
        this.id = id;
    }

    public MarcaDTO(Marca marca) {
        this();
        this.id = marca.getId();
        this.tipo = marca.getTipo();
        this.fechaHora = marca.getFechaHora();
        this.version = marca.getVersion();
        this.folioEmpleado = marca.getEmpleado().getFolio();
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

    public String getFolioEmpleado() {
        return folioEmpleado;
    }

    public void setFolioEmpleado(String folioEmpleado) {
        this.folioEmpleado = folioEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final MarcaDTO other = (MarcaDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "MarcaDTO{" + "id=" + id + ", tipo=" + tipo + ", fechaHora=" + fechaHora + ", version=" + version + ", folioEmpleado=" + folioEmpleado + '}';
    }

}
