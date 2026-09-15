package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
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

public class PlanillasController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblInfo;
    @FXML
    private MFXComboBox<?> mcbMes;
    @FXML
    private MFXComboBox<?> mcbAnno;
    @FXML
    private MFXCheckbox chkGenerada;
    @FXML
    private MFXButton btnCalcularPlanilla;
    @FXML
    private Label lblEmpleados;
    @FXML
    private Label lblCantEmpleados;
    @FXML
    private Label lblHorasOrd;
    @FXML
    private Label lblCantHorasOrd;
    @FXML
    private Label lblHorasExt;
    @FXML
    private Label lblCantHorasExt;
    @FXML
    private Label lblHorasNoc;
    @FXML
    private Label lblCantHorasNoc;
    @FXML
    private Label lblTotalPagar;
    @FXML
    private Label lblMontoTotalPagar;
    @FXML
    private Label lblResumen;
    @FXML
    private TableView<?> tbDetallesPlanilla;
    @FXML
    private TableColumn<?, ?> clFolio;
    @FXML
    private TableColumn<?, ?> clEmpleado;
    @FXML
    private TableColumn<?, ?> clSalarioHora;
    @FXML
    private TableColumn<?, ?> clHorasOrd;
    @FXML
    private TableColumn<?, ?> clHorasExtras;
    @FXML
    private TableColumn<?, ?> clHorasDobles;
    @FXML
    private TableColumn<?, ?> clTotalHoras;
    @FXML
    private MFXButton btnGenerarPlanilla;
    @FXML
    private MFXCheckbox chkEnviarCorreo;

    private final static Logger LOG = Logger.getLogger(PlanillasController.class.getName());

    @Override
    public void initialize() {
        FXAnimator.slideInFromRight(root, 20);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
        cargarValoresPorDefecto();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblTitulo.setText(bundle.getString("planillas.lbl.titulo"));
            lblInfo.setText(bundle.getString("planillas.lbl.info"));
            mcbMes.setFloatingText(bundle.getString("planillas.mcb.mes"));
            mcbAnno.setFloatingText(bundle.getString("planillas.mcb.anno"));
            chkGenerada.setText(bundle.getString("planillas.chk.generada"));
            btnCalcularPlanilla.setText(bundle.getString("planillas.btn.calcular.planilla"));
            lblEmpleados.setText(bundle.getString("planillas.lbl.empleados"));
            lblHorasOrd.setText(bundle.getString("planillas.lbl.horasord"));
            lblHorasExt.setText(bundle.getString("planillas.lbl.horasext"));
            lblHorasNoc.setText(bundle.getString("planillas.lbl.horasnoc"));
            lblTotalPagar.setText(bundle.getString("planillas.lbl.total.pagar"));
            lblResumen.setText(bundle.getString("planillas.lbl.resumen"));
            clFolio.setText(bundle.getString("planillas.cl.folio"));
            clEmpleado.setText(bundle.getString("planillas.cl.empleado"));
            clSalarioHora.setText(bundle.getString("planillas.cl.salario.hora"));
            clHorasOrd.setText(bundle.getString("planillas.cl.horasord"));
            clHorasExtras.setText(bundle.getString("planillas.cl.horasext"));
            clHorasDobles.setText(bundle.getString("planillas.cl.horasdobles"));
            clTotalHoras.setText(bundle.getString("planillas.cl.total.horas"));
            btnGenerarPlanilla.setText(bundle.getString("planillas.btn.generar.planilla"));
            chkEnviarCorreo.setText(bundle.getString("planillas.chk.enviar.correo"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at PlanillasController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

    // Main
    private void calcularPlanilla() {
        // TODO: Calcular los datos de la planilla y sus detalles y mostrarlos al usuario
    }

    private void generarPlanilla() {
        // TODO: Recoger los datos de la planilla y enviarlos al servidor
    }

    // Helpers
    private void cargarValoresPorDefecto() {
        mcbAnno.getItems().clear();
        mcbMes.getItems().clear();
        btnGenerarPlanilla.setDisable(true);
        chkEnviarCorreo.setDisable(true);
    }

    @FXML
    private void onActionBtnCalcularPlanilla(ActionEvent event) {
        calcularPlanilla();
    }

    @FXML
    private void onActionBtnGenerarPlanilla(ActionEvent event) {
        generarPlanilla();
    }

}
