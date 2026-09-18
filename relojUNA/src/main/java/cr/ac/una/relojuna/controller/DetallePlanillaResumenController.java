package cr.ac.una.relojuna.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class DetallePlanillaResumenController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
