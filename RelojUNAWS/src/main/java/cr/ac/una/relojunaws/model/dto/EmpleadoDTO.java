package cr.ac.una.relojunaws.model.dto;

import cr.ac.una.relojunaws.model.Empleado;
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

@XmlRootElement(name = "empleado")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmpleadoDTO", propOrder = {
    "id", "folio", "cedula", "nombre", "primerApellido", "segundoApellido",
    "fechaNacimiento", "foto", "salarioHora", "esAdmin", "clave", "activo", "fechaBaja"
})
public class EmpleadoDTO implements Serializable {

    @XmlElement(name = "id")
    Long id;

    @XmlElement(name = "folio")
    String folio;

    @XmlElement(name = "cedula")
    String cedula;

    @XmlElement(name = "nombre")
    String nombre;

    @XmlElement(name = "primerApellido")
    String primerApellido;

    @XmlElement(name = "segundoApellido")
    String segundoApellido;

    @XmlElement(name = "fechaNacimiento")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    LocalDate fechaNacimiento;

    @XmlElement(name = "foto")
    byte[] foto;

    @XmlElement(name = "salarioHora")
    BigDecimal salarioHora;

    @XmlElement(name = "esAdmin")
    String esAdmin;

    @XmlElement(name = "clave")
    String clave;

    @XmlElement(name = "activo")
    String activo;

    @XmlElement(name = "fechaBaja")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    LocalDate fechaBaja;

    public EmpleadoDTO() {
    }

    public EmpleadoDTO(Long id) {
        this.id = id;
    }

    public EmpleadoDTO(Empleado e) {
        this();
        this.id = e.getId();
        this.folio = e.getFolio();
        this.cedula = e.getCedula();
        this.nombre = e.getNombre();
        this.primerApellido = e.getPrimerApellido();
        this.segundoApellido = e.getSegundoApellido();
        this.fechaNacimiento = e.getFechaNacimiento();
        this.foto = e.getFoto();
        this.salarioHora = e.getSalarioHora();
        this.esAdmin = e.getEsAdmin();
        this.clave = e.getClave();
        this.activo = e.getActivo();
        this.fechaBaja = e.getFechaBaja();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public BigDecimal getSalarioHora() {
        return salarioHora;
    }

    public void setSalarioHora(BigDecimal salarioHora) {
        this.salarioHora = salarioHora;
    }

    public String getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(String esAdmin) {
        this.esAdmin = esAdmin;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
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
        final EmpleadoDTO other = (EmpleadoDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "EmpleadoDTO{" + "id=" + id + ", folio=" + folio + ", cedula=" + cedula + ", nombre=" + nombre + ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", fechaNacimiento=" + fechaNacimiento + ", foto=" + foto + ", salarioHora=" + salarioHora + ", esAdmin=" + esAdmin + ", clave=" + "***" + ", activo=" + activo + ", fechaBaja=" + fechaBaja + '}';
    }

}
