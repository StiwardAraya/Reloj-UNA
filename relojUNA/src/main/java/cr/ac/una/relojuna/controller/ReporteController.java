package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.service.ReporteService;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.ws.ArchivoDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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

    @Override
    public void initialize() {
    }

    @FXML
    private void generarReporteEmpleados(ActionEvent event) {

        Respuesta respuesta = reporteService.obtenerReporteEmpleados();
        if (!respuesta.getEstado()) {

            LOG.severe(
                    "Error reporte empleados: "
                    + respuesta.getMensajeInterno()
            );

            mostrarError(
                    respuesta.getMensaje()
                    + "\n\nDetalle:\n"
                    + respuesta.getMensajeInterno()
            );

            return;
        }

        Object resultado = respuesta.getResultado("Archivo");
        if (!(resultado instanceof ArchivoDTO archivo)) {
            mostrarError("No se recibió el reporte de empleados.");
            return;
        }

        if (archivo.getContenido() == null || archivo.getContenido().length == 0) {
            mostrarError("El reporte recibido está vacío.");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar reporte de empleados");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Documento PDF (*.pdf)", "*.pdf")
        );
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
            mostrarInformacion("Reporte de empleados generado correctamente.");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error al guardar el reporte de empleados", ex);
            mostrarError("No se pudo guardar el reporte.");
        }
    }

    @FXML
    private void generarReporteMarcas(ActionEvent event) {
        LocalDate desde = dpFechaInicio.getValue();
        LocalDate hasta = dpFechaFin.getValue();
        if (desde == null || hasta == null) {
            mostrarError("Debe indicar ambas fechas.");
            return;
        }
        if (hasta.isBefore(desde)) {
            mostrarError("La fecha final no puede ser anterior " + "a la fecha inicial.");
            return;
        }

        mostrarInformacion("La pantalla está lista. Ahora falta conectar " + "el reporte Jasper de marcas.");
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Reportes");
        alerta.setHeaderText("No se puede generar el reporte");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Reportes");
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
        // Se pueden agregar las traducciones posteriormente.
    }
}
