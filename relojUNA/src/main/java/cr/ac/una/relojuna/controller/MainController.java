package cr.ac.una.relojuna.controller;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class MainController extends Controller {

    @FXML
    private BorderPane root;

    @Override
    public void initialize() {
        // TODO
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
        // TODO
    }

}
