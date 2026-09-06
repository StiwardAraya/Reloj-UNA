package cr.ac.una.relojunaws.model.dto;

import cr.ac.una.relojunaws.model.JornadaPOJO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import cr.ac.una.relojunaws.util.LocalDateAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name = "jornada")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JornadaDTO", propOrder = {
    "folioEmpleado", "nombreEmpleado", "fecha", "marcaEntrada", "marcaSalida",
    "horasTrabajadas", "esDiaLibre", "completa"
})
public class JornadaDTO implements Serializable {

    @XmlElement(name = "folioEmpleado")
    private String folioEmpleado;

    @XmlElement(name = "nombreEmpleado")
    private String nombreEmpleado;

    @XmlElement(name = "fecha")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha;

    @XmlElement(name = "marcaEntrada")
    private MarcaDTO marcaEntrada;

    @XmlElement(name = "marcaSalida")
    private MarcaDTO marcaSalida;

    @XmlElement(name = "horasTrabajadas")
    private Double horasTrabajadas;

    @XmlElement(name = "esDiaLibre")
    private Boolean esDiaLibre;

    @XmlElement(name = "completa")
    private Boolean completa;

    public JornadaDTO() {
    }

    public JornadaDTO(JornadaPOJO jornada) {
        this.folioEmpleado = jornada.getEmpleado().getFolio();
        this.nombreEmpleado = jornada.getEmpleado().getNombre() + " " + jornada.getEmpleado().getPrimerApellido();
        this.fecha = jornada.getFecha();
        this.marcaEntrada = jornada.getEntrada() != null ? new MarcaDTO(jornada.getEntrada()) : null;
        this.marcaSalida = jornada.getSalida() != null ? new MarcaDTO(jornada.getSalida()) : null;
        this.horasTrabajadas = jornada.getHorasTrabajadas() != null
                ? jornada.getHorasTrabajadas().toMinutes() / 60.0
                : null;
        this.esDiaLibre = jornada.isEsDiaLibre();
        this.completa = jornada.isCompleta();
    }

    public String getFolioEmpleado() {
        return folioEmpleado;
    }

    public void setFolioEmpleado(String folioEmpleado) {
        this.folioEmpleado = folioEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public MarcaDTO getMarcaEntrada() {
        return marcaEntrada;
    }

    public void setMarcaEntrada(MarcaDTO marcaEntrada) {
        this.marcaEntrada = marcaEntrada;
    }

    public MarcaDTO getMarcaSalida() {
        return marcaSalida;
    }

    public void setMarcaSalida(MarcaDTO marcaSalida) {
        this.marcaSalida = marcaSalida;
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

    public Boolean getCompleta() {
        return completa;
    }

    public void setCompleta(Boolean completa) {
        this.completa = completa;
    }

}
