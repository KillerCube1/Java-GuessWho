import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Style.MainMenuButton;

public class AdvancedSettings {
    
    private static JFrame frame;
    private static JLabel info = new JLabel("", SwingConstants.CENTER);

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
        updateInfo();
    }

    private void runLANServer() {
        try {
            // Specify the path to the directory containing LanServer.jar
            String directoryPath = "src";
            
            // Specify the command to run LanServer.jar
            String[] command = {"java", "-jar", "LanServer.jar"};

            // Create ProcessBuilder instance with directory path
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File(directoryPath));

            // Start the process
            pb.start();
        } catch (IOException e) {}
    }

    private void hostOnLAN() {
        System.out.println("Hosting on a LAN");
        MainMenu.setLANConnection(true);

        frame.setVisible(false);

        // Create a popup dialog
        JFrame IPInput = new JFrame("IP Address Input");
        IPInput.setLocationRelativeTo(null);
        
        // Create a text field for IP address input
        JTextField ipAddressField = new JTextField();
        ipAddressField.setBounds(50, 50, 200, 30);
        
        // Create a label for the text field
        JLabel label = new JLabel("Enter IP Address:");
        label.setBounds(50, 20, 150, 30);
        
        // Create a button to submit the IP address
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 100, 100, 30);
        
        // Add action listener to the submit button
        submitButton.addActionListener(e -> {
            String ipAddress = ipAddressField.getText();
            MainMenu.setLANIP(ipAddress);
            updateInfo();
            IPInput.dispose();
            frame.setVisible(true);
        });
        
        // Add components to the frame
        IPInput.add(ipAddressField);
        IPInput.add(label);
        IPInput.add(submitButton);
        
        // Set frame properties
        IPInput.setSize(300, 200);
        IPInput.setLayout(null);
        IPInput.setVisible(true);
        IPInput.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
