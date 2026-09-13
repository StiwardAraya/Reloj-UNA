package cr.ac.una.relojunaws.util;

import cr.ac.una.relojunaws.model.dto.ArchivoDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArchivoResponse", propOrder = {"exito", "mensajeUsuario", "mensajeTecnico", "resultado", "codigo", "fecha"})
public class ArchivoResponse implements Serializable {

    @XmlElement(name = "exito")
    private boolean exito;
    @XmlElement(name = "mensajeUsuario")
    private String mensajeUsuario;
    @XmlElement(name = "mensajeTecnico")
    private String mensajeTecnico;
    @XmlElement(name = "resultado")
    private ArchivoDTO resultado;
    @XmlElement(name = "codigo")
    private String codigo;
    @XmlElement(name = "fecha")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha;

    public ArchivoResponse() {
        this.fecha = LocalDate.now();
    }

    public ArchivoResponse(
            boolean exito,
            String mensajeUsuario,
            String mensajeTecnico,
            ArchivoDTO resultado,
            String codigo) {

        this.exito = exito;
        this.mensajeUsuario = mensajeUsuario;
        this.mensajeTecnico = mensajeTecnico;
        this.resultado = resultado;
        this.codigo = codigo;
        this.fecha = LocalDate.now();
    }

    public static ArchivoResponse exito(ArchivoDTO resultado, String mensajeUsuario, String mensajeTecnico) {
        return new ArchivoResponse(true, mensajeUsuario, mensajeTecnico, resultado, null);
    }

    public static ArchivoResponse error(String mensajeUsuario, String mensajeTecnico) {
        return new ArchivoResponse(false, mensajeUsuario, mensajeTecnico, null, null);
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

    public String getMensajeTecnico() {
        return mensajeTecnico;
    }

    public void setMensajeTecnico(String mensajeTecnico) {
        this.mensajeTecnico = mensajeTecnico;
    }

    public ArchivoDTO getResultado() {
        return resultado;
    }

    public void setResultado(ArchivoDTO resultado) {
        this.resultado = resultado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
