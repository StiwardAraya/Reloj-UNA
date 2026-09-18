package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.service.ReporteService;
import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.ws.ArchivoDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class ReporteController extends Controller {

    private static final Logger LOG = Logger.getLogger(ReporteController.class.getName());

    private final ReporteService reporteService = new ReporteService();

    @FXML
    private AnchorPane root;
    @FXML
    private MFXDatePicker dpFechaInicio;
    @FXML
    private MFXDatePicker dpFechaFin;
    @FXML
    private MFXButton btnReporteEmpleados;
    @FXML
    private MFXTextField txtFolio;
    @FXML
    private MFXButton btnReporteMarcas;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblTituloEmpleados;
    @FXML
    private Label lblDescripcionEmpleados;
    @FXML
    private Label lblFormatoEmpleados;
    @FXML
    private Label lblTituloMarcas;
    @FXML
    private Label lblDescripcionMarcas;
    @FXML
    private Label lblFechaInicio;
    @FXML
    private Label lblFechaFin;
    @FXML
    private Label lblFolio;
    @FXML
    private Label lblNota;

    @Override
    public void initialize() {
        FXAnimator.slideInFromRight(root, 100);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
    }

    @FXML
    private void generarReporteEmpleados(ActionEvent event) {

        Respuesta respuesta = reporteService.obtenerReporteEmpleados();
        if (!respuesta.getEstado()) {
            LOG.severe("Error reporte empleados: " + respuesta.getMensajeInterno());
            mostrarError(traducirMensaje(respuesta.getMensaje()));
            return;
        }

        Object resultado = respuesta.getResultado("Archivo");
        if (!(resultado instanceof ArchivoDTO archivo)) {
            mostrarError(bundle.getString("reporte.error.sinreporteempleados"));
            return;
        }

        if (archivo.getContenido() == null || archivo.getContenido().length == 0) {
            mostrarError(bundle.getString("reporte.error.reportevacio"));
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("reporte.file.empleados"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(bundle.getString("reporte.file.pdf"), "*.pdf"));
        String nombreArchivo = archivo.getNombreArchivo();

        if (nombreArchivo == null || nombreArchivo.isBlank()) {
            nombreArchivo = "ReporteEmpleados.pdf";
        }
        fileChooser.setInitialFileName(nombreArchivo);
        File destino = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (destino == null) {
            return;
        }
        if (!destino.getName().toLowerCase().endsWith(".pdf")) {
            destino = new File(destino.getParentFile(), destino.getName() + ".pdf");
        }
        try {
            Files.write(destino.toPath(), archivo.getContenido());
            mostrarInformacion(bundle.getString("reporte.msg.empleadosgenerado"));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error al guardar el reporte de empleados", ex);
            mostrarError(bundle.getString("reporte.error.guardar"));
        }
    }

    @FXML
    private void generarReporteMarcas(ActionEvent event) {
        LocalDate desde = dpFechaInicio.getValue();
        LocalDate hasta = dpFechaFin.getValue();
        String folio = txtFolio.getText();
        if (desde == null || hasta == null) {
            mostrarError(bundle.getString("reporte.error.fechasrequeridas"));
            return;
        }
        if (hasta.isBefore(desde)) {
            mostrarError(bundle.getString("reporte.error.rangofechas"));
            return;
        }

        Respuesta respuesta = reporteService.obtenerReporteMarcas(desde, hasta, folio);
        if (!respuesta.getEstado()) {
            LOG.severe("Error reporte marcas: " + respuesta.getMensajeInterno());
            mostrarError(traducirMensaje(respuesta.getMensaje()));
            return;
        }

        Object resultado = respuesta.getResultado("Archivo");
        if (!(resultado instanceof ArchivoDTO archivo)) {
            mostrarError(bundle.getString("reporte.error.sinreportemarcas"));
            return;
        }
        if (archivo.getContenido() == null || archivo.getContenido().length == 0) {
            mostrarError(bundle.getString("reporte.error.reportevacio"));
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("reporte.file.marcas"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(bundle.getString("reporte.file.pdf"), "*.pdf"));
        String nombreArchivo = archivo.getNombreArchivo();
        if (nombreArchivo == null || nombreArchivo.isBlank()) {
            nombreArchivo = "ReporteMarcas_" + desde + "_" + hasta + ".pdf";
        }

        fileChooser.setInitialFileName(nombreArchivo);
        File destino = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (destino == null) {
            return;
        }
        if (!destino.getName().toLowerCase().endsWith(".pdf")) {

            destino = new File(destino.getParentFile(), destino.getName() + ".pdf");
        }
        try {
            Files.write(destino.toPath(), archivo.getContenido());
            mostrarInformacion(bundle.getString("reporte.msg.marcasgenerado"));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error al guardar el reporte de marcas", ex);
            mostrarError(bundle.getString("reporte.error.guardar"));
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(bundle.getString("reporte.alert.titulo"));
        alerta.setHeaderText(bundle.getString("reporte.alert.error"));
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(bundle.getString("reporte.alert.titulo"));
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
            lblTitulo.setText(bundle.getString("reporte.lbl.titulo"));
            lblDescripcion.setText(bundle.getString("reporte.lbl.descripcion"));
            lblTituloEmpleados.setText(bundle.getString("reporte.lbl.tituloempleados"));
            lblDescripcionEmpleados.setText(bundle.getString("reporte.lbl.descripcionempleados"));
            lblFormatoEmpleados.setText(bundle.getString("reporte.lbl.formatoempleados"));
            lblTituloMarcas.setText(bundle.getString("reporte.lbl.titulomarcas"));
            lblDescripcionMarcas.setText(bundle.getString("reporte.lbl.descripcionmarcas"));
            lblFechaInicio.setText(bundle.getString("reporte.lbl.fechainicio"));
            lblFechaFin.setText(bundle.getString("reporte.lbl.fechafin"));
            lblFolio.setText(bundle.getString("reporte.lbl.folio"));
            txtFolio.setPromptText(bundle.getString("reporte.txt.todos"));
            btnReporteEmpleados.setText(bundle.getString("reporte.btn.empleados"));
            btnReporteMarcas.setText(bundle.getString("reporte.btn.marcas"));
            lblNota.setText(bundle.getString("reporte.lbl.nota"));

        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at " + "ReporteController.updateLanguageTexts", ex);
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
