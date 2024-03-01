import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The entry point for running the application.
 */
public class Run {

        // private static boolean isMultiplayer = false;
        /**
         * Main method to start the application by invoking the main menu.
         *
         * @param args Command-line arguments (not used).
         */
        public static void main(String[] args) {

                fetchSettings();
                MainMenu.mainMenu();

        }

        private static void fetchSettings() {

                Properties properties = new Properties();

                try {

                        // Fetch settings from file
                        properties.load(new FileInputStream("settings.properties"));
                        String volume = properties.getProperty("volume");
                        String useMainServer = properties.getProperty("useMainServer");
                        String serverIP = properties.getProperty("serverListIP");
                        
                        System.out.println(volume);
                        System.out.println(useMainServer);
                        System.out.println(serverIP);

                } catch (IOException e) {

                        // Reset to default settings
                        properties.setProperty("volume", "100");
                        properties.setProperty("useMainServer", "true");
                        properties.setProperty("serverListIP", "null");

                        try {
                                properties.store(new FileOutputStream("settings.properties"), null);
                        } catch (FileNotFoundException e1) {
                                new File("settings.properties");
                                try {
                                        properties.store(new FileOutputStream("settings.properties"), null);
                                } catch (IOException e2) {}
                        } catch (IOException e1) {}
                        
                }

        }
}
