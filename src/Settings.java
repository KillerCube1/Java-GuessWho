import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {
    public static void updateVolume(int volume) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // Load the properties file
            inputStream = new FileInputStream("settings.properties");
            properties.load(inputStream);

            // Set the property
            properties.setProperty("volume", String.valueOf(volume));

            // Save the updated properties file
            outputStream = new FileOutputStream("settings.properties");
            properties.store(outputStream, null);
        } catch (IOException io) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void updateMainServer(boolean server) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // Load the properties file
            inputStream = new FileInputStream("settings.properties");
            properties.load(inputStream);

            // Set the property
            properties.setProperty("usingLAN", String.valueOf(server));

            // Save the updated properties file
            outputStream = new FileOutputStream("settings.properties");
            properties.store(outputStream, null);
        } catch (IOException io) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void updateLanIP(String IP) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // Load the properties file
            inputStream = new FileInputStream("settings.properties");
            properties.load(inputStream);

            // Set the property
            properties.setProperty("serverListIP", IP);

            // Save the updated properties file
            outputStream = new FileOutputStream("settings.properties");
            properties.store(outputStream, null);
        } catch (IOException io) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
