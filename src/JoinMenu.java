
import javax.swing.*;
import java.awt.*;

public class JoinMenu extends JFrame {

    private final JTextField ipTextField;
    private final JTextField portTextField;

    public JoinMenu() {
        // Set up the frame
        setTitle("IP and Port Input");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2));

        // Add IP label and text field
        mainPanel.add(new JLabel(" IP:"));
        ipTextField = new JTextField();
        mainPanel.add(ipTextField);

        // Add Port label and text field
        mainPanel.add(new JLabel(" Port:"));
        portTextField = new JTextField();
        mainPanel.add(portTextField);

        // Add Enter button
        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> {
            // Handle enter button click
            String ip = ipTextField.getText();
            String port = portTextField.getText();
            // You can perform any action with the entered IP and Port here
            JoinMenu.this.dispose();
            new Client(ip, Integer.parseInt(port));
        });

        mainPanel.add(enterButton);

        // Add Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            // Handle exit button click
            JoinMenu.this.dispose();
        });

        mainPanel.add(exitButton);

        // Add main panel to the frame
        add(mainPanel);

        // Set the frame to be visible
        setVisible(true);
    }
}
