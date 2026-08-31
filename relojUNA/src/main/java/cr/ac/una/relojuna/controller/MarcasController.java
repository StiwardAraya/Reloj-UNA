package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.FXAnimator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ResourceBundle;
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
    private TableColumn<?, ?> clNombre;
    @FXML
    private TableColumn<?, ?> clFecha;
    @FXML
    private TableColumn<?, ?> clEntrada;
    @FXML
    private TableColumn<?, ?> clSalida;
    @FXML
    private MFXCheckbox chkVerInconsistencias;
    @FXML
    private MFXButton btnRevalidar;

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
        // TODO
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnRevalidar(ActionEvent event) {
    }
}
