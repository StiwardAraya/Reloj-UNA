package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class MarcasController extends Controller {

    @FXML
    private Label lblInfo;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblTitulo;
    @FXML
    private MFXTextField txtFolio;
    @FXML
    private MFXDatePicker dtpDesde;
    @FXML
    private MFXDatePicker dtpHasta;
    @FXML
    private MFXButton btnAgregar;
    @FXML
    private TableView<?> tbMarcas;
    @FXML
    private TableColumn<?, ?> clFolio;
    @FXML
    private TableColumn<?, ?> clFecha;
    @FXML
    private MFXCheckbox chkVerInconsistencias;
    @FXML
    private MFXButton btnRevalidar;
    @FXML
    private TableColumn<?, ?> clHora;
    @FXML
    private TableColumn<?, ?> clTipo;
    @FXML
    private TableColumn<?, ?> clControles;

    private final static Logger LOG = Logger.getLogger(MarcasController.class.getName());

    @Override
    public void initialize() {
        FXAnimator.slideInFromRight(root, 20);
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblTitulo.setText(bundle.getString("marcas.lbl.titulo"));
            lblInfo.setText(bundle.getString("marcas.lbl.info"));
            txtFolio.setFloatingText(bundle.getString("marcas.txt.folio"));
            dtpDesde.setFloatingText(bundle.getString("marcas.dtp.desde"));
            dtpHasta.setFloatingText(bundle.getString("marcas.dtp.hasta"));
            btnBuscar.setText(bundle.getString("marcas.btn.buscar"));
            btnAgregar.setText(bundle.getString("marcas.btn.agregar"));
            clFolio.setText(bundle.getString("marcas.col.folio"));
            clHora.setText(bundle.getString("marcas.col.hora"));
            clFecha.setText(bundle.getString("marcas.col.fecha"));
            clTipo.setText(bundle.getString("marcas.col.tipo"));
            clControles.setText(bundle.getString("marcas.col.controles"));
            chkVerInconsistencias.setText(bundle.getString("marcas.chk.inconsistencia"));
            btnRevalidar.setText(bundle.getString("marcas.btn.validar"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at MarcasController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        //falta agregar esto
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        //falta agregar esto
    }

    @FXML
    private void onActionBtnRevalidar(ActionEvent event) {
        //falta agregar esto
    }
}
