package cr.ac.una.relojunaws.model;

import cr.ac.una.relojunaws.model.dto.EmpleadoDTO;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.QueryHint;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "EMPLEADO", schema = "relojUNA")
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findById", query = "SELECT e FROM Empleado e WHERE e.id = :id"),
    @NamedQuery(name = "Empleado.findByIdFolio", query = "SELECT e FROM Empleado e WHERE e.id = :id OR e.folio = :folio"),
    @NamedQuery(name = "Empleado.findByFilters", query = "SELECT e FROM Empleado e WHERE UPPER(e.correo) LIKE :correo AND UPPER(e.cedula) LIKE :cedula AND UPPER(e.nombre) LIKE :nombre AND UPPER(e.primerApellido) LIKE :primerApellido AND UPPER(e.segundoApellido) LIKE :segundoApellido", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
    @NamedQuery(name = "Empleado.authenticate", query = "SELECT e FROM Empleado e WHERE e.folio = :folio AND e.clave = :clave AND e.activo = 'A' AND e.esAdmin = 'S'", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
})
public class Empleado implements Serializable {

    @Id
    @SequenceGenerator(name = "EMP_ID_GENERATOR", sequenceName = "relojUNA.EMP_SEQ_01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "folio")
    private String folio;

    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;

    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @Column(name = "primer_apellido")
    private String primerApellido;

    @Basic(optional = false)
    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Basic(optional = false)
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Basic(optional = false)
    @Column(name = "foto")
    private byte[] foto;

    @Basic(optional = false)
    @Column(name = "salario_hora")
    private BigDecimal salarioHora;

    @Basic(optional = false)
    @Column(name = "es_admin")
    private String esAdmin;

    @Column(name = "clave")
    private String clave;

    @Column(name = "correo")
    @Basic(optional = false)
    private String correo;

    @Basic(optional = false)
    @Column(name = "activo")
    private String activo;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @Version
    @Basic(optional = false)
    @Column(name = "version_empleado")
    private Long version;

    public Empleado() {
    }

    public Empleado(Long id) {
        this.id = id;
    }

    public Empleado(EmpleadoDTO dto) {
        this.id = dto.getId();
        actualizar(dto);
    }

    public final void actualizar(EmpleadoDTO dto) {
        this.folio = dto.getFolio();
        this.cedula = dto.getCedula();
        this.nombre = dto.getNombre();
        this.primerApellido = dto.getPrimerApellido();
        this.segundoApellido = dto.getSegundoApellido();
        this.fechaNacimiento = dto.getFechaNacimiento();
        this.foto = dto.getFoto();
        this.salarioHora = dto.getSalarioHora();
        this.esAdmin = dto.getEsAdmin();
        this.clave = dto.getClave();
        this.correo = dto.getCorreo();
        this.activo = dto.getActivo();
        this.fechaBaja = dto.getFechaBaja();
        this.version = dto.getVersion();
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.id);
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
        final Empleado other = (Empleado) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + '}';
    }

}
