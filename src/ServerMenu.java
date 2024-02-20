import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Represents a simple server menu user interface.
 */
public class ServerMenu {

    private final JFrame frame;
    private final JLabel label1;
    private final JLabel label2;

    /**
     * Constructs a ServerMenu object.
     */
    public ServerMenu() {
        frame = new JFrame("Simple UI Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);

        label1 = new JLabel("Server name: ");
        label2 = new JLabel("Waiting for second player...");

        JPanel panel = new JPanel();
        panel.add(label1);
        panel.add(label2);

        frame.getContentPane().add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
    }


    /**
     * Updates the UI with the server name.
     *
     * @param name the server name to display
     */
    public void updateUI(String name) {
        label1.setText("Server name: " + name);
        label2.setText("Waiting for second player...");
    }

    /**
     * Disposes the frame.
     */
    public void disposeFrame() {
        frame.dispose();
    }
}
