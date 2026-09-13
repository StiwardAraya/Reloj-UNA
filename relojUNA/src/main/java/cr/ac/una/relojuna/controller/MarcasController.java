package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.model.MarcaViewModel;
import cr.ac.una.relojuna.service.MarcaService;
import cr.ac.una.relojuna.util.FXAnimator;
import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.Respuesta;
import cr.ac.una.relojuna.util.TipoMarca;
import cr.ac.una.relojuna.util.UIRouter;
import cr.ac.una.relojuna.ws.MarcaDTO;
import cr.ac.una.relojuna.ws.MarcaListDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public class MarcasController extends Controller {

    @FXML
    private Label lblInfo;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblTitulo;
    @FXML
    private MFXTextField txtFolio;
    @FXML
    private MFXDatePicker dtpDesde;
    @FXML
    private MFXDatePicker dtpHasta;
    @FXML
    private MFXButton btnAgregar;
    @FXML
    private TableView<MarcaViewModel> tbMarcas;
    @FXML
    private TableColumn<MarcaViewModel, String> clFolio;
    @FXML
    private TableColumn<MarcaViewModel, LocalDate> clFecha;
    @FXML
    private MFXCheckbox chkVerInconsistencias;
    @FXML
    private MFXButton btnRevalidar;
    @FXML
    private TableColumn<MarcaViewModel, LocalTime> clHora;
    @FXML
    private TableColumn<MarcaViewModel, TipoMarca> clTipo;
    @FXML
    private TableColumn<MarcaViewModel, Void> clControles;

    private final static Logger LOG = Logger.getLogger(MarcasController.class.getName());

    private final ObservableList<MarcaViewModel> marcas = FXCollections.observableArrayList();
    private final FilteredList<MarcaViewModel> marcasFiltradas = new FilteredList<>(marcas, m -> true);
    private final MarcaService service = new MarcaService();
    private MarcaViewModel marcaEnEdicion;

    @Override
    public void initialize() {
        FXAnimator.slideInFromRight(root, 20);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
        configurarFechasIniciales();
        configurarTabla();
        cargarMarcas();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            lblTitulo.setText(bundle.getString("marcas.lbl.titulo"));
            lblInfo.setText(bundle.getString("marcas.lbl.info"));
            txtFolio.setFloatingText(bundle.getString("marcas.txt.folio"));
            dtpDesde.setFloatingText(bundle.getString("marcas.dtp.desde"));
            dtpHasta.setFloatingText(bundle.getString("marcas.dtp.hasta"));
            btnBuscar.setText(bundle.getString("marcas.btn.buscar"));
            btnAgregar.setText(bundle.getString("marcas.btn.agregar"));
            clFolio.setText(bundle.getString("marcas.col.folio"));
            clHora.setText(bundle.getString("marcas.col.hora"));
            clFecha.setText(bundle.getString("marcas.col.fecha"));
            clTipo.setText(bundle.getString("marcas.col.tipo"));
            clControles.setText(bundle.getString("marcas.col.controles"));
            chkVerInconsistencias.setText(bundle.getString("marcas.chk.inconsistencia"));
            btnRevalidar.setText(bundle.getString("marcas.btn.validar"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at MarcasController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

    // Main
    private void cargarMarcas() {
        LocalDate desde = dtpDesde.getValue();
        LocalDate hasta = dtpHasta.getValue();

        Respuesta respuesta = service.obtenerPorFechas(desde, hasta);
        manejarRespuesta(respuesta, () -> {
            @SuppressWarnings("unchecked")
            List<MarcaDTO> dtos = ((MarcaListDTO) respuesta.getResultado("Marcas")).getMarcas();
            refrescarDatos(dtos);
        });
    }

    private void buscarMarcas() {
        // TODO
    }

    private void agregarMarca() {
        // TODO
    }

    private void guardarFila(MarcaViewModel row) {
        // TODO
    }

    private void cancelarEdicionFila(MarcaViewModel row) {
        // TODO
    }

    private void editarMarca(MarcaViewModel row) {
        // TODO
    }

    private void eliminarMarca(MarcaViewModel row) {
        // TODO
    }

    private void revalidarMarcas() {
        // TODO
    }

    private void filtrarInconsistencias(boolean soloInconsistentes) {
        // TODO
    }

    // Helpers
    private void configurarTabla() {
        tbMarcas.setItems(marcas);
        tbMarcas.setEditable(false);

        clFolio.setCellValueFactory(cd -> cd.getValue().folioProperty());
        clFolio.setCellFactory(col -> celdaFolio());

        clFecha.setCellValueFactory(cd -> cd.getValue().fechaProperty());
        clFecha.setCellFactory(col -> celdaFecha());

        clHora.setCellValueFactory(cd -> cd.getValue().horaProperty());
        clHora.setCellFactory(col -> celdaHora());

        clTipo.setCellValueFactory(cd -> cd.getValue().tipoProperty());
        clTipo.setCellFactory(col -> celdaTipo());

        clControles.setCellFactory(col -> celdaControles());

        tbMarcas.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(MarcaViewModel row, boolean empty) {
                super.updateItem(row, empty);
                if (empty || row == null) {
                    setStyle("");
                } else if (row.inconsistenteProperty().get()) {
                    setStyle("-fx-background-color: #fff59d;"); // amarillo
                } else {
                    setStyle("");
                }
            }
        });
    }

    private void refrescarDatos(List<MarcaDTO> dtos) {
        marcas.clear();
        if (dtos != null) {
            for (MarcaDTO dto : dtos) {
                marcas.add(new MarcaViewModel(dto));
            }
        }
        marcaEnEdicion = null;
        tbMarcas.refresh();
    }

    private void manejarRespuesta(Respuesta respuesta, Runnable siExito) {
        if (respuesta == null) {
            LOG.log(Level.SEVERE, "Respuesta nula recibida del servicio en MarcasController");
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.ERROR,
                    bundle.getString("general.notification.error.titulo"),
                    bundle.getString("general.notification.error.msg"));
            return;
        }

        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            if (siExito != null) {
                siExito.run();
            }
        } else {
            if (respuesta.getMensajeInterno() != null) {
                LOG.log(Level.WARNING, respuesta.getMensajeInterno());
            }
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.ERROR,
                    bundle.getString("general.notification.error.titulo"),
                    respuesta.getMensaje());
        }
    }

    private boolean validarFila(MarcaViewModel row) {
        if (row == null) {
            return false;
        }
        if (row.folioProperty().get() == null || row.folioProperty().get().isBlank()) {
            return false;
        }
        if (row.fechaProperty().get() == null) {
            return false;
        }
        if (row.horaProperty().get() == null) {
            return false;
        }
        return row.tipoProperty().get() != null;
    }

    private TableCell<MarcaViewModel, String> celdaFolio() {
        return new TableCell<>() {
            private final MFXTextField campo = new MFXTextField();

            {
                setAlignment(Pos.CENTER);
                campo.textProperty().addListener((obs, old, val) -> {
                    MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();
                    if (row != null) {
                        row.folioProperty().set(val);
                    }
                });
            }

            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);
                MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();

                if (empty || row == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                if (esEditable(row)) {
                    campo.setText(value);
                    setGraphic(campo);
                    setText(null);
                } else {
                    setGraphic(null);
                    setText(value);
                }
            }
        };
    }

    private TableCell<MarcaViewModel, LocalDate> celdaFecha() {
        return new TableCell<>() {
            private final MFXDatePicker picker = new MFXDatePicker();

            {
                setAlignment(Pos.CENTER);
                picker.valueProperty().addListener((obs, old, val) -> {
                    MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();
                    if (row != null) {
                        row.fechaProperty().set(val);
                    }
                });
            }

            @Override
            protected void updateItem(LocalDate value, boolean empty) {
                super.updateItem(value, empty);
                MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();

                if (empty || row == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                if (esEditable(row)) {
                    picker.setValue(value);
                    setGraphic(picker);
                    setText(null);
                } else {
                    setGraphic(null);
                    setText(value == null ? "" : value.toString());
                }
            }
        };
    }

    private TableCell<MarcaViewModel, LocalTime> celdaHora() {
        return new TableCell<>() {
            private final Spinner<Integer> spnHora = crearSpinner(23, "spinner-hora");
            private final Spinner<Integer> spnMinuto = crearSpinner(59, "spinner-minuto");
            private final HBox contenedor = new HBox(4, spnHora, new Label(":"), spnMinuto);
            private boolean actualizandoDesdeRow = false;

            {
                setAlignment(Pos.CENTER);
                spnHora.valueProperty().addListener((obs, old, val) -> notificarCambioHora());
                spnMinuto.valueProperty().addListener((obs, old, val) -> notificarCambioHora());
            }

            private void notificarCambioHora() {
                if (actualizandoDesdeRow) {
                    return;
                }
                MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();
                if (row != null) {
                    row.horaProperty().set(LocalTime.of(spnHora.getValue(), spnMinuto.getValue()));
                }
            }

            @Override
            protected void updateItem(LocalTime value, boolean empty) {
                super.updateItem(value, empty);
                MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();

                if (empty || row == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                if (esEditable(row)) {
                    LocalTime valorInicial = value != null ? value : LocalTime.of(0, 0);
                    actualizandoDesdeRow = true;
                    spnHora.getValueFactory().setValue(valorInicial.getHour());
                    spnMinuto.getValueFactory().setValue(valorInicial.getMinute());
                    actualizandoDesdeRow = false;
                    setGraphic(contenedor);
                    setText(null);
                } else {
                    setGraphic(null);
                    setText(value == null ? "" : value.toString());
                }
            }
        };
    }

    private Spinner<Integer> crearSpinner(int max, String cssClass) {
        SpinnerValueFactory.IntegerSpinnerValueFactory factory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, max, 0);
        factory.setWrapAround(true);
        factory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer valor) {
                return String.format("%02d", valor == null ? 0 : valor);
            }

            @Override
            public Integer fromString(String texto) {
                try {
                    int valor = Integer.parseInt(texto.trim());
                    if (valor < 0 || valor > max) {
                        return factory.getValue();
                    }
                    return valor;
                } catch (NumberFormatException ex) {
                    return factory.getValue();
                }
            }
        });

        Spinner<Integer> spinner = new Spinner<>(factory);
        spinner.setEditable(true);
        spinner.getStyleClass().add(cssClass);
        spinner.getEditor().focusedProperty().addListener((obs, teniaFoco, tieneFoco) -> {
            if (!tieneFoco) {
                spinner.increment(0);
            }
        });
        return spinner;
    }

    private TableCell<MarcaViewModel, TipoMarca> celdaTipo() {
        return new TableCell<>() {
            private final MFXComboBox<TipoMarca> combo
                    = new MFXComboBox<>(FXCollections.observableArrayList(TipoMarca.values()));

            {
                setAlignment(Pos.CENTER);
                combo.valueProperty().addListener((obs, old, val) -> {
                    MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();
                    if (row != null) {
                        row.tipoProperty().set(val);
                    }
                });
            }

            @Override
            protected void updateItem(TipoMarca value, boolean empty) {
                super.updateItem(value, empty);
                MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();

                if (empty || row == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                if (esEditable(row)) {
                    combo.setValue(value);
                    setGraphic(combo);
                    setText(null);
                } else {
                    setGraphic(null);
                    setText(value == null ? "" : value.getDescripcion());
                }
            }
        };
    }

    private TableCell<MarcaViewModel, Void> celdaControles() {
        return new TableCell<>() {
            private final MFXButton btnAccion = new MFXButton("");
            private final MFXButton btnSecundario = new MFXButton("");
            private final HBox contenedor = new HBox(8, btnAccion, btnSecundario);

            {
                btnAccion.setOnAction(e -> {
                    MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();
                    if (row == null) {
                        return;
                    }
                    if (esEditable(row)) {
                        guardarFila(row);
                    } else {
                        editarMarca(row);
                    }
                });
                btnSecundario.setOnAction(e -> {
                    MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();
                    if (row == null) {
                        return;
                    }
                    if (esEditable(row)) {
                        cancelarEdicionFila(row);
                    } else {
                        eliminarMarca(row);
                    }
                });
            }

            @Override
            protected void updateItem(Void value, boolean empty) {
                super.updateItem(value, empty);
                MarcaViewModel row = getTableRow() == null ? null : getTableRow().getItem();

                if (empty || row == null) {
                    setGraphic(null);
                    return;
                }

                boolean editable = esEditable(row);
                btnAccion.setText(bundle.getString(editable ? "marcas.btn.guardar" : "marcas.btn.editar"));
                btnSecundario.setText(bundle.getString(editable ? "marcas.btn.cancelar" : "marcas.btn.eliminar"));
                setGraphic(contenedor);
            }
        };
    }

    private boolean esEditable(MarcaViewModel row) {
        return row != null && (row.isNueva() || row.editandoProperty().get());
    }

    private void configurarFechasIniciales() {
        LocalDate hasta = LocalDate.now();
        LocalDate desde = hasta.minusDays(30);

        dtpDesde.setValue(desde);
        dtpHasta.setValue(hasta);
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        buscarMarcas();
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        agregarMarca();
    }

    @FXML
    private void onActionBtnRevalidar(ActionEvent event) {
        revalidarMarcas();
    }

    @FXML
    private void onActionChkVerInconsistencias(ActionEvent event) {
        filtrarInconsistencias(chkVerInconsistencias.isSelected());
    }
}
