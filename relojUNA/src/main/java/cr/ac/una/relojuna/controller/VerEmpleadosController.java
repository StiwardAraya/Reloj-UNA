package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.service.EmpleadoService;
import cr.ac.una.relojuna.util.AppContext;
import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.ws.EmpleadoDTO;
import cr.ac.una.relojuna.ws.EmpleadoListDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class VerEmpleadosController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblFiltros;
    @FXML
    private MFXTextField txtFolio;
    @FXML
    private MFXTextField txtCedula;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXTextField txtPApellido;
    @FXML
    private MFXTextField txtSApellido;
    @FXML
    private MFXButton btnFiltrar;
    @FXML
    private TableView<EmpleadoDTO> tbEmpleados;
    @FXML
    private TableColumn<EmpleadoDTO, String> clFolio;
    @FXML
    private TableColumn<EmpleadoDTO, String> clCedula;
    @FXML
    private TableColumn<EmpleadoDTO, String> clNombre;
    @FXML
    private TableColumn<EmpleadoDTO, String> clPrimerApellido;
    @FXML
    private TableColumn<EmpleadoDTO, String> clSegundoApellido;

    private static final Logger LOG = Logger.getLogger(VerEmpleadosController.class.getName());
    private final ObservableList<EmpleadoDTO> empleadosData = FXCollections.observableArrayList();

    @Override
    public void initialize() {
        FXAnimator.fadeSlideInFromBottom(root, 20);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
        configurarColumnas();
        cargarEmpleados("", "", "", "", "");
    }

    // Main
    private void cargarEmpleados(String folio, String cedula, String nombre, String pApellido, String sApellido) {
        EmpleadoService service = new EmpleadoService();
        Respuesta respuesta = service.getEmpleadosByFilters(folio, cedula, nombre, pApellido, sApellido);

        if (!respuesta.getEstado()) {
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.ERROR,
                    bundle.getString(respuesta.getMensaje()),
                    respuesta.getMensaje());
            return;
        }

        List<EmpleadoDTO> empleados = ((EmpleadoListDTO) respuesta.getResultado("Empleados")).getEmpleados();
        empleadosData.setAll(empleados);
    }

    // Helpers
    private void configurarColumnas() {
        this.clFolio.setCellValueFactory(new PropertyValueFactory<>("folio"));
        this.clCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        this.clNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.clPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        this.clSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
        tbEmpleados.setItems(empleadosData);

        tbEmpleados.setRowFactory(tv -> {
            TableRow<EmpleadoDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    EmpleadoDTO empleadoSeleccionado = row.getItem();
                    onDobleClickEmpleado(empleadoSeleccionado);
                }
            });
            return row;
        });
    }

    private void onDobleClickEmpleado(EmpleadoDTO empleado) {
        AppContext.getInstance().set("EmpleadoBusqueda", empleado);
        UIRouter.getInstance().hideModal();
    }

    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
        cargarEmpleados(
                txtFolio.getText(),
                txtCedula.getText(),
                txtNombre.getText(),
                txtPApellido.getText(),
                txtSApellido.getText());
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblFiltros.setText(bundle.getString("empleados.lbl.filtros"));
            txtFolio.setFloatingText(bundle.getString("empleados.txt.folio"));
            txtCedula.setFloatingText(bundle.getString("empleados.txt.cedula"));
            txtNombre.setFloatingText(bundle.getString("empleados.txt.nombre"));
            txtPApellido.setFloatingText(bundle.getString("empleados.txt.papellido"));
            txtSApellido.setFloatingText(bundle.getString("empleados.txt.sapellido"));
            btnFiltrar.setText(bundle.getString("empleados.btn.filtrar"));
            clFolio.setText(bundle.getString("empleados.col.folio"));
            clCedula.setText(bundle.getString("empleados.col.cedula"));
            clNombre.setText(bundle.getString("empleados.col.nombre"));
            clPrimerApellido.setText(bundle.getString("empleados.col.papellido"));
            clSegundoApellido.setText(bundle.getString("empleados.col.sapellido"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at VerEmpleadosController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }
}
