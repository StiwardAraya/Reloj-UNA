package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.service.ConsultaService;
import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.ws.JornadaDTO;
import cr.ac.una.relojuna.ws.JornadaListDTO;
import cr.ac.una.relojuna.ws.ResumenMarcasDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.LocalDate;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import cr.ac.una.relojuna.ws.ArchivoDTO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.stage.FileChooser;

public class ConsultasController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXDatePicker dpFechaInicio;
    @FXML
    private MFXDatePicker dpFechaFin;
    @FXML
    private MFXTextField txtFolio;
    @FXML
    private MFXButton btnConsultar;
    @FXML
    private MFXButton btnExportarExcel;
    @FXML
    private Label lblTituloConsulta;
    @FXML
    private Label lblDescripcionConsulta;
    @FXML
    private Label lblTituloFiltros;
    @FXML
    private Label lblFechaInicio;
    @FXML
    private Label lblFechaFin;
    @FXML
    private Label lblFolio;
    @FXML
    private Label lblSinJornadas;
    @FXML
    private Label lblTextoCantidadEmpleados;
    @FXML
    private Label lblTextoTotalMarcas;
    @FXML
    private Label lblTextoTotalHoras;
    @FXML
    private Label lblTituloDetalle;
    @FXML
    private Label lblCantidadEmpleados;
    @FXML
    private Label lblTotalMarcas;
    @FXML
    private Label lblTotalHoras;
    @FXML
    private TableView<JornadaDTO> tblJornadas;
    @FXML
    private TableColumn<JornadaDTO, String> colFecha;
    @FXML
    private TableColumn<JornadaDTO, String> colFolio;
    @FXML
    private TableColumn<JornadaDTO, String> colEmpleado;
    @FXML
    private TableColumn<JornadaDTO, String> colEntrada;
    @FXML
    private TableColumn<JornadaDTO, String> colSalida;
    @FXML
    private TableColumn<JornadaDTO, String> colHoras;

    @FXML
    private TableColumn<JornadaDTO, String> colEstado;
    private final ConsultaService consultaService = new ConsultaService();
    private final ObservableList<JornadaDTO> jornadas = FXCollections.observableArrayList();
    private static final Logger LOG = Logger.getLogger(ConsultasController.class.getName());

    @Override
    public void initialize() {
        FXAnimator.slideInFromRight(root, 20);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
        configurarTabla();

        tblJornadas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        dpFechaInicio.setValue(LocalDate.now().withDayOfMonth(1));
        dpFechaFin.setValue(LocalDate.now());
        tblJornadas.setItems(jornadas);
        btnExportarExcel.setDisable(true);

    }

    private void configurarTabla() {
        colFecha.setCellValueFactory(datos -> new SimpleStringProperty(valorSeguro(datos.getValue().getFecha())));
        colFolio.setCellValueFactory(datos -> new SimpleStringProperty(valorSeguro(datos.getValue().getFolioEmpleado())));
        colEmpleado.setCellValueFactory(datos -> new SimpleStringProperty(valorSeguro(datos.getValue().getNombreEmpleado())));
        colEntrada.setCellValueFactory(datos -> new SimpleStringProperty(obtenerHoraEntrada(datos.getValue())));
        colSalida.setCellValueFactory(datos -> new SimpleStringProperty(obtenerHoraSalida(datos.getValue())));
        colHoras.setCellValueFactory(datos -> {
            Double horas = datos.getValue().getHorasTrabajadas();
            String texto;
            if (horas == null) {
                texto = "-";
            } else {
                texto = String.format(bundle.getString(
                        "consultas.formato.horas"
                ),
                        horas
                );
            }
            return new SimpleStringProperty(texto);
        });
        colEstado.setCellValueFactory(datos -> {
            Boolean completa = datos.getValue().isCompleta();
            String estado = Boolean.TRUE.equals(completa)
                    ? bundle.getString("consultas.estado.completa")
                    : bundle.getString("consultas.estado.incompleta");
            return new SimpleStringProperty(estado);
        });
    }

    @FXML
    private void consultar(ActionEvent event) {

        LocalDate desde = dpFechaInicio.getValue();
        LocalDate hasta = dpFechaFin.getValue();
        String folio = txtFolio.getText();

        Respuesta respuestaJornadas = consultaService.obtenerJornadas(desde, hasta, folio);

        if (!respuestaJornadas.getEstado()) {
            mostrarError(traducirMensaje(respuestaJornadas.getMensaje()));
            return;
        }
        Respuesta respuestaResumen = consultaService.consultarResumen(desde, hasta, folio);

        if (!respuestaResumen.getEstado()) {
            mostrarError(respuestaResumen.getMensaje());
            return;
        }
        cargarJornadas(respuestaJornadas);
        cargarResumen(respuestaResumen);
        btnExportarExcel.setDisable(jornadas.isEmpty());
    }

    @FXML
    private void exportarExcel(ActionEvent event) {

        LocalDate desde = dpFechaInicio.getValue();
        LocalDate hasta = dpFechaFin.getValue();
        String folio = txtFolio.getText();

        Respuesta respuesta = consultaService.obtenerExcelMarcas(desde, hasta, folio);
        if (!respuesta.getEstado()) {
            mostrarError(respuesta.getMensaje());
            return;
        }

        Object resultado = respuesta.getResultado("Archivo");
        if (!(resultado instanceof ArchivoDTO archivo)) {
            mostrarError("El servidor no devolvió el archivo esperado.");
            return;
        }
        if (archivo.getContenido() == null || archivo.getContenido().length == 0) {
            mostrarError("El archivo Excel recibido está vacío.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Excel (*.xlsx)", "*.xlsx"));
        String nombreArchivo = archivo.getNombreArchivo();
        if (nombreArchivo == null || nombreArchivo.isBlank()) {
            nombreArchivo = "ConsultaMarcas.xlsx";
        }

        fileChooser.setInitialFileName(nombreArchivo);
        File destino = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (destino == null) {
            return;
        }
        if (!destino.getName().toLowerCase().endsWith(".xlsx")) {
            destino = new File(destino.getParentFile(), destino.getName() + ".xlsx");
        }

        try {
                Files.write(destino.toPath(), archivo.getContenido());
            mostrarInformacion("El archivo Excel se guardó correctamente.");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error al guardar el Excel", ex);
            mostrarError("No se pudo guardar el archivo Excel.");
        }
    }

    private void cargarJornadas(Respuesta respuesta) {
        Object resultado = respuesta.getResultado("Jornadas");

        if (!(resultado instanceof JornadaListDTO dto)) {
            jornadas.clear();
            return;
        }
        jornadas.setAll(dto.getJornadas());
    }

    private void cargarResumen(Respuesta respuesta) {
        Object resultado = respuesta.getResultado("ResumenMarcas");

        if (!(resultado instanceof ResumenMarcasDTO resumen)) {
            lblCantidadEmpleados.setText("0");
            lblTotalMarcas.setText("0");
            lblTotalHoras.setText(
                    bundle.getString("consultas.formato.horascero")
            );
            return;
        }
        lblCantidadEmpleados.setText(String.valueOf(resumen.getCantidadEmpleados()));
        lblTotalMarcas.setText(String.valueOf(resumen.getTotalMarcas()));
        lblTotalHoras.setText(String.format(bundle.getString("consultas.formato.totalhoras"), resumen.getTotalHorasTrabajadas(), resumen.getTotalMinutosTrabajados())
        );
    }

    private String obtenerHoraEntrada(JornadaDTO jornada) {
        if (jornada.getMarcaEntrada() == null) {
            return "-";
        }
        return extraerHora(jornada.getMarcaEntrada().getFechaHora());
    }

    private String obtenerHoraSalida(JornadaDTO jornada) {
        if (jornada.getMarcaSalida() == null) {
            return "-";
        }
        return extraerHora(jornada.getMarcaSalida().getFechaHora());
    }

    private String extraerHora(String fechaHora) {
        if (fechaHora == null
                || fechaHora.isBlank()) {
            return "-";
        }
        if (fechaHora.length() >= 16 && fechaHora.contains("T")) {
            return fechaHora.substring(11, 16);
        }
        return fechaHora;
    }

    private String valorSeguro(Object valor) {
        return valor == null ? "-" : valor.toString();
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(bundle.getString("consultas.alerta.error.titulo"));
        alerta.setHeaderText(bundle.getString("consultas.alerta.error.encabezado"));
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(bundle.getString("consultas.alerta.info.titulo"));
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblTituloConsulta.setText(bundle.getString("consultas.lbl.titulo"));
            lblDescripcionConsulta.setText(bundle.getString("consultas.lbl.descripcion"));
            lblTituloFiltros.setText(bundle.getString("consultas.lbl.filtros"));
            lblFechaInicio.setText(bundle.getString("consultas.lbl.fechainicio"));
            lblFechaFin.setText(bundle.getString("consultas.lbl.fechafin"));
            lblFolio.setText(bundle.getString("consultas.lbl.folio"));
            lblTextoCantidadEmpleados.setText(bundle.getString("consultas.lbl.empleados"));
            lblTextoTotalMarcas.setText(bundle.getString("consultas.lbl.totalmarcas"));
            lblTextoTotalHoras.setText(bundle.getString("consultas.lbl.totalhoras"));
            lblTituloDetalle.setText(bundle.getString("consultas.lbl.detalle"));
            lblSinJornadas.setText(bundle.getString("consultas.lbl.sinjornadas"));
            txtFolio.setFloatingText(bundle.getString("consultas.txt.folio"));
            txtFolio.setPromptText(bundle.getString("consultas.txt.todos"));
            btnConsultar.setText(bundle.getString("consultas.btn.consultar"));
            btnExportarExcel.setText(bundle.getString("consultas.btn.excel"));
            colFecha.setText(bundle.getString("consultas.col.fecha"));
            colFolio.setText(bundle.getString("consultas.col.folio"));
            colEmpleado.setText(bundle.getString("consultas.col.empleado"));
            colEntrada.setText(bundle.getString("consultas.col.entrada"));
            colSalida.setText(bundle.getString("consultas.col.salida"));
            colHoras.setText(bundle.getString("consultas.col.horas"));
            colEstado.setText(bundle.getString("consultas.col.estado"));
            tblJornadas.refresh(); 

        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at " + "ConsultasViewController.updateLanguageTexts", ex);
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
}
