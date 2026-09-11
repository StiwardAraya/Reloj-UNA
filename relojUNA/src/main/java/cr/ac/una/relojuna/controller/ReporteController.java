package cr.ac.una.relojuna.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class ReporteViewController extends Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private MFXDatePicker dpFechaInicio;

    @FXML
    private MFXDatePicker dpFechaFin;

    @FXML
    private MFXTextField txtFolio;

    @FXML
    private MFXButton btnReporteEmpleados;

    @FXML
    private MFXButton btnReporteMarcas;

    @Override
    public void initialize() {
        dpFechaInicio.setValue(
                LocalDate.now().withDayOfMonth(1)
        );

        dpFechaFin.setValue(LocalDate.now());
    }

    @FXML
    private void generarReporteEmpleados(ActionEvent event) {
        mostrarInformacion(
                "La pantalla está lista. Ahora falta conectar " + "el reporte Jasper de empleados."
        );
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
            mostrarError( "La fecha final no puede ser anterior " + "a la fecha inicial." );
            return;
        }

        mostrarInformacion( "La pantalla está lista. Ahora falta conectar " + "el reporte Jasper de marcas." );
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