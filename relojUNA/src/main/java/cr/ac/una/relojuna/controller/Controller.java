package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.LanguageChangeListener;
import cr.ac.una.relojuna.util.LanguagesManager;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public abstract class Controller implements LanguageChangeListener {

    private Stage stage;
    private String accion;
    private String nombreVista;

    protected ResourceBundle bundle;

    public abstract void initialize();

    public abstract Node getRoot();

    protected abstract void updateLanguageTexts(ResourceBundle bundle);

    public void onRegister() {
        LanguagesManager.addLanguageChangeListener(this);

        ResourceBundle current = LanguagesManager.getCurrentBundle();
        if (current != null) {
            this.bundle = current;
        }
    }

    @Override
    public final void onLanguageChanged(ResourceBundle newBundle) {
        this.bundle = newBundle;
        updateLanguageTexts(newBundle);
    }

    public boolean canDismiss() {
        return true;
    }

    public void cleanup() {
        LanguagesManager.removeLanguageChangeListener(this);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getNombreVista() {
        return nombreVista;
    }

    public void setNombreVista(String nombreVista) {
        this.nombreVista = nombreVista;
    }

    public void sendTabEvent(KeyEvent event) {
        event.consume();
        KeyEvent tabEvent = new KeyEvent(
                KeyEvent.KEY_PRESSED,
                null, null,
                KeyCode.TAB,
                false, false, false, false
        );
        ((Control) event.getSource()).fireEvent(tabEvent);
    }
}
