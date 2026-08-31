/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.relojuna.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Tames
 */
public class BrandMaintenanceController implements Initializable {

    @FXML
    private MFXDatePicker dpFechaInicio;
    @FXML
    private MFXDatePicker dpFechaFin;
    @FXML
    private MFXButton btnConsultar;
    @FXML
    private MFXComboBox<?> cbEmpleado;
    @FXML
    private MFXComboBox<?> cbTipo;
    @FXML
    private MFXDatePicker dpFecha;
    @FXML
    private MFXTextField txtHora;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private MFXButton btnModificar;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnLimpiar;
    @FXML
    private Label lblCantidadInconsistentes;
    @FXML
    private MFXButton btnRevisarInconsistencias;
    @FXML
    private MFXButton btnSiguienteInconsistente;
    @FXML
    private TableView<?> tablaMarcas;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colEmpleado;
    @FXML
    private TableColumn<?, ?> colTipo;
    @FXML
    private TableColumn<?, ?> colFechaHora;
    @FXML
    private TableColumn<?, ?> colEstado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void consultar(ActionEvent event) {
    }

    @FXML
    private void nuevo(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {
    }

    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void limpiar(ActionEvent event) {
    }

    @FXML
    private void revisarInconsistencias(ActionEvent event) {
    }

    @FXML
    private void siguienteInconsistente(ActionEvent event) {
    }
    
}
