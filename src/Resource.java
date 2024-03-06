import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class Resource {

    public static BufferedImage getImageResource(String path) {
        InputStream inputStream = Resource.class.getResourceAsStream(path);

        if (inputStream != null) {
            try (inputStream) {
                return ImageIO.read(inputStream);
            } catch (IOException ignored) {}
        }

        return null;
    }

    public static File getAudioResource(String path) {
        InputStream inputStream = Resource.class.getResourceAsStream(path);

        if (inputStream != null) {
            try {
                // Create a temporary file
                File file = File.createTempFile("tempAudio", ".wav");

                // Copy the contents of the InputStream to the temporary file
                try (OutputStream outputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                return file;
            } catch (IOException ignored) {}
        }

        return null;
    }

}
