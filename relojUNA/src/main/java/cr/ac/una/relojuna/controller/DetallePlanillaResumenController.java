package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class DetallePlanillaResumenController extends Controller {

    private static final Logger LOG = Logger.getLogger(DetallePlanillaResumenController.class.getName());

    @FXML
    private BorderPane root;
    @FXML
    private ImageView imgEmpleado;
    @FXML
    private Label lblNombreEmpleado;
    @FXML
    private Label lblFolio;
    @FXML
    private Label lblSalarioHora;
    @FXML
    private Label lblAdministrador;
    @FXML
    private Label lblEstadoEmpleado;
    @FXML
    private Label lblFechaInicio;
    @FXML
    private Label lblFechaFin;
    @FXML
    private Label lblPeriodo;
    @FXML
    private TableView<?> tablaJornadas;
    @FXML
    private TableColumn<?, ?> colFecha;
    @FXML
    private TableColumn<?, ?> colEntrada;
    @FXML
    private TableColumn<?, ?> colSalida;
    @FXML
    private TableColumn<?, ?> colHoras;
    @FXML
    private TableColumn<?, ?> colDiaLibre;
    @FXML
    private TableColumn<?, ?> colEstado;
    @FXML
    private Label lblTotalJornadas;
    @FXML
    private Label lblJornadasCompletas;
    @FXML
    private Label lblJornadasIncompletas;
    @FXML
    private Label lblDiasLibres;
    @FXML
    private Label lblTotalHoras;
    @FXML
    private Label lblTotalMinutos;
    @FXML
    private Label lblResumenSalarioHora;
    @FXML
    private Label lblTotalPagar;
    @FXML
    private MFXButton btnCerrar;
    @FXML
    private Label lblTituloDetallePlanilla;
    @FXML
    private Label lblTituloDatosEmpleado;
    @FXML
    private Label lblTextoFolio;
    @FXML
    private Label lblTextoSalarioHora;
    @FXML
    private Label lblTextoAdministrador;
    @FXML
    private Label lblTextoEstado;
    @FXML
    private Label lblTextoFechaInicio;
    @FXML
    private Label lblTextoFechaFin;
    @FXML
    private Label lblTextoPeriodo;
    @FXML
    private Label lblTituloMarcas;
    @FXML
    private Label lblDescripcionMarcas;
    @FXML
    private Label lblTituloResumen;
    @FXML
    private Label lblTextoTotalJornadas;
    @FXML
    private Label lblTextoJornadasCompletas;
    @FXML
    private Label lblTextoJornadasIncompletas;
    @FXML
    private Label lblTextoDiasLibres;
    @FXML
    private Label lblTextoTotalHoras;
    @FXML
    private Label lblTextoTotalMinutos;
    @FXML
    private Label lblTextoResumenSalarioHora;
    @FXML
    private Label lblTextoTotalPagar;

    @Override
    public void initialize() {

    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblTituloDetallePlanilla.setText(bundle.getString("detalleplanilla.lbl.titulo"));
            lblTituloDatosEmpleado.setText(bundle.getString("detalleplanilla.lbl.datosEmpleado"));

            lblTextoFolio.setText(bundle.getString("detalleplanilla.lbl.folio"));
            lblTextoSalarioHora.setText(bundle.getString("detalleplanilla.lbl.salarioHora"));
            lblTextoAdministrador.setText(bundle.getString("detalleplanilla.lbl.administrador"));
            lblTextoEstado.setText(bundle.getString("detalleplanilla.lbl.estado"));
            lblTextoFechaInicio.setText(bundle.getString("detalleplanilla.lbl.fechaInicio"));
            lblTextoFechaFin.setText(bundle.getString("detalleplanilla.lbl.fechaFin"));
            lblTextoPeriodo.setText(bundle.getString("detalleplanilla.lbl.periodo"));

            lblTituloMarcas.setText(bundle.getString("detalleplanilla.lbl.detalleMarcas"));
            lblDescripcionMarcas.setText(bundle.getString("detalleplanilla.lbl.descripcionMarcas"));

            lblTituloResumen.setText(bundle.getString("detalleplanilla.lbl.resumen"));
            lblTextoTotalJornadas.setText(bundle.getString("detalleplanilla.lbl.totalJornadas"));
            lblTextoJornadasCompletas.setText(bundle.getString("detalleplanilla.lbl.jornadasCompletas"));
            lblTextoJornadasIncompletas.setText(bundle.getString("detalleplanilla.lbl.jornadasIncompletas"));
            lblTextoDiasLibres.setText(bundle.getString("detalleplanilla.lbl.diasLibres"));

            lblTextoTotalHoras.setText(bundle.getString("detalleplanilla.lbl.totalHoras"));
            lblTextoTotalMinutos.setText(bundle.getString("detalleplanilla.lbl.totalMinutos"));
            lblTextoResumenSalarioHora.setText(bundle.getString("detalleplanilla.lbl.salarioHora"));
            lblTextoTotalPagar.setText(bundle.getString("detalleplanilla.lbl.totalPagar"));

            btnCerrar.setText(bundle.getString("detalleplanilla.btn.cerrar"));

            colFecha.setText(bundle.getString("detalleplanilla.col.fecha"));
            colEntrada.setText(bundle.getString("detalleplanilla.col.entrada"));
            colSalida.setText(bundle.getString("detalleplanilla.col.salida"));
            colHoras.setText(bundle.getString("detalleplanilla.col.horas"));
            colDiaLibre.setText(bundle.getString("detalleplanilla.col.diaLibre"));
            colEstado.setText(bundle.getString("detalleplanilla.col.estado"));

            tablaJornadas.refresh();

        } catch (MissingResourceException ex) {
            LOG.log(
                    Level.SEVERE,
                    "Exception configuring view language at DetallePlanillaResumenController.updateLanguageTexts",
                    ex
            );

            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg")
            );
        }
    }

    private String traducirMensaje(String clave) {
        if (clave == null || clave.isBlank()) {
            return "";
        }

        try {
            return bundle.getString(clave);
        } catch (MissingResourceException ex) {
            return clave;
        }
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
