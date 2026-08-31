package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView<?> tbEmpleados;
    @FXML
    private TableColumn<?, ?> clId;
    @FXML
    private TableColumn<?, ?> clCedula;
    @FXML
    private TableColumn<?, ?> clNombre;
    @FXML
    private TableColumn<?, ?> clPrimerApellido;
    @FXML
    private TableColumn<?, ?> clSegundoApellido;

    private static final Logger LOG = Logger.getLogger(VerEmpleadosController.class.getName());

    @Override
    public void initialize() {
        FXAnimator.fadeSlideInFromBottom(root, 20);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
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
            clId.setText(bundle.getString("empleados.col.folio"));
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

    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
    }

}
