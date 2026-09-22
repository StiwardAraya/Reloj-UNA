package cr.ac.una.relojunaws.model.dto;

import cr.ac.una.relojunaws.util.LocalDateAdapter;
import cr.ac.una.relojunaws.util.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResumenJornadaDTO", propOrder = {
    "fecha", "horaEntrada", "horaSalida", "horasTrabajadas",
    "esDiaLibre", "estado"
})
public class ResumenJornadaDTO {

    @XmlElement(name = "fecha")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha;

    @XmlElement(name = "horaEntrada")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime horaEntrada;

    @XmlElement(name = "horaSalida")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime horaSalida;

    @XmlElement(name = "horasTrabajadas")
    private Double horasTrabajadas;

    @XmlElement(name = "esDiaLibre")
    private Boolean esDiaLibre;

    @XmlElement(name = "estado")
    private Boolean estado; //<- true: completa, false: inconsistente

    public ResumenJornadaDTO() {
    }

    public ResumenJornadaDTO(LocalDate fecha, LocalDateTime horaEntrada, LocalDateTime horaSalida, Double horasTrabajadas, Boolean esDiaLibre, Boolean estado) {
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasTrabajadas = horasTrabajadas;
        this.esDiaLibre = esDiaLibre;
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public Boolean getEsDiaLibre() {
        return esDiaLibre;
    }

    public void setEsDiaLibre(Boolean esDiaLibre) {
        this.esDiaLibre = esDiaLibre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ResumenJornadaDTO{" + "fecha=" + fecha + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", horasTrabajadas=" + horasTrabajadas + ", esDiaLibre=" + esDiaLibre + ", estado=" + estado + '}';
    }

}
