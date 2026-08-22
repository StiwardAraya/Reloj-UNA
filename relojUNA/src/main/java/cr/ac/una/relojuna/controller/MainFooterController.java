package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.NotificationColor;
import cr.ac.una.relojuna.util.UIRouter;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainFooterController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblSeguro;
    @FXML
    private Label lblSello;

    Logger LOG;

    // heredados
    @Override
    public void initialize() {
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
        LOG = Logger.getLogger(MainFooterController.class.getName());
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        try {
            this.lblSeguro.setText(bundle.getString("mainfooter.lbl.seguridad"));
            this.lblSello.setText(bundle.getString("mainfooter.lbl.sello"));
        } catch (MissingResourceException ex) {
            LOG.log(Level.SEVERE, "Exception configuring view language at MainHeaderController.updateLanguageTexts", ex);
            UIRouter.getInstance().notify(
                    UIRouter.NotificationPosition.BOTTOM_RIGHT,
                    NotificationColor.WARNING,
                    bundle.getString("general.notification.language.errortitle"),
                    bundle.getString("general.notification.language.errormsg"));
        }
    }

}
