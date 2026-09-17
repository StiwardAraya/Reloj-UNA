package cr.ac.una.relojuna.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public final class SFXPlayer {

    private static MediaPlayer currentPlayer;

    private SFXPlayer() {
    }

    public static void reproducir(String archivo) {
        detener();
        try {
            String ruta = SFXPlayer.class.getResource(
                    "/cr/ac/una/relojuna/sound/" + archivo).toExternalForm();
            currentPlayer = new MediaPlayer(new Media(ruta));
            currentPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reproducirEnBucle(String archivo) {
        detener();
        try {
            String ruta = SFXPlayer.class.getResource(
                    "/cr/ac/una/relojuna/sound/" + archivo).toExternalForm();
            currentPlayer = new MediaPlayer(new Media(ruta));
            currentPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            currentPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void detener() {
        if (currentPlayer != null) {
            currentPlayer.stop();
            currentPlayer.dispose();
            currentPlayer = null;
        }
    }

    public static void setVolumen(double volumen) {
        if (currentPlayer != null) {
            currentPlayer.setVolume(volumen);
        }
    }
}
