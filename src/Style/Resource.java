package Style;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Resource {

    public static Font getFontResource(String path, float size) {
        InputStream inputStream = Resource.class.getResourceAsStream(path);

        if (inputStream != null) {
            try {
                return Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(size);
            } catch (IOException | FontFormatException ignored) {}
        }

        return null;
    }

}
