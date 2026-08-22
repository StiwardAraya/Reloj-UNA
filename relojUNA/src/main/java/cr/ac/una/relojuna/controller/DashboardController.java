package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.IdiomaItem;
import cr.ac.una.relojuna.util.LanguagesManager;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DashboardController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblDashboard;
    @FXML
    private Label lblResumen;
    @FXML
    private MFXComboBox<IdiomaItem> cmbIdioma;
    @FXML
    private Label lblTotalEntradas;
    @FXML
    private Label lblEntradas;
    @FXML
    private Label lblPorcientoEntradas;
    @FXML
    private Label lblEntradasSAnterior;
    @FXML
    private Label lblTotalSalidas;
    @FXML
    private Label lblSalidas;
    @FXML
    private Label lblPorcientoSalidas;
    @FXML
    private Label lblSalidasSAnterior;
    @FXML
    private Label lblTotalMovimientos;
    @FXML
    private Label lblMovimientos;
    @FXML
    private Label lblEntradasSalidas;
    @FXML
    private Label lblTotalErrores;
    @FXML
    private Label lblErrores;
    @FXML
    private Label lblErroresSistema;
    @FXML
    private Label lblTituloGrafico;
    @FXML
    private Label lblGraficoEntradas;
    @FXML
    private Label lblGraficoSalidas;
    @FXML
    private Label lblTopTitulo;
    @FXML
    private Label lblEmpleado1;
    @FXML
    private Label lblEmpleado2;
    @FXML
    private Label lblEmpleado3;
    @FXML
    private Label lblEmpleado4;
    @FXML
    private Label lblEmpleado5;

    private Logger LOG;

    @Override
    public void initialize() {
        FXAnimator.slideInFromRight(root, 20);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
            configurarComboIdiomas();
        });
        LOG = Logger.getLogger(DashboardController.class.getName());
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblResumen.setText(bundle.getString("dashboard.lbl.resumen"));
            lblTotalEntradas.setText(bundle.getString("dashboard.lbl.totalentradas"));
            lblEntradasSAnterior.setText(bundle.getString("dashboard.lbl.s.anterior"));
            lblSalidasSAnterior.setText(bundle.getString("dashboard.lbl.s.anterior"));
            lblTotalSalidas.setText(bundle.getString("dashboard.lbl.totalsalidas"));
            lblTotalMovimientos.setText(bundle.getString("dashboard.lbl.totalmovimientos"));
            lblEntradasSalidas.setText(bundle.getString("dashboard.lbl.entradassalidas"));
            lblTotalErrores.setText(bundle.getString("dashboard.lbl.totalerrores"));
            lblErroresSistema.setText(bundle.getString("dashboard.lbl.erroressistema"));
            lblTituloGrafico.setText(bundle.getString("dashboard.lbl.titulografico"));
            lblGraficoEntradas.setText(bundle.getString("dashboard.lbl.graficoentradas"));
            lblGraficoSalidas.setText(bundle.getString("dashboard.lbl.graficosalidas"));
            lblTopTitulo.setText(bundle.getString("dashboard.lbl.toptitulo"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at DashboardController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

    // Helpers
    private void configurarComboIdiomas() {
        ObservableList<IdiomaItem> idiomas = FXCollections.observableArrayList(
                new IdiomaItem(bundle.getString("dashboard.lancontrol.es"), new Locale("es", "CR")),
                new IdiomaItem(bundle.getString("dashboard.lancontrol.en"), new Locale("en", "US"))
        );

        cmbIdioma.setItems(idiomas);

        Locale currentLocale = LanguagesManager.getCurrentLocale();
        for (IdiomaItem item : idiomas) {
            if (item.getLocale().getLanguage().equals(currentLocale.getLanguage())) {
                cmbIdioma.selectItem(item);
                break;
            }
        }

        cmbIdioma.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue != oldValue) {
                cambiarIdioma(newValue.getLocale());
            }
        });
    }

    private void cambiarIdioma(Locale locale) {
        LanguagesManager.setLocale(locale);
    }

}
