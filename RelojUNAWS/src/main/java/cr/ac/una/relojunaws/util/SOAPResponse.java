package cr.ac.una.relojunaws.util;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOAPResponse", propOrder = {"exito", "mensajeUsuario", "mensajeTecnico", "resultado", "codigo", "fecha"})
public class SOAPResponse<T> implements Serializable {

    @XmlElement(name = "exito")
    private boolean exito;

    @XmlElement(name = "mensajeUsuario")
    private String mensajeUsuario;

    @XmlElement(name = "mensajeTecnico")
    private String mensajeTecnico;

    @XmlElement(name = "resultado")
    private T resultado;

    @XmlElement(name = "codigo")
    private String codigo;

    @XmlElement(name = "fecha")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha;

    public SOAPResponse() {
        this.fecha = LocalDate.now();
    }

    public SOAPResponse(boolean exito, String mensajeUsuario, String mensajeTecnico, T resultado, String codigo) {
        this.exito = exito;
        this.mensajeUsuario = mensajeUsuario;
        this.mensajeTecnico = mensajeTecnico;
        this.resultado = resultado;
        this.codigo = codigo;
        this.fecha = LocalDate.now();
    }

    public static <T> SOAPResponse<T> exito(T resultado, String mensajeUsuario) {
        return new SOAPResponse(true, mensajeUsuario, "OK", resultado, null);
    }

    public static <T> SOAPResponse<T> exito(String mensajeUsuario, String mensajeTecnico) {//el metodo anterior recibe mensaje tecnico pero lo ignora
        return new SOAPResponse<>(true, mensajeUsuario, mensajeTecnico, null, null);
    }

    public static <T> SOAPResponse<T> exito(T resultado, String mensajeUsuario, String mensajeTecnico) {
        return new SOAPResponse(true, mensajeUsuario, mensajeTecnico, resultado, null);
    }

    public static <T> SOAPResponse<T> error(String mensajeUsuario, String mensajeTecnico) {
        return new SOAPResponse(false, mensajeUsuario, mensajeTecnico, null, null);
    }

    public static <T> SOAPResponse<T> error(String mensajeUsuario, String mensajeTecnico, String codigo) {
        return new SOAPResponse(false, mensajeUsuario, mensajeTecnico, null, codigo);
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

    public T getResultado() {
        return resultado;
    }

    public void setResultado(T resultado) {
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
