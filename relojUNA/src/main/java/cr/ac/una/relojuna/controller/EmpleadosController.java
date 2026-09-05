package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.model.EmpleadoViewModel;
import cr.ac.una.relojuna.service.EmpleadoService;
import cr.ac.una.relojuna.util.AppContext;
import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.FieldFormat;
import cr.ac.una.relojuna.util.FormValidator;
import cr.ac.una.relojuna.util.Mensaje;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.ws.EmpleadoDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EmpleadosController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblInfo;
    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXTextField txtFolioBusqueda;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private Label lblDatos;
    @FXML
    private Label lblFoto;
    @FXML
    private ImageView imvFoto;
    @FXML
    private MFXButton btnSubirFoto;
    @FXML
    private MFXTextField txtFolio;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXTextField txtPApellido;
    @FXML
    private MFXTextField txtSApellido;
    @FXML
    private MFXDatePicker dtpFechaNacimiento;
    @FXML
    private MFXTextField txtSalarioHora;
    @FXML
    private MFXPasswordField txtClave;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnFiltrar;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private Label lblIndicacionFoto;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private MFXCheckbox chkAdministrador;
    @FXML
    private MFXTextField txtCedula;
    @FXML
    private MFXTextField txtCorreo;

    private static final Logger LOG = Logger.getLogger(EmpleadosController.class.getName());
    private EmpleadoViewModel empleadoView;
    private ObjectProperty<EmpleadoViewModel> empleadoProperty;
    private EmpleadoDTO empleadoDto;

    @Override
    public void initialize() {
        FXAnimator.slideInFromRight(root, 20);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
        configurarFormatos();
        configurarDragAndDrop();
        txtClave.setDisable(true);
        txtFolio.setDisable(true);
        crearEventoCheckAdministrador();
        this.empleadoProperty = new SimpleObjectProperty<>();
        bindEmpleado();
        cargarValoresPorDefecto();
    }

    //Main
    private void obtenerEmpleado() {
        try {
            EmpleadoService service = new EmpleadoService();
            Respuesta respuesta = service.getEmpleadoIdFolio(txtId.getText(), txtFolioBusqueda.getText());
            if (!respuesta.getEstado()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, bundle.getString("empleados.error.title"), getStage(), bundle.getString(respuesta.getMensaje()));
                return;
            }
            cargarEmpleado((EmpleadoDTO) respuesta.getResultado("Empleado"));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error buscando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, bundle.getString("empleados.error.title"), getStage(), bundle.getString("empleados.get.error"));
        }
    }

    private void verEmpleados() {
        this.cargarValoresPorDefecto();
        AppContext.getInstance().set("EmpleadoBusqueda", empleadoDto);
        UIRouter.getInstance().showModalAndWait("VerEmpleadosView");
        empleadoDto = (EmpleadoDTO) AppContext.getInstance().get("EmpleadoBusqueda");
        if (empleadoDto.getId() == null) {
            LOG.log(Level.SEVERE, "Ocurrió un error obteniendo el empleado desde busqueda");
            return;
        }
        cargarEmpleado(empleadoDto);
    }

    private void eliminarEmpleado() {
        try {
            EmpleadoService service = new EmpleadoService();
            Respuesta respuesta = service.eliminarEmpleado(txtId.getText());
            if (!respuesta.getEstado()) {
                new Mensaje().show(Alert.AlertType.ERROR, bundle.getString("empleados.error.title"), bundle.getString(respuesta.getMensaje()));
                return;
            }
            UIRouter.getInstance().notify(UIRouter.NotificationPosition.BOTTOM_RIGHT, NotificationColor.SUCCESS, bundle.getString("empleado.eliminar.exito.title"), bundle.getString(respuesta.getMensaje()));
            cargarValoresPorDefecto();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error eliminando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, bundle.getString("empleados.error.title"), getStage(), bundle.getString("empleados.delete.error"));
        }
    }

    private void guardarEmpleado() {
        try {
            // FIXME: Validar correo electronico correctamente
            if (!validarCampos()) {
                return;
            }

            EmpleadoService service = new EmpleadoService();
            empleadoDto = empleadoView.toDto();
            Respuesta respuesta = service.guardarEmpleado(empleadoDto);
            if (!respuesta.getEstado()) {
                new Mensaje().show(Alert.AlertType.ERROR, bundle.getString("empleados.error.title"), bundle.getString(respuesta.getMensaje()));
                return;
            }
            cargarEmpleado((EmpleadoDTO) respuesta.getResultado("Empleado"));
            UIRouter.getInstance().notify(UIRouter.NotificationPosition.BOTTOM_RIGHT, NotificationColor.SUCCESS, bundle.getString("empleado.guardar.exito.title"), bundle.getString(respuesta.getMensaje()));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().show(Alert.AlertType.ERROR, bundle.getString("empleados.error.title"), bundle.getString("empleados.guardar.error"));

        }
    }

    //Helpers
    private void subirFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("empleados.lbl.indicacion.foto"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) root.getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);
        cargarImagen(archivoSeleccionado);
    }

    private void cargarEmpleado(EmpleadoDTO dto) {
        empleadoDto = dto;
        empleadoView = new EmpleadoViewModel();
        empleadoView.fromDto(empleadoDto);
        empleadoProperty.set(empleadoView);
        txtId.setDisable(true);
        txtFolioBusqueda.setDisable(true);
        btnEliminar.setDisable(false);
    }

    private void logout() {
        UIRouter.getInstance().close(UIRouter.Position.LEFT);
        UIRouter.getInstance().close(UIRouter.Position.CENTER);
        UIRouter.getInstance().show("MainHeaderView", UIRouter.Position.TOP);
        UIRouter.getInstance().show("MainFooterView", UIRouter.Position.BOTTOM);
        UIRouter.getInstance().show("LoginView", UIRouter.Position.CENTER);
    }

    private void cargarImagen(File archivo) {
        if (archivo == null) {
            return;
        }
        try {
            Image imagen = new Image(archivo.toURI().toString());
            imvFoto.setImage(imagen);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error cargando la imagen seleccionada", ex);
        }
    }

    private boolean esImagenValida(File archivo) {
        String nombre = archivo.getName().toLowerCase();
        return nombre.endsWith(".png") || nombre.endsWith(".jpg") || nombre.endsWith(".jpeg");
    }

    private void configurarDragAndDrop() {
        imvFoto.setOnDragOver(this::onDragOverFoto);
        imvFoto.setOnDragDropped(this::onDragDroppedFoto);
    }

    private void onDragOverFoto(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles() && db.getFiles().stream().anyMatch(this::esImagenValida)) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    private void onDragDroppedFoto(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean exito = false;

        if (db.hasFiles()) {
            List<File> archivos = db.getFiles();
            File imagenValida = archivos.stream()
                    .filter(this::esImagenValida)
                    .findFirst()
                    .orElse(null);

            if (imagenValida != null) {
                cargarImagen(imagenValida);
                exito = true;
            } else {
                LOG.log(Level.SEVERE, "Formato de imagen invalido");
            }
        }

        event.setDropCompleted(exito);
        event.consume();
    }

    private void cargarValoresPorDefecto() {
        this.empleadoDto = new EmpleadoDTO();
        this.empleadoView = new EmpleadoViewModel();
        this.empleadoView.setActivo("A");
        this.empleadoView.setEsAdmin("N");
        this.empleadoProperty.set(empleadoView);
        txtFolioBusqueda.clear();
        txtFolioBusqueda.setDisable(false);
        txtId.setDisable(false);
        txtId.clear();
        txtId.requestFocus();
        btnEliminar.setDisable(true);
    }

    private void bindEmpleado() {
        try {
            empleadoProperty.addListener((obs, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtId.textProperty().unbind();
                    txtFolio.textProperty().unbindBidirectional(oldVal.folioProperty());
                    txtCedula.textProperty().unbindBidirectional(oldVal.cedulaProperty());
                    txtNombre.textProperty().unbindBidirectional(oldVal.nombreProperty());
                    txtPApellido.textProperty().unbindBidirectional(oldVal.primerApellidoProperty());
                    txtSApellido.textProperty().unbindBidirectional(oldVal.segundoApellidoProperty());
                    dtpFechaNacimiento.valueProperty().unbindBidirectional(oldVal.fechaNacimientoProperty());
                    chkAdministrador.selectedProperty().unbindBidirectional(oldVal.esAdminProperty());
                    txtSalarioHora.textProperty().unbindBidirectional(oldVal.salarioHoraProperty());
                    txtClave.textProperty().unbindBidirectional(oldVal.claveProperty());
                    txtCorreo.textProperty().unbindBidirectional(oldVal.correoProperty());
                    chkActivo.selectedProperty().unbindBidirectional(oldVal.activoProperty());
                    imvFoto.imageProperty().unbindBidirectional(oldVal.fotoProperty());
                }
                if (newVal != null) {
                    if (newVal.idProperty().get() != null
                            && !newVal.idProperty().get().isBlank()) {
                        txtId.textProperty().bind(newVal.idProperty());
                    }
                    txtFolio.textProperty().bindBidirectional(newVal.folioProperty());
                    txtCedula.textProperty().bindBidirectional(newVal.cedulaProperty());
                    txtNombre.textProperty().bindBidirectional(newVal.nombreProperty());
                    txtPApellido.textProperty().bindBidirectional(newVal.primerApellidoProperty());
                    txtSApellido.textProperty().bindBidirectional(newVal.segundoApellidoProperty());
                    dtpFechaNacimiento.valueProperty().bindBidirectional(newVal.fechaNacimientoProperty());
                    chkAdministrador.selectedProperty().bindBidirectional(newVal.esAdminProperty());
                    txtSalarioHora.textProperty().bindBidirectional(newVal.salarioHoraProperty());
                    txtClave.textProperty().bindBidirectional(newVal.claveProperty());
                    txtCorreo.textProperty().bindBidirectional(newVal.correoProperty());
                    chkActivo.selectedProperty().bindBidirectional(newVal.activoProperty());
                    imvFoto.imageProperty().bindBidirectional(newVal.fotoProperty());
                }
            });

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error bindeando el empleado", ex);
        }
    }

    private void crearEventoCheckAdministrador() {
        chkAdministrador.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txtClave.setDisable(false);
            } else {
                txtClave.setDisable(true);
                txtClave.clear();
            }
        });
    }

    private void configurarFormatos() {
        txtId.delegateSetTextFormatter(FieldFormat.formatoSoloNumeros());
        txtFolio.delegateSetTextFormatter(FieldFormat.formatoAlfanumerico(6));
        txtFolioBusqueda.delegateSetTextFormatter(FieldFormat.formatoAlfanumerico(6));
        txtCedula.delegateSetTextFormatter(FieldFormat.formatoSoloNumeros(9));
        txtNombre.delegateSetTextFormatter(FieldFormat.formatoSoloLetras(30));
        txtPApellido.delegateSetTextFormatter(FieldFormat.formatoSoloLetras(30));
        txtSApellido.delegateSetTextFormatter(FieldFormat.formatoSoloLetras(30));
        txtSalarioHora.delegateSetTextFormatter(FieldFormat.formatoSoloNumeros());
        txtClave.delegateSetTextFormatter(FieldFormat.formatoLimiteCaracteres(16));
        txtCorreo.delegateSetTextFormatter(FieldFormat.formatoCorreoElectronico(50));
    }

    private boolean validarCampos() {
        var result = FormValidator.validate(root, bundle);
        if (!result.isValid()) {
            new Mensaje().show(Alert.AlertType.ERROR, bundle.getString("validation.missing.fields.title"), result.toMessage());
        }
        return result.isValid();
    }

    private void advertenciaCheckActivo() {
        if (!this.chkActivo.selectedProperty().get()) {
            new Mensaje().show(Alert.AlertType.WARNING, bundle.getString("empleados.activo.adv.title"), bundle.getString("empleados.activo.adv"));
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        obtenerEmpleado();
    }

    @FXML
    private void onActionBtnSubirFoto(ActionEvent event) {
        subirFoto();
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Empleado", getStage(), "¿Esta seguro que desea limpiar el registro?")) {
            cargarValoresPorDefecto();
        }
    }

    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
        verEmpleados();
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        eliminarEmpleado();
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        guardarEmpleado();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            this.lblTitulo.setText(bundle.getString("empleados.lbl.titulo"));
            this.lblInfo.setText(bundle.getString("empleados.lbl.info"));
            this.txtId.setFloatingText(bundle.getString("empleados.txt.id"));
            this.txtFolioBusqueda.setFloatingText(bundle.getString("empleados.txt.folio"));
            this.txtFolio.setFloatingText(bundle.getString("empleados.txt.folio"));
            this.btnBuscar.setText(bundle.getString("empleados.btn.buscar"));
            this.lblDatos.setText(bundle.getString("empleados.lbl.datos"));
            this.chkActivo.setText(bundle.getString("empleados.chk.activo"));
            this.chkAdministrador.setText(bundle.getString("empleados.chk.admin"));
            this.lblFoto.setText(bundle.getString("empleados.lbl.foto"));
            this.lblIndicacionFoto.setText(bundle.getString("empleados.lbl.indicacion.foto"));
            this.btnSubirFoto.setText(bundle.getString("empleados.btn.subir"));
            this.txtCedula.setFloatingText(bundle.getString("empleados.txt.cedula"));
            this.txtNombre.setFloatingText(bundle.getString("empleados.txt.nombre"));
            this.txtPApellido.setFloatingText(bundle.getString("empleados.txt.papellido"));
            this.txtSApellido.setFloatingText(bundle.getString("empleados.txt.sapellido"));
            this.dtpFechaNacimiento.setFloatingText(bundle.getString("empleados.dtp.nacimiento"));
            this.txtSalarioHora.setFloatingText(bundle.getString("empleados.txt.salario"));
            this.txtClave.setFloatingText(bundle.getString("empleados.txt.clave"));
            this.txtCorreo.setFloatingText(bundle.getString("empleados.txt.correo"));
            this.btnNuevo.setText(bundle.getString("empleados.btn.nuevo"));
            this.btnFiltrar.setText(bundle.getString("empleados.btn.filtrar"));
            this.btnEliminar.setText(bundle.getString("empleados.btn.eliminar"));
            this.btnGuardar.setText(bundle.getString("empleados.btn.guardar"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at EmpleadosController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            txtFolioBusqueda.clear();
            obtenerEmpleado();
        }
    }

    @FXML
    private void onKeyPressedTxtFolio(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            txtId.clear();
            obtenerEmpleado();
        }
    }

    @FXML
    private void onActionChkActivo(ActionEvent event) {
        advertenciaCheckActivo();
    }
}
