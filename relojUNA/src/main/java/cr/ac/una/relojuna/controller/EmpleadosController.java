package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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

    private static final Logger LOG = Logger.getLogger(EmpleadosController.class.getName());

    @Override
    public void initialize() {
        FXAnimator.slideInFromRight(root, 20);
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
            this.txtNombre.setFloatingText(bundle.getString("empleados.txt.nombre"));
            this.txtPApellido.setFloatingText(bundle.getString("empleados.txt.papellido"));
            this.txtSApellido.setFloatingText(bundle.getString("empleados.txt.sapellido"));
            this.dtpFechaNacimiento.setFloatingText(bundle.getString("empleados.dtp.nacimiento"));
            this.txtSalarioHora.setFloatingText(bundle.getString("empleados.txt.salario"));
            this.txtClave.setFloatingText(bundle.getString("empleados.txt.clave"));
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
    private void onActionBtnBuscar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnSubirFoto(ActionEvent event) {
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
    }

    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
    }

}
