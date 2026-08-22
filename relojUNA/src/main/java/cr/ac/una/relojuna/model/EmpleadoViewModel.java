package cr.ac.una.relojuna.model;

import cr.ac.una.relojuna.util.ImageConverter;
import cr.ac.una.relojuna.ws.EmpleadoDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Predicate;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class EmpleadoViewModel {

    Predicate<SimpleStringProperty> esIdValido = idEmpleado -> idEmpleado.get() != null && !idEmpleado.get().isBlank();

    private final SimpleStringProperty id = new SimpleStringProperty("");
    private final SimpleStringProperty folio = new SimpleStringProperty("");
    private final SimpleStringProperty cedula = new SimpleStringProperty("");
    private final SimpleStringProperty nombre = new SimpleStringProperty("");
    private final SimpleStringProperty primerApellido = new SimpleStringProperty("");
    private final SimpleStringProperty segundoApellido = new SimpleStringProperty("");
    private final SimpleObjectProperty<LocalDate> fechaNacimiento = new SimpleObjectProperty<>(LocalDate.now());
    private final SimpleObjectProperty<Image> foto = new SimpleObjectProperty<>();
    private final SimpleStringProperty salarioHora = new SimpleStringProperty("");
    private final SimpleBooleanProperty esAdmin = new SimpleBooleanProperty(false);
    private final SimpleStringProperty clave = new SimpleStringProperty("");
    private final SimpleBooleanProperty activo = new SimpleBooleanProperty(false);

    public Long getId() {
        if (esIdValido.test(this.id)) {
            return Long.valueOf(this.id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getFolio() {
        return folio.get();
    }

    public void setFolio(String folio) {
        this.folio.set(folio);
    }

    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getPrimerApellido() {
        return primerApellido.get();
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido.set(primerApellido);
    }

    public String getSegundoApellido() {
        return segundoApellido.get();
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido.set(segundoApellido);
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }

    public byte[] getFoto() {
        return ImageConverter.toByte(foto.get());
    }

    public void setFoto(byte[] foto) {
        this.foto.set(ImageConverter.toImage(foto));
    }

    public BigDecimal getSalarioHora() {
        return new BigDecimal(this.salarioHora.get());
    }

    public void setSalarioHora(BigDecimal salarioHora) {
        this.salarioHora.set(salarioHora.toString());
    }

    public String getEsAdmin() {
        return esAdmin.get() ? "S" : "N";
    }

    public void setEsAdmin(String esAdmin) {
        this.esAdmin.set(esAdmin.equals("S"));
    }

    public String getClave() {
        return clave.get();
    }

    public void setClave(String clave) {
        this.clave.set(clave);
    }

    public String getActivo() {
        return activo.get() ? "A" : "I";
    }

    public void setActivo(String activo) {
        this.activo.set(activo.equals("A"));
    }

    // PROPERTIES
    public SimpleStringProperty idProperty() {
        return id;
    }

    public SimpleStringProperty folioProperty() {
        return folio;
    }

    public SimpleStringProperty cedulaProperty() {
        return cedula;
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public SimpleStringProperty primerApellidoProperty() {
        return primerApellido;
    }

    public SimpleStringProperty segundoApellidoProperty() {
        return segundoApellido;
    }

    public SimpleObjectProperty<LocalDate> fechaNacimientoProperty() {
        return fechaNacimiento;
    }

    public SimpleObjectProperty<Image> fotoProperty() {
        return foto;
    }

    public SimpleStringProperty salarioHoraProperty() {
        return salarioHora;
    }

    public SimpleBooleanProperty esAdminProperty() {
        return esAdmin;
    }

    public SimpleStringProperty claveProperty() {
        return clave;
    }

    public SimpleBooleanProperty activoProperty() {
        return activo;
    }

    public EmpleadoDTO toDto() {
        EmpleadoDTO empleadoDto = new EmpleadoDTO();
        empleadoDto.setId(this.getId());
        empleadoDto.setFolio(this.getFolio());
        empleadoDto.setCedula(this.getCedula());
        empleadoDto.setNombre(this.getNombre());
        empleadoDto.setPrimerApellido(this.getPrimerApellido());
        empleadoDto.setSegundoApellido(this.getSegundoApellido());
        empleadoDto.setFechaNacimiento(this.getFechaNacimiento().toString());
        empleadoDto.setFoto(this.getFoto());
        empleadoDto.setSalarioHora(this.getSalarioHora());
        empleadoDto.setEsAdmin(this.getEsAdmin());
        empleadoDto.setClave(this.getClave());
        empleadoDto.setActivo(this.getActivo());
        return empleadoDto;
    }

    public void fromDto(EmpleadoDTO dto) {
        this.setId(dto.getId());
        this.setFolio(dto.getFolio());
        this.setCedula(dto.getCedula());
        this.setNombre(dto.getNombre());
        this.setPrimerApellido(dto.getPrimerApellido());
        this.setSegundoApellido(dto.getSegundoApellido());
        this.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        this.setFoto(dto.getFoto());
        this.setSalarioHora(dto.getSalarioHora());
        this.setEsAdmin(dto.getEsAdmin());
        this.setClave(dto.getClave());
        this.setActivo(dto.getActivo());
    }
}
