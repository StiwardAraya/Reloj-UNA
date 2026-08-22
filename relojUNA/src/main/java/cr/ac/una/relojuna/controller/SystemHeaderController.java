package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.FXAnimator;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SystemHeaderController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Button btnMenu;
    @FXML
    private Label lblAdministrador;

    @Override
    public void initialize() {
        FXAnimator.slideInFromTop(root, 200);
        Platform.runLater(() -> {
            updateLanguageTexts(bundle);
        });
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        //TODO
    }

    @FXML
    private void onActionBtnMenu(ActionEvent event) {
    }

}
