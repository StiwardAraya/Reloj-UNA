package cr.ac.una.relojunaws.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlRootElement(name = "archivo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArchivoDTO", propOrder = {"nombreArchivo", "tipoContenido", "contenido"})

public class ArchivoDTO implements Serializable {

    @XmlElement(name = "nombreArchivo")
    private String nombreArchivo;
    @XmlElement(name = "tipoContenido")
    private String tipoContenido;
    @XmlElement(name = "contenido")
    private byte[] contenido;

    public ArchivoDTO() {
    }

    public ArchivoDTO(String nombreArchivo, String tipoContenido, byte[] contenido) {

        this.nombreArchivo = nombreArchivo;
        this.tipoContenido = tipoContenido;
        this.contenido = contenido;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
}
