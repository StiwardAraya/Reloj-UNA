package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.NotificationColor;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class NotificationController extends Controller {

    private AnchorPane root;
    private Pane notificationTypeSeparator;
    private Label lblTitle;
    private Label lblDescription;

    private String pendingTitle;
    private String pendingDescription;
    private NotificationColor pendingColor;
    @FXML
    private RadioButton rbFecha;
    @FXML
    private ToggleGroup tipoReporte;
    @FXML
    private RadioButton rbHoras;
    @FXML
    private RadioButton rbMes;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextField txtHoraInicio;
    @FXML
    private TextField txtHoraFin;
    @FXML
    private ComboBox<?> cbMes;
    @FXML
    private TextField txtAnio;
    @FXML
    private TableView<?> tblReporte;
    @FXML
    private TableColumn<?, ?> colFecha;
    @FXML
    private TableColumn<?, ?> colHora;
    @FXML
    private TableColumn<?, ?> colFolio;
    @FXML
    private TableColumn<?, ?> colTipo;
    @FXML
    private TableColumn<?, ?> colUsuario;
    @FXML
    private TableColumn<?, ?> colDescripcion;
    @FXML
    private MFXButton btnGenerarReporte;

    public void setup(String title, String description, NotificationColor color) {
        this.pendingTitle = title;
        this.pendingDescription = description;
        this.pendingColor = color;
    }

    @Override
    public void initialize() {
        applyColor(pendingColor);
        lblTitle.setText(pendingTitle);
        lblDescription.setText(pendingDescription);
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
    }

    @Override
    public Node getRoot() {
        return root;
    }

    private void applyColor(NotificationColor color) {
        if (color == null) {
            return;
        }

        for (NotificationColor c : NotificationColor.values()) {
            notificationTypeSeparator.getStyleClass().remove(c.getCssClass());
        }

        notificationTypeSeparator.getStyleClass().add(color.getCssClass());
    }

    @FXML
    private void generarReporte(ActionEvent event) {
    }
}
