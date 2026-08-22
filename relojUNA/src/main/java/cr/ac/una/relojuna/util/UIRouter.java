package cr.ac.una.relojuna.util;

import cr.ac.una.relojuna.controller.Controller;
import cr.ac.una.relojuna.controller.NotificationController;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public final class UIRouter {

    private static UIRouter instance;

    private static final Logger LOG = Logger.getLogger(UIRouter.class.getName());

    private UIRouter() {
        notificationDuration = DEFAULT_NOTIFICATION_DURATION;
    }

    public static synchronized UIRouter getInstance() {
        if (instance == null) {
            instance = new UIRouter();
        }
        return instance;
    }

    public static final String BASE_VIEW_PATH = "/cr/ac/una/relojuna/view/";
    public static final String FXML_EXTENSION = ".fxml";
    public static final Duration DEFAULT_NOTIFICATION_DURATION = Duration.seconds(3.0);
    public static final Double DEFAULT_MODAL_OPACITY = 0.4;
    public static final Duration DEFAULT_ANIMATION_DURATION = Duration.millis(250);
    public static final String OVERLAY_ID = "ui-manager-overlay";
    public static final String NOTIFICATION_CONTAINER_ID = "ui-manager-notification-container";
    public static final String MODAL_LAYER_ID = "ui-manager-modal-layer";

    private static final String NOTIFICATION_VIEW_NAME = "NotificationView";
    private static final double NOTIFICATION_MARGIN = 16.0;
    private static final Duration NOTIFICATION_FADE_DURATION = Duration.millis(200);

    public enum Position {
        TOP, BOTTOM, LEFT, RIGHT, CENTER
    }

    public enum AlertType {
        ERROR, WARNING, INFO, CONFIRMATION
    }

    public enum NotificationPosition {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    public enum ModalStyle {
        DIALOG, FULLSCREEN, DRAWER, CUSTOM
    }

    public enum AnimationType {
        FADE, SLIDE, SCALE, NONE
    }

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Scene mainScene;

    private StackPane overlayLayer;
    private Pane notificationLayer;
    private StackPane modalLayer;
    private Pane modalBackground;

    private final Map<Position, Controller> activeControllers = new HashMap<>();
    private Controller activeModalController;

    private Parent activeNotificationNode;
    private SequentialTransition activeNotificationTimeline;

    private boolean initialized;
    private boolean modalActive;

    private Duration notificationDuration;

    private final Map<String, Controller> viewCache = new HashMap<>();

    public void init(Stage stage, String mainViewName) {
        runOnFxThread(() -> initInternal(stage, mainViewName));
    }

    public void show(String viewName, Position position) {
        runOnFxThread(() -> showInternal(viewName, position));
    }

    public void close(Position position) {
        runOnFxThread(() -> closeInternal(position));
    }

    public void showModal(String viewName) {
        runOnFxThread(() -> showModalInternal(viewName));
    }

    public void hideModal() {
        runOnFxThread(this::hideModalInternal);
    }

    public void notify(NotificationPosition position,
            NotificationColor color,
            String title,
            String description) {
        runOnFxThread(() -> notifyInternal(position, color, title, description));
    }

    private void runOnFxThread(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }

    private void initInternal(Stage stage, String mainViewName) {
        validateInitialization(stage, mainViewName);
        this.primaryStage = stage;

        BorderPane root = loadRootLayout(mainViewName);
        this.rootLayout = root;

        StackPane rootContainer = buildRootContainer();
        mainScene = new Scene(rootContainer);
        MFXThemeManager.addOn(mainScene, Themes.DEFAULT);

        primaryStage.setScene(mainScene);
        primaryStage.show();

        resetState();
    }

    private void showInternal(String viewName, Position position) {
        checkInitialization();
        validateShowArgs(viewName, position);

        evictControllerFromOtherPositions(viewName, position);

        cleanupActiveController(position);

        Controller controller = loadController(viewName);
        activateController(controller, position);

        placeInLayout(controller.getRoot(), position);
    }

    private void closeInternal(Position position) {
        checkInitialization();
        validateClosePosition(position);

        cleanupActiveController(position);
        clearFromLayout(position);
    }

    private void showModalInternal(String viewName) {
        checkInitialization();
        checkNoActiveModal();

        Controller controller = loadController(viewName);

        activateController(controller, null);
        activeModalController = controller;

        mountModalContent((Parent) controller.getRoot());
        setModalLayerVisible(true);

        modalActive = true;
    }

    private void hideModalInternal() {
        checkInitialization();

        if (!modalActive) {
            return;
        }

        if (activeModalController != null && !activeModalController.canDismiss()) {
            LOG.log(Level.INFO, "Modal dismissal blocked by controller: {0}",
                    activeModalController.getClass().getSimpleName());
            return;
        }

        cleanupController(activeModalController);
        activeModalController = null;

        clearModalContent();
        setModalLayerVisible(false);

        modalActive = false;
    }

    private void notifyInternal(NotificationPosition position,
            NotificationColor color,
            String title,
            String description) {
        checkInitialization();

        dismissActiveNotification();

        NotificationController nc = loadFreshNotificationController(title, description, color);
        activateController(nc, null);

        Parent notificationNode = (Parent) nc.getRoot();
        placeNotification(notificationNode, position);
        notificationLayer.getChildren().add(notificationNode);

        activeNotificationNode = notificationNode;
        activeNotificationTimeline = buildNotificationTimeline(notificationNode);
        activeNotificationTimeline.play();
    }

    private BorderPane loadRootLayout(String mainViewName) {
        Controller controller = loadController(mainViewName);
        Object root = controller.getRoot();

        if (!(root instanceof BorderPane bp)) {
            throw new IllegalStateException(
                    "Main view root must be a BorderPane, got: "
                    + (root == null ? "null" : root.getClass().getSimpleName()));
        }

        return bp;
    }

    private StackPane buildRootContainer() {
        StackPane rootContainer = new StackPane();

        buildOverlayLayer();
        buildModalBackground(rootContainer);
        buildModalLayer();
        buildNotificationLayer();

        overlayLayer.getChildren().addAll(modalBackground, modalLayer, notificationLayer);
        rootContainer.getChildren().addAll(rootLayout, overlayLayer);

        return rootContainer;
    }

    private void validateClosePosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
    }

    private void clearFromLayout(Position position) {
        switch (position) {
            case TOP ->
                rootLayout.setTop(null);
            case BOTTOM ->
                rootLayout.setBottom(null);
            case LEFT ->
                rootLayout.setLeft(null);
            case RIGHT ->
                rootLayout.setRight(null);
            case CENTER ->
                rootLayout.setCenter(null);
            default ->
                throw new UnsupportedOperationException("Unsupported position: " + position);
        }
    }

    private void buildOverlayLayer() {
        overlayLayer = new StackPane();
        overlayLayer.setId(OVERLAY_ID);
        overlayLayer.setPickOnBounds(false);
    }

    private void buildModalBackground(StackPane rootContainer) {
        modalBackground = new Pane();
        modalBackground.setStyle("-fx-background-color: rgba(0,0,0," + DEFAULT_MODAL_OPACITY + ");");
        modalBackground.setVisible(false);
        modalBackground.setManaged(false);
        modalBackground.prefWidthProperty().bind(rootContainer.widthProperty());
        modalBackground.prefHeightProperty().bind(rootContainer.heightProperty());
        modalBackground.setOnMouseClicked(e -> hideModal());
    }

    private void buildModalLayer() {
        modalLayer = new StackPane();
        modalLayer.setId(MODAL_LAYER_ID);
        modalLayer.setVisible(false);
        modalLayer.setManaged(false);
        modalLayer.setPickOnBounds(false);
    }

    private void buildNotificationLayer() {
        notificationLayer = new Pane();
        notificationLayer.setId(NOTIFICATION_CONTAINER_ID);
        notificationLayer.setPickOnBounds(false);
    }

    private void resetState() {
        this.notificationDuration = DEFAULT_NOTIFICATION_DURATION;
        this.modalActive = false;
        this.initialized = true;
    }

    private void validateShowArgs(String viewName, Position position) {
        if (viewName == null || viewName.isBlank()) {
            throw new IllegalArgumentException("View name cannot be null or empty.");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
    }

    private void placeInLayout(Object root, Position position) {
        if (!(root instanceof Parent view)) {
            throw new IllegalStateException("Controller root is not a Parent node.");
        }

        switch (position) {
            case TOP ->
                rootLayout.setTop(view);
            case BOTTOM ->
                rootLayout.setBottom(view);
            case LEFT ->
                rootLayout.setLeft(view);
            case RIGHT ->
                rootLayout.setRight(view);
            case CENTER ->
                rootLayout.setCenter(view);
            default ->
                throw new UnsupportedOperationException("Unsupported position: " + position);
        }
    }

    private void evictControllerFromOtherPositions(String viewName, Position targetPosition) {
        if (!viewCache.containsKey(viewName)) {
            return;
        }

        Controller controller = viewCache.get(viewName);

        activeControllers.entrySet().removeIf(entry
                -> entry.getValue() == controller && entry.getKey() != targetPosition
        );
    }

    private void cleanupActiveController(Position position) {
        Controller outgoing = activeControllers.remove(position);
        cleanupController(outgoing);
    }

    private void cleanupController(Controller controller) {
        if (controller != null) {
            controller.cleanup();
        }
    }

    private void activateController(Controller controller, Position position) {
        if (controller == null) {
            return;
        }

        controller.onRegister();
        controller.initialize();

        if (position != null) {
            activeControllers.put(position, controller);
        }
    }

    private void checkNoActiveModal() {
        if (modalActive) {
            throw new IllegalStateException("A modal is already active.");
        }
    }

    private void mountModalContent(Parent content) {
        modalLayer.getChildren().setAll(content);
    }

    private void clearModalContent() {
        modalLayer.getChildren().clear();
    }

    private void setModalLayerVisible(boolean visible) {
        modalBackground.setVisible(visible);
        modalBackground.setManaged(visible);
        modalLayer.setVisible(visible);
        modalLayer.setManaged(visible);
    }

    private NotificationController loadFreshNotificationController(String title,
            String description,
            NotificationColor color) {
        URL resource = resolveResource(NOTIFICATION_VIEW_NAME);
        Controller ctrl = parseFXML(NOTIFICATION_VIEW_NAME, resource);

        if (!(ctrl instanceof NotificationController nc)) {
            throw new IllegalStateException(
                    "NotificationView controller must be a NotificationController.");
        }

        nc.setup(title, description, color);
        return nc;
    }

    private void placeNotification(Parent node, NotificationPosition position) {
        switch (position) {
            case TOP_LEFT -> {
                node.setLayoutX(NOTIFICATION_MARGIN);
                node.setLayoutY(NOTIFICATION_MARGIN);
            }
            case TOP_RIGHT -> {
                ChangeListener<Bounds> listener = new ChangeListener<>() {
                    @Override
                    public void changed(javafx.beans.value.ObservableValue<? extends Bounds> obs,
                            Bounds oldB, Bounds newB) {
                        if (newB.getWidth() > 0) {
                            node.setLayoutX(notificationLayer.getWidth()
                                    - newB.getWidth() - NOTIFICATION_MARGIN);
                            node.setLayoutY(NOTIFICATION_MARGIN);
                            node.layoutBoundsProperty().removeListener(this);
                        }
                    }
                };
                node.layoutBoundsProperty().addListener(listener);
            }
            case BOTTOM_LEFT -> {
                ChangeListener<Bounds> listener = new ChangeListener<>() {
                    @Override
                    public void changed(javafx.beans.value.ObservableValue<? extends Bounds> obs,
                            Bounds oldB, Bounds newB) {
                        if (newB.getHeight() > 0) {
                            node.setLayoutX(NOTIFICATION_MARGIN);
                            node.setLayoutY(notificationLayer.getHeight()
                                    - newB.getHeight() - NOTIFICATION_MARGIN);
                            node.layoutBoundsProperty().removeListener(this);
                        }
                    }
                };
                node.layoutBoundsProperty().addListener(listener);
            }
            case BOTTOM_RIGHT -> {
                ChangeListener<Bounds> listener = new ChangeListener<>() {
                    @Override
                    public void changed(javafx.beans.value.ObservableValue<? extends Bounds> obs,
                            Bounds oldB, Bounds newB) {
                        if (newB.getWidth() > 0 && newB.getHeight() > 0) {
                            node.setLayoutX(notificationLayer.getWidth()
                                    - newB.getWidth() - NOTIFICATION_MARGIN);
                            node.setLayoutY(notificationLayer.getHeight()
                                    - newB.getHeight() - NOTIFICATION_MARGIN);
                            node.layoutBoundsProperty().removeListener(this);
                        }
                    }
                };
                node.layoutBoundsProperty().addListener(listener);
            }
        }
    }

    private SequentialTransition buildNotificationTimeline(Parent node) {
        FadeTransition fadeIn = new FadeTransition(NOTIFICATION_FADE_DURATION, node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        PauseTransition hold = new PauseTransition(notificationDuration);

        FadeTransition fadeOut = new FadeTransition(NOTIFICATION_FADE_DURATION, node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        SequentialTransition timeline = new SequentialTransition(fadeIn, hold, fadeOut);
        timeline.setOnFinished(e -> removeActiveNotification());

        return timeline;
    }

    private void dismissActiveNotification() {
        if (activeNotificationTimeline != null) {
            activeNotificationTimeline.stop();
            activeNotificationTimeline = null;
        }
        removeActiveNotification();
    }

    private void removeActiveNotification() {
        if (activeNotificationNode != null) {
            notificationLayer.getChildren().remove(activeNotificationNode);
            activeNotificationNode = null;
        }
    }

    private void checkInitialization() {
        if (!initialized) {
            throw new IllegalStateException("UIRouter has not been initialized.");
        }
    }

    private void validateInitialization(Stage stage, String mainViewName) {
        if (initialized) {
            throw new IllegalStateException("UIRouter is already initialized.");
        }
        if (stage == null) {
            throw new IllegalStateException("The primary stage cannot be null.");
        }
        if (mainViewName == null || mainViewName.isBlank()) {
            throw new IllegalStateException("Main view name cannot be null or empty.");
        }
    }

    private Controller loadController(String viewName) {
        if (viewCache.containsKey(viewName)) {
            return viewCache.get(viewName);
        }

        URL resource = resolveResource(viewName);
        Controller ctrl = parseFXML(viewName, resource);

        viewCache.put(viewName, ctrl);
        return ctrl;
    }

    private URL resolveResource(String viewName) {
        String fullPath = BASE_VIEW_PATH + viewName + FXML_EXTENSION;
        URL resource = getClass().getResource(fullPath);

        if (resource == null) {
            throw new IllegalArgumentException("FXML not found at path: " + fullPath);
        }

        return resource;
    }

    private Controller parseFXML(String viewName, URL resource) {
        try {
            FXMLLoader loader = new FXMLLoader(resource);
            loader.load();
            Object rawCtrl = loader.getController();

            if (rawCtrl == null) {
                throw new IllegalStateException(
                        "No controller declared in FXML: " + viewName);
            }

            if (!(rawCtrl instanceof Controller ctrl)) {
                throw new IllegalStateException(
                        "Controller for view '" + viewName
                        + "' does not extend Controller. Got: "
                        + rawCtrl.getClass().getName());
            }

            return ctrl;

        } catch (IOException e) {
            throw new IllegalStateException("Failed to load FXML: " + viewName, e);
        }
    }
}
