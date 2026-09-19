package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.model.ResumenDetallePlanillaViewModel;
import cr.ac.una.relojuna.service.PlanillaService;
import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.ws.ResumenDetallePlanillaDTO;
import cr.ac.una.relojuna.ws.ResumenPlanillaDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

public class PlanillasController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblInfo;
    @FXML
    private MFXComboBox<Integer> mcbMes;
    @FXML
    private MFXComboBox<Integer> mcbAnno;
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
    private TableView<ResumenDetallePlanillaViewModel> tbDetallesPlanilla;
    @FXML
    private TableColumn<ResumenDetallePlanillaViewModel, String> clFolio;
    @FXML
    private TableColumn<ResumenDetallePlanillaViewModel, String> clEmpleado;
    @FXML
    private TableColumn<ResumenDetallePlanillaViewModel, String> clSalarioHora;
    @FXML
    private TableColumn<ResumenDetallePlanillaViewModel, String> clHorasOrd;
    @FXML
    private TableColumn<ResumenDetallePlanillaViewModel, String> clHorasExtras;
    @FXML
    private TableColumn<ResumenDetallePlanillaViewModel, String> clHorasDobles;
    @FXML
    private TableColumn<ResumenDetallePlanillaViewModel, String> clTotalHoras;
    @FXML
    private TableColumn<ResumenDetallePlanillaViewModel, String> clTotalAPagar;
    @FXML
    private MFXButton btnGenerarPlanilla;
    @FXML
    private MFXCheckbox chkEnviarCorreo;

    private final static Logger LOG = Logger.getLogger(PlanillasController.class.getName());

    private final PlanillaService planillaService = new PlanillaService();
    private ResumenPlanillaDTO resumenActual;

    @Override
    public void initialize() {
        FXAnimator.slideInFromRight(root, 20);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
        cargarValoresPorDefecto();
        configurarTabla();
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
        Integer mes = obtenerMesSeleccionado();
        Integer anio = obtenerAnioSeleccionado();
        if (mes == null || anio == null) {
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.warning.title"),
                    bundle.getString("planillas.notification.mesanno.requerido"));
            return;
        }

        Respuesta respuesta = planillaService.calcularPlanilla(anio, mes);

        if (!respuesta.getEstado()) {
            limpiarResumen();
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.ERROR,
                    bundle.getString("general.notification.error.titulo"),
                    respuesta.getMensaje());
            return;
        }

        ResumenPlanillaDTO resumen = (ResumenPlanillaDTO) respuesta.getResultado("ResumenPlanilla");
        mostrarResumen(resumen);
    }

    private void generarPlanilla() {
        // TODO: Enviar al servidor el resumen de la planilla para generarla y procesar cualquier error
    }

    // Helpers
    private void configurarTabla() {
        clFolio.setCellValueFactory(cellData -> cellData.getValue().folioProperty());
        clEmpleado.setCellValueFactory(cellData -> cellData.getValue().nombreCompletoEmpleadoProperty());
        clSalarioHora.setCellValueFactory(cellData -> cellData.getValue().salarioHoraEmpleadoProperty());
        clHorasOrd.setCellValueFactory(cellData -> cellData.getValue().horasOrdinariasProperty());
        clHorasExtras.setCellValueFactory(cellData -> cellData.getValue().horasExtrasProperty());
        clHorasDobles.setCellValueFactory(cellData -> cellData.getValue().horasDoblesProperty());
        clTotalHoras.setCellValueFactory(cellData -> cellData.getValue().totalHorasProperty());
        clTotalAPagar.setCellValueFactory(cellData -> cellData.getValue().totalAPagarProperty());
    }

    private void cargarValoresPorDefecto() {
        mcbAnno.getItems().clear();
        mcbMes.getItems().clear();
        tbDetallesPlanilla.getItems().clear();
        configurarComboMes();

        LocalDate mesAnterior = LocalDate.now().minusMonths(1);
        mcbMes.getSelectionModel().selectItem(mesAnterior.getMonthValue());
        mcbAnno.getSelectionModel().selectItem(mesAnterior.getYear());

        chkGenerada.setDisable(true);
        btnGenerarPlanilla.setDisable(true);
        chkEnviarCorreo.setDisable(true);
    }

    private void configurarComboMes() {
        mcbMes.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer mes) {
                if (mes == null) {
                    return "";
                }
                Locale locale = (bundle != null) ? bundle.getLocale() : Locale.getDefault();
                return Month.of(mes).getDisplayName(TextStyle.FULL, locale);
            }

            @Override
            public Integer fromString(String string) {
                return null;
            }
        });

        for (int mes = 1; mes <= 12; mes++) {
            mcbMes.getItems().add(mes);
        }

        int anioActual = LocalDate.now().getYear();
        for (int anio = anioActual; anio >= anioActual - 5; anio--) {
            mcbAnno.getItems().add(anio);
        }
    }

    private Integer obtenerMesSeleccionado() {
        return mcbMes.getSelectionModel().getSelectedItem();
    }

    private Integer obtenerAnioSeleccionado() {
        return mcbAnno.getSelectionModel().getSelectedItem();
    }

    private void mostrarResumen(ResumenPlanillaDTO resumen) {
        this.resumenActual = resumen;

        lblCantEmpleados.setText(String.valueOf(resumen.getCantEmpleados()));
        lblCantHorasOrd.setText(redondear(resumen.getCantHorasOrdinarias()).toString());
        lblCantHorasExt.setText(redondear(resumen.getCantHorasExtras()).toString());
        lblCantHorasNoc.setText(redondear(resumen.getCantHorasNocturnas()).toString());
        lblMontoTotalPagar.setText(resumen.getTotalAPagar().setScale(2, RoundingMode.HALF_UP).toString());

        boolean generada = Boolean.TRUE.equals(resumen.isGenerada());
        chkGenerada.setSelected(generada);
        btnGenerarPlanilla.setDisable(generada);
        chkEnviarCorreo.setDisable(generada);

        tbDetallesPlanilla.setItems(convertirDetalles(resumen.getDetallesPlanilla()));
    }

    private Double redondear(Double numero) {
        return new BigDecimal(numero).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private void limpiarResumen() {
        this.resumenActual = null;

        lblCantEmpleados.setText("");
        lblCantHorasOrd.setText("");
        lblCantHorasExt.setText("");
        lblCantHorasNoc.setText("");
        lblMontoTotalPagar.setText("");

        chkGenerada.setSelected(false);
        btnGenerarPlanilla.setDisable(true);
        chkEnviarCorreo.setDisable(true);

        tbDetallesPlanilla.getItems().clear();
    }

    private ObservableList<ResumenDetallePlanillaViewModel> convertirDetalles(List<ResumenDetallePlanillaDTO> detalles) {
        ObservableList<ResumenDetallePlanillaViewModel> items = FXCollections.observableArrayList();
        if (detalles == null) {
            return items;
        }
        for (ResumenDetallePlanillaDTO dto : detalles) {
            dto.setHorasOrdinarias(redondear(dto.getHorasOrdinarias()));
            dto.setHorasExtras(redondear(dto.getHorasExtras()));
            dto.setHorasDobles(redondear(dto.getHorasDobles()));
            dto.setHorasNocturnas(redondear(dto.getHorasNocturnas()));
            dto.setTotalHoras(redondear(dto.getTotalHoras()));
            dto.setTotalAPagar(dto.getTotalAPagar().setScale(2, RoundingMode.HALF_UP));
            ResumenDetallePlanillaViewModel vm = new ResumenDetallePlanillaViewModel();
            vm.fromDTO(dto);
            items.add(vm);
        }
        return items;
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
