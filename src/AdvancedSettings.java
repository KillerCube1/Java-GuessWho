import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Style.MainMenuButton;

public class AdvancedSettings {

    private static JFrame frame;
    private static final JLabel info = new JLabel("", SwingConstants.CENTER);

    /**
     * Displays the main menu of the game.
     */
    public AdvancedSettings(){
        frame = new JFrame("Advanced Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Increase frame size
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(7, 1));
        panel.add(new JLabel("Advanced Settings Menu", SwingConstants.CENTER));
        updateInfo();
        panel.add(info);

        // Volume controls and music buttons
        JButton playButton = new MainMenuButton("Use Server", 25, 130, 165, 45);
        playButton.addActionListener(e -> hostOnServer());

        JButton hostButton = new MainMenuButton("Host LAN", 25, 180, 165, 45);
        hostButton.addActionListener(e -> runLANServer());

        JButton stopButton = new MainMenuButton("Use LAN", 25, 230, 165, 45);
        stopButton.addActionListener(e -> hostOnLAN());

        JButton backButton = new MainMenuButton("Back", 25, 280, 165, 45);
        backButton.addActionListener(e -> {
            frame.dispose();
            MainMenu.openSettings();
        });

        panel.add(playButton);
        panel.add(hostButton);
        panel.add(stopButton);
        panel.add(backButton);

        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }

    private void updateInfo() {
        if (MainMenu.usingLAN()) {
            info.setText(
                    "<html>[ Connected to LAN " + MainMenu.getIP() + " ]<br/>When hosting on a LAN, make sure the server is running locally.</html>"
            );
        } else {
            info.setText(
                    "[ Connected to default servers ]"
            );
        }
    }

    private void hostOnServer() {
        System.out.println("Hosting on main server");
        MainMenu.setLANConnection(false);
        Settings.updateMainServer(false);
        updateInfo();
    }

    private void runLANServer() {
        try {
            String directoryPath = "src";
            String jarFilePath = "LanServer.jar";

            File jarFile = new File(directoryPath, jarFilePath);

            if (!jarFile.exists()) {
                System.err.println("Error: LanServer.jar not found in directory " + directoryPath);
                return;
            }

            String[] command = {"java", "-jar", jarFilePath};

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File(directoryPath));

            Process process = pb.start();

            // Redirect output and error streams to console
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("LAN Server Process exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    Old one the new one is for testing
        private void runLANServer() {
        try {
            String directoryPath = "src";

            String[] command = {"java", "-jar", "LanServer.jar"};

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File(directoryPath));

            pb.start();
        } catch (IOException ignored) {}
    }

     */


    private void hostOnLAN() {
        System.out.println("Hosting on a LAN");
        MainMenu.setLANConnection(true);

        frame.setVisible(false);

        JFrame IPInput = new JFrame("IP Address Input");
        IPInput.setLocationRelativeTo(null);

        JTextField ipAddressField = new JTextField();
        ipAddressField.setBounds(50, 50, 200, 30);

        JLabel label = new JLabel("Enter IP Address:");
        label.setBounds(50, 20, 150, 30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 100, 100, 30);

        submitButton.addActionListener(e -> {
            String ipAddress = ipAddressField.getText();
            MainMenu.setLANIP(ipAddress);
            Settings.updateMainServer(true);
            Settings.updateLanIP(ipAddress);
            updateInfo();
            IPInput.dispose();
            frame.setVisible(true);
        });

        IPInput.add(ipAddressField);
        IPInput.add(label);
        IPInput.add(submitButton);
        frame.getRootPane().setDefaultButton(submitButton);

        IPInput.setSize(300, 200);
        IPInput.setLayout(null);
        IPInput.setVisible(true);
        IPInput.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}