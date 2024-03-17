
import javax.swing.*;

public class LoadingScreen {

    private static JLabel status;
    private static JFrame frame;

    public static void start() {
        frame = new JFrame("Guess Who");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 150);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        status = new JLabel("<html><div style='text-align: center;'>Loading...<br>[ waiting ]</div></html>");
        status.setHorizontalAlignment(SwingConstants.CENTER);

        frame.add(status);

        frame.setVisible(true);
    }

    public static void update(String text) {
        status.setText("<html><div style='text-align: center;'>Loading...<br>[ "+ text +" ]</div></html>");
    }

    public static void stop() {
        frame.dispose();
    }
}
