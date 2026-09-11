package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.AppContext;
import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.Mensaje;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainMenuController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblMiEmpresa;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnEmpleados;
    @FXML
    private Button btnPlanillas;
    @FXML
    private Button btnConsultas;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lblFooterMiEmpresa;
    @FXML
    private Label lblDerechos;
    @FXML
    private Button btnMarcas;
    @FXML
    private Label lblFolioAdmin;

    private enum Vista {
        DASHBOARD,
        EMPLEADOS,
        MARCAS,
        PLANILLAS,
        CONSULTAS,
        REPORTES
    }

    private Vista vistaActual;
    private Button botonActual;
    private Logger LOG;

    // Heredados
    @Override
    public void initialize() {
        FXAnimator.slideInFromLeft(root, 200);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
            UIRouter.getInstance().show("DashboardView", UIRouter.Position.CENTER);
        });
        LOG = Logger.getLogger(MainMenuController.class.getName());
        resetButtonStyles();
        this.vistaActual = Vista.DASHBOARD;
        this.botonActual = btnDashboard;
        cambiarBoton(btnDashboard);
        this.lblFolioAdmin.setText((String) AppContext.getInstance().get("FolioLogueado"));
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblMiEmpresa.setText(bundle.getString("mainmenu.title"));
            btnDashboard.setText(bundle.getString("mainmenu.btn.dashboard"));
            btnEmpleados.setText(bundle.getString("mainmenu.btn.empleados"));
            btnMarcas.setText(bundle.getString("mainmenu.btn.marcas"));
            btnPlanillas.setText(bundle.getString("mainmenu.btn.planillas"));
            btnConsultas.setText(bundle.getString("mainmenu.btn.consultas"));
            btnReportes.setText(bundle.getString("mainmenu.btn.reportes"));
            btnSalir.setText(bundle.getString("mainmenu.btn.salir"));
            lblFooterMiEmpresa.setText(bundle.getString("mainmenu.footer.empresa"));
            lblDerechos.setText(bundle.getString("mainmenu.footer.derechos"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at MainMenuController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

    // helpers
    private void cambiarBoton(Button btnSeleccionado) {
        botonActual.getStyleClass().remove("selected");
        botonActual = btnSeleccionado;
        botonActual.getStyleClass().add("selected");
    }

    private void resetButtonStyles() {
        btnDashboard.getStyleClass().remove("selected");
        btnEmpleados.getStyleClass().remove("selected");
        btnMarcas.getStyleClass().remove("selected");
        btnConsultas.getStyleClass().remove("selected");
        btnPlanillas.getStyleClass().remove("selected");
        btnReportes.getStyleClass().remove("selected");
    }

    private void logout() {
        UIRouter.getInstance().close(UIRouter.Position.LEFT);
        UIRouter.getInstance().close(UIRouter.Position.CENTER);
        UIRouter.getInstance().show("MainHeaderView", UIRouter.Position.TOP);
        UIRouter.getInstance().show("MainFooterView", UIRouter.Position.BOTTOM);
        UIRouter.getInstance().show("LoginView", UIRouter.Position.CENTER);
    }

    @FXML
    private void onActionBtnDashboard(ActionEvent event) {
        if (vistaActual != Vista.DASHBOARD) {
            cambiarBoton(btnDashboard);
            vistaActual = Vista.DASHBOARD;
            UIRouter.getInstance().show("DashboardView", UIRouter.Position.CENTER);
        }
    }

    @FXML
    private void onActionBtnEmpleados(ActionEvent event) {
        if (vistaActual != Vista.EMPLEADOS) {
            cambiarBoton(btnEmpleados);
            vistaActual = Vista.EMPLEADOS;
            UIRouter.getInstance().show("EmpleadosView", UIRouter.Position.CENTER);
        }
    }

    @FXML
    private void onActionBtnMarcas(ActionEvent event) {
        if (vistaActual != Vista.MARCAS) {
            cambiarBoton(btnMarcas);
            vistaActual = Vista.MARCAS;
            UIRouter.getInstance().show("MarcasView", UIRouter.Position.CENTER);
        }
    }

    @FXML
    private void onActionBtnPlanillas(ActionEvent event) {
        if (vistaActual != Vista.PLANILLAS) {
            cambiarBoton(btnPlanillas);
            vistaActual = Vista.PLANILLAS;
            // TODO: Abrir ventana de planillas
        }
    }

    @FXML
    private void onActionBtnConsultas(ActionEvent event) {
        if (vistaActual != Vista.CONSULTAS) {
            cambiarBoton(btnConsultas);
            vistaActual = Vista.CONSULTAS;
            UIRouter.getInstance().show("ConsultasView", UIRouter.Position.CENTER);
        }
    }

    @FXML
    private void onActionBtnReportes(ActionEvent event) {
        if (vistaActual != Vista.REPORTES) {
            cambiarBoton(btnReportes);
            vistaActual = Vista.REPORTES;
            UIRouter.getInstance().show("ReporteView", UIRouter.Position.CENTER);
        }
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        if (new Mensaje().showConfirmation(bundle.getString("mainmenu.check.logout.title"), root.getScene().getWindow(), bundle.getString("mainmenu.check.logout"))) {
            logout();
        }
    }

}
