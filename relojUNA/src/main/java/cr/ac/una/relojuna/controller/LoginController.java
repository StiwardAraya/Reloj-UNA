package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.FieldFormat;
import cr.ac.una.relojuna.util.FormValidator;
import cr.ac.una.relojuna.util.Mensaje;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.model.LoginViewModel;
import cr.ac.una.relojuna.service.EmpleadoService;
import cr.ac.una.relojuna.util.AppContext;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.EmpleadoDTO;
import cr.ac.una.relojuna.ws.LoginRequestDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class LoginController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblIniciarSesion;
    @FXML
    private Label lblAdministradores;
    @FXML
    private MFXTextField txtFolio;
    @FXML
    private MFXPasswordField txtClave;
    @FXML
    private Hyperlink linkRecuperacion;
    @FXML
    private MFXButton btnIngresar;

    private Logger LOG;
    private LoginViewModel loginModel;
    private LoginRequestDTO loginRequestDTO;
    private EmpleadoService empleadoService;

    // Heredados
    @Override
    public void initialize() {
        FXAnimator.fadeSlideInFromBottom(root, 20);
        LOG = Logger.getLogger(LoginController.class.getName());
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
        loginModel = new LoginViewModel();
        loginRequestDTO = new LoginRequestDTO();
        empleadoService = new EmpleadoService();
        configurarFormatos();
        bindLogin();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblIniciarSesion.setText(bundle.getString("login.lbl.iniciarsesion"));
            lblAdministradores.setText(bundle.getString("login.lbl.administradores"));
            txtFolio.setFloatingText(bundle.getString("login.txt.folio"));
            txtClave.setFloatingText(bundle.getString("login.txt.clave"));
            linkRecuperacion.setText(bundle.getString("login.link.recuperacion"));
            btnIngresar.setText(bundle.getString("login.btn.ingresar"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at LoginController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

    // Main
    private void ingresar() {
        if (!validarCampos()) {
            return;
        }

        loginRequestDTO = loginModel.toDto();
        try {
            Respuesta respuesta = empleadoService.autenticarEmpleado(loginRequestDTO);
            if (respuesta.getEstado()) {
                contextualizarEmpleado((EmpleadoDTO) respuesta.getResultado("Empleado"));
                cerrarVentanasActuales();
                abrirSistema();
            } else {
                UIRouter.getInstance().notify(UIRouter.NotificationPosition.BOTTOM_RIGHT, NotificationColor.ERROR, "Login", bundle.getString(respuesta.getMensaje()));
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception processing 'respuesta' item for authentication LoginController.ingresar", e);
        }
    }

    // Helpers
    private boolean validarCampos() {
        var result = FormValidator.validate(root, bundle);
        if (!result.isValid()) {
            new Mensaje().show(Alert.AlertType.ERROR, bundle.getString("validation.missing.fields.title"), result.toMessage());
        }
        return result.isValid();
    }

    private void cerrarVentanasActuales() {
        UIRouter.getInstance().close(UIRouter.Position.BOTTOM);
        UIRouter.getInstance().close(UIRouter.Position.TOP);
        UIRouter.getInstance().close(UIRouter.Position.CENTER);
    }

    private void abrirSistema() {
        UIRouter.getInstance().show("MainMenuView", UIRouter.Position.LEFT);
    }

    private void bindLogin() {
        this.txtFolio.textProperty().bindBidirectional(loginModel.folioProperty());
        this.txtClave.textProperty().bindBidirectional(loginModel.claveProperty());
    }

    private void configurarFormatos() {
        txtFolio.delegateSetTextFormatter(FieldFormat.formatoAlfanumerico(6));
        txtClave.delegateSetTextFormatter(FieldFormat.formatoLimiteCaracteres(16));
    }

    private void contextualizarEmpleado(EmpleadoDTO empleadoDto) {
        AppContext.getInstance().set("EmpleadoLogueado", empleadoDto.getId());
    }

    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        ingresar();
    }

    @FXML
    private void onKeyPressedLogin(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            ingresar();
        }
    }

}
