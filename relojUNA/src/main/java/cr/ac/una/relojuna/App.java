package cr.ac.una.relojuna;

import cr.ac.una.relojuna.util.LanguagesManager;
import cr.ac.una.relojuna.util.UIRouter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        LanguagesManager.setLocale(Locale.of("es", "CR"));
        UIRouter.getInstance().init(stage, "MainView");
        UIRouter.getInstance().show("MainHeaderView", UIRouter.Position.TOP);
        UIRouter.getInstance().show("MainFooterView", UIRouter.Position.BOTTOM);
        UIRouter.getInstance().show("MarcadorView", UIRouter.Position.CENTER);
    }

    public static void main(String[] args) {
        launch();
    }

}
