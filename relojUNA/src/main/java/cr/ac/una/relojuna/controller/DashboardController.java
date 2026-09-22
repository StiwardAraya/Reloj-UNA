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
import cr.ac.una.relojuna.service.DashboardService;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.DashboardDTO;
import java.util.List;
import javafx.scene.chart.PieChart;

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
    private PieChart chartGraficoCircular;
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
    private DashboardService dashboardService;
    private DashboardDTO dashboardActual;

    @Override
    public void initialize() {
        LOG = Logger.getLogger(DashboardController.class.getName());
        dashboardService = new DashboardService();
        FXAnimator.slideInFromRight(root, 20);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
            configurarComboIdiomas();
            cargarDashboard();
        });
    }

    @Override
    public Node getRoot() {
        return root;
    }

    private void cargarDashboard() {

        try {
            Respuesta respuesta = dashboardService.getDashboard();
            if (!respuesta.getEstado()) {
                UIRouter.getInstance().notify(
                        UIRouter.NotificationPosition.BOTTOM_RIGHT, NotificationColor.WARNING, "Dashboard", respuesta.getMensaje());
                return;
            }
            DashboardDTO dashboard = (DashboardDTO) respuesta.getResultado("Dashboard");
            if (dashboard == null) {
                return;
            }

            dashboardActual = dashboard;
            mostrarDatosDashboard(dashboard);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error cargando los datos del Dashboard", ex);
            UIRouter.getInstance().notify(UIRouter.NotificationPosition.BOTTOM_RIGHT, NotificationColor.WARNING, "Dashboard", "No se pudieron cargar los datos del Dashboard.");
        }
    }

    private void mostrarDatosDashboard(DashboardDTO dashboard) {
        lblEntradas.setText(String.valueOf(valorSeguro(dashboard.getTotalEntradas())));
        lblSalidas.setText(String.valueOf(valorSeguro(dashboard.getTotalSalidas())));
        lblMovimientos.setText(String.valueOf(valorSeguro(dashboard.getTotalMovimientos())));
        lblErrores.setText(String.valueOf(valorSeguro(dashboard.getTotalInconsistencias())));
        lblPorcientoEntradas.setText(formatearPorcentaje(dashboard.getAsocieEntradasSemanaAnterior()));
        lblPorcientoSalidas.setText(formatearPorcentaje(dashboard.getAsocieSalidasSemanaAnterior()));

        cargarGrafico(dashboard);
        cargarTopEmpleados(dashboard.getTopEmpleados());
    }

    private int valorSeguro(Integer valor) {
        return valor != null ? valor : 0;
    }

    private String formatearPorcentaje(Double valor) {
        if (valor == null) {
            return "0%";
        }
        return String.format(Locale.US, "%+.1f%%", valor);
    }

    private void cargarGrafico(DashboardDTO dashboard) {

        chartGraficoCircular.getData().clear();

        double porcentajeEntradas = dashboard.getAsocieEntreMarcas() != null
                ? dashboard.getAsocieEntreMarcas() : 0.0;
        double porcentajeSalidas = 100.0 - porcentajeEntradas;
        PieChart.Data dataEntradas = new PieChart.Data(
                bundle.getString("dashboard.lbl.graficoentradas") + String.format(Locale.US, " %.1f%%", porcentajeEntradas), porcentajeEntradas
        );
        PieChart.Data dataSalidas = new PieChart.Data(
                bundle.getString("dashboard.lbl.graficosalidas") + String.format(Locale.US, " %.1f%%", porcentajeSalidas), porcentajeSalidas
        );
        chartGraficoCircular.getData().addAll(dataEntradas, dataSalidas);
    }

    private void cargarTopEmpleados(List<String> empleados) {
        Label[] labels = {lblEmpleado1, lblEmpleado2, lblEmpleado3, lblEmpleado4, lblEmpleado5};
        for (int i = 0; i < labels.length; i++) {
            if (empleados != null && i < empleados.size()) {
                labels[i].setText(empleados.get(i));
            } else {
                labels[i].setText("-");
            }
        }
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
            if (dashboardActual != null) {
                cargarGrafico(dashboardActual);
            }
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
