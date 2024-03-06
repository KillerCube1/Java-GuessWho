package Style;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;

public class MainMenuButton extends JButton {

    private static final String FONT_PATH = "/Style/Roboto-Bold.ttf";

    private Color buttonColor = new Color(204, 0, 0);

    private final int xPos;
    private final int yPos;
    private int changeInX = 0;

    public MainMenuButton(String text,int x, int y, int xSize, int ySize) {
        super(text);

        // Load Roboto font
        Font robotoFont = null;
        robotoFont = Resource.getFontResource(FONT_PATH, 21f);

        xPos = x;
        yPos = y;

        setOpaque(false);
        setBackground(Color.white);
        setForeground(Color.white);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(robotoFont);
        setPreferredSize(new Dimension(165, 45));
        setMaximumSize(new Dimension(getPreferredSize().width, getPreferredSize().height));

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonColor = new Color(0, 135, 255);
                changeInX = 10;
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonColor = new Color(204, 0, 0);
                changeInX = 0;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill the button background
        if (getModel().isArmed()) {
            g2.setColor(new Color(40, 175, 255));
        } else {
            g2.setColor(buttonColor);
            setBounds(xPos + changeInX, yPos, 165, 45);
        }
        g2.fillRoundRect(1, 1, getWidth()-1, getHeight()-1, 39, 39);

        // Draw the white outline
        g2.setColor(Color.white);
        g2.setStroke(new java.awt.BasicStroke(5)); // Set thickness of the outline
        g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 35, 35);

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}
