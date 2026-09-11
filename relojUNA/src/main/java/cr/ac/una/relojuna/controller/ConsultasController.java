package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.service.ConsultasService;
import cr.ac.una.relojuna.service.ExcelService;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.ws.JornadaDTO;
import cr.ac.una.relojuna.ws.ResumenMarcasDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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
    private final ConsultasService consultasService = new ConsultasService();
    private final ExcelService excelService = new ExcelService();
    private final ObservableList<JornadaDTO> jornadas = FXCollections.observableArrayList();
    private ResumenMarcasDTO resumenActual;
    private static final Logger LOG = Logger.getLogger(ConsultasController.class.getName());

    @Override
    public void initialize() {
        configurarTabla();

        tblJornadas.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN );
        dpFechaInicio.setValue( LocalDate.now().withDayOfMonth(1) );
        dpFechaFin.setValue(LocalDate.now());
        tblJornadas.setItems(jornadas);
        btnExportarExcel.setDisable(true);

        Platform.runLater(() -> { updateLanguageTexts(bundle);
        });
    }

    private void configurarTabla() {
        colFecha.setCellValueFactory(datos -> new SimpleStringProperty( valorSeguro( datos.getValue().getFecha() )
                )
        );
        colFolio.setCellValueFactory(datos
                -> new SimpleStringProperty( valorSeguro( datos.getValue().getFolioEmpleado() )
                )
        );
        colEmpleado.setCellValueFactory(datos
                -> new SimpleStringProperty( valorSeguro(datos.getValue().getNombreEmpleado() )
                )
        );
        colEntrada.setCellValueFactory(datos
                -> new SimpleStringProperty( obtenerHoraEntrada(datos.getValue())
                )
        );
        colSalida.setCellValueFactory(datos
                -> new SimpleStringProperty( obtenerHoraSalida(datos.getValue())
                )
        );
        colHoras.setCellValueFactory(datos -> {
            Double horas = datos.getValue().getHorasTrabajadas();
            String texto;
            if (horas == null) {
                texto = "-";
            } else {
                texto = String.format( bundle.getString(
                                "consultas.formato.horas"
                            ),
                            horas
                );
            }
            return new SimpleStringProperty(texto);
        });
        colEstado.setCellValueFactory(datos -> { Boolean completa = datos.getValue().isCompleta();
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
        String folio = txtFolio.getText() == null
                ? "" : txtFolio.getText().trim();
        if (desde == null || hasta == null) {
            mostrarError(
                    bundle.getString( "consultas.error.fechasrequeridas" )
            );
            return;
        }
        if (hasta.isBefore(desde)) {
            mostrarError(
                    bundle.getString(
                            "consultas.error.rangofechas"
                    )
            );
            return;
        }
        Respuesta respuestaJornadas
                = consultasService.obtenerJornadas(
                        desde,
                        hasta,
                        folio
                );

        if (!respuestaJornadas.getEstado()) {
            mostrarError(
                    respuestaJornadas.getMensaje()
            );
            return;
        }
        Respuesta respuestaResumen
                = consultasService.consultarResumen(
                        desde,
                        hasta,
                        folio
                );
        if (!respuestaResumen.getEstado()) {
            mostrarError(
                    respuestaResumen.getMensaje()
            );
            return;
        }
        cargarJornadas(respuestaJornadas);
        cargarResumen(respuestaResumen);
        btnExportarExcel.setDisable(
                jornadas.isEmpty()
        );
    }

    @SuppressWarnings("unchecked")
    private void cargarJornadas(Respuesta respuesta) {
        Object resultado = respuesta.getResultado("Jornadas");

        if (resultado instanceof List<?> lista) {
            jornadas.setAll(
                    (List<JornadaDTO>) lista
            );
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
            lblTotalHoras.setText(
                    bundle.getString( "consultas.formato.horascero" )
            );
            return;
        }
        resumenActual = resumen;
        lblCantidadEmpleados.setText( String.valueOf( resumen.getCantidadEmpleados() )
        );
        lblTotalMarcas.setText( String.valueOf( resumen.getTotalMarcas() )
        );
        lblTotalHoras.setText(
                String.format( bundle.getString( "consultas.formato.totalhoras" ),
                        resumen.getTotalHorasTrabajadas(),
                        resumen.getTotalMinutosTrabajados()
                )
        );
    }

    private String obtenerHoraEntrada(
            JornadaDTO jornada) {
        if (jornada.getMarcaEntrada() == null) {
            return "-";
        }
        return extraerHora( jornada.getMarcaEntrada().getFechaHora() );
    }

    private String obtenerHoraSalida(
            JornadaDTO jornada) {
        if (jornada.getMarcaSalida() == null) {
            return "-";
        }
        return extraerHora( jornada.getMarcaSalida().getFechaHora() );
    }

    private String extraerHora(String fechaHora) {
        if (fechaHora == null
                || fechaHora.isBlank()) {
            return "-";
        }
        //esto es por el formato de como el WS devuelve la fecha y hora =2026-09-11T08:30:00
        if (fechaHora.length() >= 16 && fechaHora.contains("T")) {
            return fechaHora.substring(11, 16);
        }
        return fechaHora;
    }

    private String valorSeguro(Object valor) {
        return valor == null ? "-" : valor.toString();
    }

    //Aqui utilizo el boton para IMPRIMO EXCEL
    @FXML
    private void exportarExcel(ActionEvent event) {
        if (jornadas.isEmpty() || resumenActual == null) {

            mostrarError( bundle.getString( "consultas.error.sinresultados" ) );
            return;
        }

        FileChooser selector = new FileChooser();
            selector.setTitle( bundle.getString( "consultas.excel.titulo" )
        );
            selector.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        bundle.getString( "consultas.excel.tipoarchivo" ),
                        "*.xlsx"
                )
        );
            selector.setInitialFileName(
                "ConsultaMarcas_"
                + dpFechaInicio.getValue()
                + "_al_"
                + dpFechaFin.getValue()
                + ".xlsx"
        );
        File archivo = selector.showSaveDialog( root.getScene().getWindow() );
        if (archivo == null) {
            return;
        }
        if (!archivo.getName() .toLowerCase() .endsWith(".xlsx")) {
            archivo = new File( archivo.getAbsolutePath() + ".xlsx" );
        }
        try {
            excelService.generarExcel(
                    archivo,
                    jornadas,
                    dpFechaInicio.getValue(),
                    dpFechaFin.getValue(),
                    txtFolio.getText(),
                    resumenActual
            );
            mostrarInformacion( bundle.getString( "consultas.excel.exito" ) );
        } catch (IOException ex) {
            LOG.log( Level.SEVERE, "Error generando el archivo Excel", ex );
            mostrarError( bundle.getString(  "consultas.excel.error" ) + " " + ex.getMessage() );
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle( bundle.getString( "consultas.alerta.error.titulo" ) );
        alerta.setHeaderText( bundle.getString( "consultas.alerta.error.encabezado" ) );
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle( bundle.getString( "consultas.alerta.info.titulo" ) );
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
            lblTituloConsulta.setText( bundle.getString("consultas.lbl.titulo") );
            lblDescripcionConsulta.setText( bundle.getString("consultas.lbl.descripcion") );
            lblTituloFiltros.setText(bundle.getString("consultas.lbl.filtros"));
            lblFechaInicio.setText( bundle.getString("consultas.lbl.fechainicio") );          
            lblFechaFin.setText( bundle.getString("consultas.lbl.fechafin") );
            lblFolio.setText( bundle.getString("consultas.lbl.folio") );
            lblTextoCantidadEmpleados.setText( bundle.getString("consultas.lbl.empleados") );
            lblTextoTotalMarcas.setText( bundle.getString("consultas.lbl.totalmarcas") );
            lblTextoTotalHoras.setText( bundle.getString("consultas.lbl.totalhoras") );
            lblTituloDetalle.setText( bundle.getString("consultas.lbl.detalle") );
            lblSinJornadas.setText( bundle.getString("consultas.lbl.sinjornadas") );
            txtFolio.setFloatingText( bundle.getString("consultas.txt.folio") );
            txtFolio.setPromptText( bundle.getString("consultas.txt.todos") );
            btnConsultar.setText( bundle.getString("consultas.btn.consultar") );
            btnExportarExcel.setText( bundle.getString("consultas.btn.excel") );
            colFecha.setText( bundle.getString("consultas.col.fecha") );
            colFolio.setText( bundle.getString("consultas.col.folio") );
            colEmpleado.setText( bundle.getString("consultas.col.empleado") );
            colEntrada.setText( bundle.getString("consultas.col.entrada") );
            colSalida.setText( bundle.getString("consultas.col.salida") );
            colHoras.setText( bundle.getString("consultas.col.horas") );
            colEstado.setText( bundle.getString("consultas.col.estado") );
            tblJornadas.refresh();// Actualiza Completa/Incompleta en la tabla.

        } catch (MissingResourceException ex) {
            LOG.log( Level.SEVERE, "Exception configuring view language at " + "ConsultasViewController.updateLanguageTexts", ex );
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString( "general.notification.language.errortitle" ),
                    bundle.getString( "general.notification.language.errormsg" )
            );
        }
    }
}
