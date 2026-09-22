package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.model.JornadaViewModel;
import cr.ac.una.relojuna.service.PlanillaService;
import cr.ac.una.relojuna.util.AppContext;
import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.ws.EmpleadoDTO;
import cr.ac.una.relojuna.ws.ResumenDetallesEmpleadoDTO;
import cr.ac.una.relojuna.ws.ResumenDetallePlanillaDTO;
import cr.ac.una.relojuna.ws.ResumenJornadaDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.math.RoundingMode;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
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
    private Label lblPeriodo;
    @FXML
    private TableView<JornadaViewModel> tablaJornadas;
    @FXML
    private TableColumn<JornadaViewModel, String> colFecha;
    @FXML
    private TableColumn<JornadaViewModel, String> colEntrada;
    @FXML
    private TableColumn<JornadaViewModel, String> colSalida;
    @FXML
    private TableColumn<JornadaViewModel, String> colHoras;
    @FXML
    private TableColumn<JornadaViewModel, String> colDiaLibre;
    @FXML
    private TableColumn<JornadaViewModel, String> colEstado;
    @FXML
    private Label lblTotalHoras;
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
    private Label lblTextoPeriodo;
    @FXML
    private Label lblTituloMarcas;
    @FXML
    private Label lblDescripcionMarcas;
    @FXML
    private Label lblTituloResumen;
    @FXML
    private Label lblTextoTotalHoras;
    @FXML
    private Label lblTextoResumenSalarioHora;
    @FXML
    private Label lblTextoTotalPagar;
    @FXML
    private Label lblResumenFooter;
    @FXML
    private Label lblTextoHorasOrdinarias;
    @FXML
    private Label lblTotalHorasOrdinarias;
    @FXML
    private Label lblTextoHorasExtras;
    @FXML
    private Label lblTotalHorasExtras;
    @FXML
    private Label lblTextoHorasDobles;
    @FXML
    private Label lblTotalHorasDobles;
    @FXML
    private Label lblTextoHorasNocturnas;
    @FXML
    private Label lblTotalHorasNocturnas;

    private final PlanillaService planillaService = new PlanillaService();

    @Override
    public void initialize() {
        FXAnimator.fadeSlideInFromBottom(root, 100);
        configurarTabla();
        String folio = (String) AppContext.getInstance().get("DetallePlanillaFolio");
        Integer mes = (Integer) AppContext.getInstance().get("DetallePlanillaMes");
        Integer anio = (Integer) AppContext.getInstance().get("DetallePlanillaAnio");
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
            cargarDatos(folio, anio, mes);
        });
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblTituloDetallePlanilla.setText(bundle.getString("detalleplanilla.lbl.titulo"));
            lblTituloDatosEmpleado.setText(bundle.getString("detalleplanilla.lbl.datosEmpleado"));
            lblTextoFolio.setText(bundle.getString("detalleplanilla.lbl.folio"));
            lblTextoSalarioHora.setText(bundle.getString("detalleplanilla.lbl.salarioHora"));
            lblTextoAdministrador.setText(bundle.getString("detalleplanilla.lbl.administrador"));
            lblTextoPeriodo.setText(bundle.getString("detalleplanilla.lbl.periodo"));
            lblTituloMarcas.setText(bundle.getString("detalleplanilla.lbl.detalleMarcas"));
            lblDescripcionMarcas.setText(bundle.getString("detalleplanilla.lbl.descripcionMarcas"));
            lblTituloResumen.setText(bundle.getString("detalleplanilla.lbl.resumen"));
            lblTextoTotalHoras.setText(bundle.getString("detalleplanilla.lbl.totalHoras"));
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

    // Helpers
    private void configurarTabla() {
        colFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        colEntrada.setCellValueFactory(cellData -> cellData.getValue().entradaProperty());
        colSalida.setCellValueFactory(cellData -> cellData.getValue().salidaProperty());
        colHoras.setCellValueFactory(cellData -> cellData.getValue().horasTrabajadasProperty());
        colDiaLibre.setCellValueFactory(cellData -> cellData.getValue().diaLibreProperty());
        colEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
    }

    private void cargarDatos(String folio, int anio, int mes) {
        Respuesta respuesta = planillaService.getDetallesEmpleadoResumen(folio, anio, mes);
        if (!respuesta.getEstado()) {
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.ERROR,
                    bundle.getString("general.notification.error.titulo"),
                    respuesta.getMensaje());
            return;
        }

        ResumenDetallesEmpleadoDTO dto = (ResumenDetallesEmpleadoDTO) respuesta.getResultado("ResumenDetallesEmpleado");

        EmpleadoDTO empleado = dto.getEmpleadoDto();
        lblNombreEmpleado.setText(empleado.getNombre() + " " + empleado.getPrimerApellido() + " " + empleado.getSegundoApellido());
        lblFolio.setText(empleado.getFolio());
        lblSalarioHora.setText(dto.getResumenDetalleDto().getSalarioHoraEmpleado().toString());
        lblAdministrador.setText("S".equalsIgnoreCase(empleado.getEsAdmin()) ? "Sí" : "No");
        lblPeriodo.setText(Month.of(mes).getDisplayName(TextStyle.FULL, Locale.getDefault()) + ", " + anio);

        ResumenDetallePlanillaDTO resumen = dto.getResumenDetalleDto();
        lblTotalHorasOrdinarias.setText(resumen.getHorasOrdinarias().toString());
        lblTotalHorasExtras.setText(resumen.getHorasExtras().toString());
        lblTotalHorasDobles.setText(resumen.getHorasDobles().toString());
        lblTotalHorasNocturnas.setText(resumen.getHorasNocturnas().toString());
        lblTotalHoras.setText(resumen.getTotalHoras().toString());
        lblTotalPagar.setText(resumen.getTotalAPagar().setScale(2, RoundingMode.HALF_UP).toString());

        ObservableList<JornadaViewModel> items = FXCollections.observableArrayList();
        for (ResumenJornadaDTO jornada : dto.getJornadas()) {
            JornadaViewModel vm = new JornadaViewModel();
            vm.fromDTO(jornada);
            items.add(vm);
        }
        tablaJornadas.setItems(items);
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

    @FXML
    private void onActionBtnCerrar(ActionEvent event) {
        UIRouter.getInstance().hideModal();
    }
}
