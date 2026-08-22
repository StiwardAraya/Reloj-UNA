package cr.ac.una.relojuna.controller;

import cr.ac.una.relojuna.util.NotificationColor;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class NotificationController extends Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Pane notificationTypeSeparator;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblDescription;

    private String pendingTitle;
    private String pendingDescription;
    private NotificationColor pendingColor;

    public void setup(String title, String description, NotificationColor color) {
        this.pendingTitle = title;
        this.pendingDescription = description;
        this.pendingColor = color;
    }

    @Override
    public void initialize() {
        applyColor(pendingColor);
        lblTitle.setText(pendingTitle);
        lblDescription.setText(pendingDescription);
    }

    @Override
    protected void updateLanguageTexts(ResourceBundle bundle) {
    }

    @Override
    public Node getRoot() {
        return root;
    }

    private void applyColor(NotificationColor color) {
        if (color == null) {
            return;
        }

        for (NotificationColor c : NotificationColor.values()) {
            notificationTypeSeparator.getStyleClass().remove(c.getCssClass());
        }

        notificationTypeSeparator.getStyleClass().add(color.getCssClass());
    }
}
