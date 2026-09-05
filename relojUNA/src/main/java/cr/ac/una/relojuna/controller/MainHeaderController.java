package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainHeaderController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblSubTitulo;

    Logger LOG;

    @Override
    public void initialize() {
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
        LOG = Logger.getLogger(MainHeaderController.class.getName());
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            this.lblTitulo.setText(bundle.getString("mainheader.lbl.titulo"));
            this.lblSubTitulo.setText(bundle.getString("mainheader.lbl.subtitulo"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at MainHeaderController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

    // Main
    private void mostrarLogin() {
        UIRouter.getInstance().show("LoginView", UIRouter.Position.CENTER);
    }

    private void mostrarMarcador() {
        UIRouter.getInstance().show("MarcadorView", UIRouter.Position.CENTER);
    }

}
