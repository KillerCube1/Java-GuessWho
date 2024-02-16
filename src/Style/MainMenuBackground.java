package Style;

import javax.swing.*;
import java.awt.*;

public class MainMenuBackground extends JLabel {


    public MainMenuBackground(JFrame frame, String imagePath) {
        ImageIcon logoIcon = new ImageIcon(imagePath);
        Image image = logoIcon.getImage();
        Image newimg = image.getScaledInstance(320, 167, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(newimg);
        setBounds(0, 0, frame.getWidth(), frame.getHeight());
        setIcon(new ImageIcon(imagePath));
    }

}
