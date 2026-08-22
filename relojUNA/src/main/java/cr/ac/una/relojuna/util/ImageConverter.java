package cr.ac.una.relojuna.util;

import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageConverter {

    public static Image toImage(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new Image(new ByteArrayInputStream(bytes));
    }

    public static byte[] toByte(Image image) {
        if (image == null) {
            return null;
        }
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            Logger.getLogger(ImageConverter.class.getName()).log(Level.SEVERE, "Error al convertir Image a byte[]");
            return null;
        }
    }
}
