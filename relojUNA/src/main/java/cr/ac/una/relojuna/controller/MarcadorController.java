package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.service.EmpleadoService;
import cr.ac.una.relojuna.service.MarcaService;
import cr.ac.una.relojuna.util.AppContext;
import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.FieldFormat;
import cr.ac.una.relojuna.util.FormValidator;
import cr.ac.una.relojuna.util.Mensaje;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.util.SFXPlayer;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.ws.EmpleadoDTO;
import cr.ac.una.relojuna.ws.MarcaDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MarcadorController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblBienvenida;
    @FXML
    private Label lblDireccion;
    @FXML
    private Label lblReloj;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblDireccionForm;
    @FXML
    private Label lblDireccionEntrada;
    @FXML
    private MFXTextField txtFolio;
    @FXML
    private MFXButton btnContinuar;
    @FXML
    private Label lblContacto;

    Logger LOG;

    @Override
    public void initialize() {
        FXAnimator.fadeSlideInFromBottom(root, 20);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });

        LOG = Logger.getLogger(MarcadorController.class.getName());
        configurarFormatos();
        iniciarReloj();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblBienvenida.setText(bundle.getString("marcador.lbl.bienvenida"));
            lblDireccion.setText(bundle.getString("marcador.lbl.direccion"));
            lblFecha.setText(bundle.getString("marcador.lbl.fecha"));
            lblDireccionForm.setText(bundle.getString("marcador.lbl.direccion.form"));
            lblDireccionEntrada.setText(bundle.getString("marcador.lbl.direccion.entrada"));
            txtFolio.setFloatingText(bundle.getString("marcador.txt.folio"));
            btnContinuar.setText(bundle.getString("marcador.btn.continuar"));
            lblContacto.setText(bundle.getString("marcador.lbl.contacto"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at MarcadorController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

    // Main
    private void marcarFolio() {
        try {
            if (!validarCampos()) {
                return;
            }

            MarcaService service = new MarcaService();
            Respuesta respuesta = service.registrarMarca(txtFolio.getText());
            if (!respuesta.getEstado()) {
                UIRouter.getInstance().notify(UIRouter.NotificationPosition.BOTTOM_RIGHT, NotificationColor.ERROR, bundle.getString("marcas.error.title"), bundle.getString(respuesta.getMensaje()));
                return;
            }

            MarcaDTO marcaDto = (MarcaDTO) respuesta.getResultado("Marca");
            EmpleadoDTO empleadoDto = obtenerDatosEmpleado(marcaDto.getFolioEmpleado());
            AppContext.getInstance().set("empleadoRegistrado", empleadoDto);
            AppContext.getInstance().set("marcaRegistrada", marcaDto);

            UIRouter.getInstance().showModalAndWait("MarcaRegistradaView");
            txtFolio.clear();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error registrando la marca.", ex);
            new Mensaje().show(Alert.AlertType.ERROR, bundle.getString("marcas.error.title"), bundle.getString("marcas.registrar.error"));
        }
    }

    // Helpers
    private void configurarFormatos() {
        txtFolio.delegateSetTextFormatter(FieldFormat.formatoAlfanumerico(6));
    }

    private boolean validarCampos() {
        var result = FormValidator.validate(root, bundle);
        if (!result.isValid()) {
            new Mensaje().show(Alert.AlertType.ERROR, bundle.getString("validation.missing.fields.title"), result.toMessage());
        }
        return result.isValid();
    }

    private void iniciarReloj() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e
                        -> lblReloj.setText(LocalTime.now().format(formato))
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private EmpleadoDTO obtenerDatosEmpleado(String folio) {
        try {
            EmpleadoService service = new EmpleadoService();
            Respuesta respuesta = service.getEmpleadoIdFolio(null, folio);
            if (!respuesta.getEstado()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, bundle.getString("empleados.error.title"), getStage(), bundle.getString(respuesta.getMensaje()));
                return null;
            }
            return (EmpleadoDTO) respuesta.getResultado("Empleado");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error obteniendo los datos del empleado", ex);
            new Mensaje().show(Alert.AlertType.ERROR, bundle.getString("marcas.error.title"), bundle.getString("marcas.error.datosempleado"));
            return null;
        }
    }

    @FXML
    private void onActionBtnContinuar(ActionEvent event) {
        marcarFolio();
    }

    @FXML
    private void onKeyPressedTxtFolio(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            marcarFolio();
        }
    }

}
