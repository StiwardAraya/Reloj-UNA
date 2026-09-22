package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.AppContext;
import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.ImageConverter;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.ws.EmpleadoDTO;
import cr.ac.una.relojuna.ws.MarcaDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MarcaRegistradaController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblSaludoDespedida;
    @FXML
    private Label lblFolio;
    @FXML
    private ImageView imvFoto;
    @FXML
    private Label lblNombreApellidos;
    @FXML
    private Label lblCedula;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblRegistrada;
    @FXML
    private Label lblHora;

    private MarcaDTO marcaDto;
    private EmpleadoDTO empleadoDto;

    private final static Logger LOG = Logger.getLogger(MarcaRegistradaController.class.getName());

    @Override
    public void initialize() {
        marcaDto = (MarcaDTO) AppContext.getInstance().get("marcaRegistrada");
        empleadoDto = (EmpleadoDTO) AppContext.getInstance().get("empleadoRegistrado");
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
            FXAnimator.fadeSlideInFromBottom(root, 50);
            LocalDate fechaNacimientoParsed = LocalDate.parse(empleadoDto.getFechaNacimiento());
            if (fechaNacimientoParsed.getDayOfMonth() == LocalDate.now().getDayOfMonth()
                    && fechaNacimientoParsed.getMonth() == LocalDate.now().getMonth()
                    && marcaDto.getTipo().equalsIgnoreCase("E")) {
                FXAnimator.felizCumpleanos(root, 300, 110);
            }
        });
        cargarDatos();
        PauseTransition pausa = new PauseTransition(Duration.seconds(6));
        pausa.setOnFinished(event -> UIRouter.getInstance().hideModal());
        pausa.play();
    }

    private void cargarDatos() {
        LocalDateTime fechaHora = LocalDateTime.parse(marcaDto.getFechaHora());
        DateTimeFormatter formatoTiempo = DateTimeFormatter.ofPattern("HH:mm:ss");
        lblFolio.setText(empleadoDto.getFolio());
        imvFoto.setImage(ImageConverter.toImage(empleadoDto.getFoto()));
        lblNombreApellidos.setText(
                empleadoDto.getNombre()
                        .concat(" ")
                        .concat(empleadoDto.getPrimerApellido())
                        .concat(" ")
                        .concat(empleadoDto.getSegundoApellido())
        );
        lblCedula.setText(empleadoDto.getCedula());
        lblFecha.setText(fechaHora.toLocalDate().toString());
        lblHora.setText(fechaHora.toLocalTime().format(formatoTiempo));
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblRegistrada.setText(bundle.getString("marcas.registrar.marcado"));
            if (this.marcaDto.getTipo().equalsIgnoreCase("E")) {
                lblSaludoDespedida.setText(bundle.getString("marcas.registrar.saludo"));
            } else {
                lblSaludoDespedida.setText(bundle.getString("marcas.registrar.despedida"));
            }
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at MarcaRegistradaController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

}
