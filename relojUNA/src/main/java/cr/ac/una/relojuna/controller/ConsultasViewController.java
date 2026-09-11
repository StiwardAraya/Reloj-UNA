package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.service.ConsultasService;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.JornadaDTO;
import cr.ac.una.relojuna.ws.ResumenMarcasDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import cr.ac.una.relojuna.service.ExcelService;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;


public class ConsultasViewController extends Controller {

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

    private final ConsultasService consultasService = new ConsultasService();

    private final ObservableList<JornadaDTO> jornadas
            = FXCollections.observableArrayList();

    //EXCEL
    private final ExcelService excelService = new ExcelService();
    private ResumenMarcasDTO resumenActual;

    @Override
    public void initialize() {
        configurarTabla();

        tblJornadas.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        dpFechaInicio.setValue(LocalDate.now().withDayOfMonth(1));
        dpFechaFin.setValue(LocalDate.now());

        tblJornadas.setItems(jornadas);
    }

    private void configurarTabla() {
        colFecha.setCellValueFactory(datos -> new SimpleStringProperty(
                        valorSeguro(datos.getValue().getFecha())
                )
        );

        colFolio.setCellValueFactory(datos -> new SimpleStringProperty(
                        valorSeguro(datos.getValue().getFolioEmpleado())
                )
        );

        colEmpleado.setCellValueFactory(datos -> new SimpleStringProperty(
                        valorSeguro(datos.getValue().getNombreEmpleado())
                )
        );

        colEntrada.setCellValueFactory(datos -> new SimpleStringProperty(
                        obtenerHoraEntrada(datos.getValue())
                )
        );

        colSalida.setCellValueFactory(datos -> new SimpleStringProperty(
                        obtenerHoraSalida(datos.getValue())
                )
        );

        colHoras.setCellValueFactory(datos -> {
            Double horas = datos.getValue().getHorasTrabajadas();

            String texto = horas == null
                    ? "-"
                    : String.format("%.2f h", horas);

            return new SimpleStringProperty(texto);
        });

        colEstado.setCellValueFactory(datos -> {
            Boolean completa = datos.getValue().isCompleta();

            String estado = Boolean.TRUE.equals(completa)
                    ? "Completa"
                    : "Incompleta";

            return new SimpleStringProperty(estado);
        });
    }

    @FXML
    private void consultar(ActionEvent event) {
        LocalDate desde = dpFechaInicio.getValue();
        LocalDate hasta = dpFechaFin.getValue();
        String folio = txtFolio.getText();

        Respuesta respuestaJornadas = consultasService.obtenerJornadas(desde, hasta, folio);

        if (!respuestaJornadas.getEstado()) {
            mostrarError(respuestaJornadas.getMensaje());
            return;
        }

        Respuesta respuestaResumen = consultasService.consultarResumen(desde, hasta, folio);

        if (!respuestaResumen.getEstado()) {
            mostrarError(respuestaResumen.getMensaje());
            return;
        }

        cargarJornadas(respuestaJornadas);
        cargarResumen(respuestaResumen);

        btnExportarExcel.setDisable(jornadas.isEmpty());
    }

    @SuppressWarnings("Desmarcado")
    private void cargarJornadas(Respuesta respuesta) {
        Object resultado = respuesta.getResultado("Jornadas");

        if (resultado instanceof List<?> lista) {
            jornadas.setAll((List<JornadaDTO>) lista);
        } else {
            jornadas.clear();
        }
    }

    private void cargarResumen(Respuesta respuesta) {
        Object resultado = respuesta.getResultado("ResumenMarcas");

        if (!(resultado instanceof ResumenMarcasDTO resumen)) {
            resumenActual = null;
            lblCantidadEmpleados.setText("0");
            lblTotalMarcas.setText("0");
            lblTotalHoras.setText("0 h 0 min");
            return;
        }

        resumenActual = resumen;

        lblCantidadEmpleados.setText(
                String.valueOf(resumen.getCantidadEmpleados())
        );

        lblTotalMarcas.setText(
                String.valueOf(resumen.getTotalMarcas())
        );

        lblTotalHoras.setText(
                resumen.getTotalHorasTrabajadas() + " h " + resumen.getTotalMinutosTrabajados() + " min"
        );
    }

    private String obtenerHoraEntrada(JornadaDTO jornada) {
        if (jornada.getMarcaEntrada() == null) {
            return "-";
        }

        return extraerHora(
                jornada.getMarcaEntrada().getFechaHora()
        );
    }

    private String obtenerHoraSalida(JornadaDTO jornada) {
        if (jornada.getMarcaSalida() == null) {
            return "-";
        }

        return extraerHora(
                jornada.getMarcaSalida().getFechaHora()
        );
    }

    private String extraerHora(String fechaHora) {
        if (fechaHora == null || fechaHora.isBlank()) {
            return "-";
        }

        //esto es por el formato de como el WS, me devuelve la hora
        if (fechaHora.length() >= 16 && fechaHora.contains("T")) {
            return fechaHora.substring(11, 16);
        }

        return fechaHora;
    }

    private String valorSeguro(Object valor) {
        return valor == null ? "-" : valor.toString();
    }

    @FXML
    private void exportarExcel(ActionEvent event) {
        if (jornadas.isEmpty() || resumenActual == null) {
            mostrarError( "Primero debe realizar una consulta con resultados.");
            return;
        }

        FileChooser selector = new FileChooser();
        
        selector.setTitle("Guardar consulta en Excel");

        selector.getExtensionFilters().add(
                new FileChooser.ExtensionFilter( "Archivo de Excel (*.xlsx)", "*.xlsx" )
        );

        selector.setInitialFileName(
                "ConsultaMarcas_" + "_" + dpFechaFin.getValue() + ".xlsx"
        );

        File archivo = selector.showSaveDialog(
                root.getScene().getWindow()
        );

        if (archivo == null) {
            return;
        }

        if (!archivo.getName().toLowerCase().endsWith(".xlsx")) {
            archivo = new File( archivo.getParentFile(),
                    archivo.getName() + ".xlsx"
            );
        }

        try {excelService.generarExcel(
                    archivo,
                    jornadas,
                    dpFechaInicio.getValue(),
                    dpFechaFin.getValue(),
                    txtFolio.getText(),
                    resumenActual
            );
            mostrarInformacion(
                    "El archivo Excel se generó correctamente."
            );
        } catch (IOException ex) {
            mostrarError( "No se pudo guardar el archivo Excel: " + ex.getMessage()
            );
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Consulta de jornadas");
        alerta.setHeaderText("No se pudo realizar la consulta");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Consultas");
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
        // falta modificar la traduccion del idioma
    }

}
