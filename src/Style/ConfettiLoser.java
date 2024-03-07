package Style;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ConfettiLoser extends JPanel {

    private int messageX = 400;
    private int messageY = 300;
    private int messageVelocityX = 2;
    private int messageVelocityY = 2;
    private final ArrayList<BloodDrop> bloodDrops = new ArrayList<>();
    private Timer timer;
    private int redComponent = 0;
    private Clip backgroundClip;


    public ConfettiLoser() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 600));
        playBackgroundAudio();
    }

    private void playBackgroundAudio() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/music/all_things_come_to_an_end-64302.wav"));
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioInputStream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Check if blood drops need to be generated
        if (bloodDrops.isEmpty()) {
            generateBloodDrops();
            startAnimation();
        }

        // Draw background with gradually increasing red component
        Color bgColor = new Color(redComponent, 0, 0);
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw blood drops
        g2d.setColor(Color.RED);
        for (BloodDrop drop : bloodDrops) {
            int diameter = drop.radius * 2;
            g2d.fillOval(drop.x - drop.radius, drop.y - drop.radius, diameter, diameter);
        }

        // Draw "You Lost" message
        g2d.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 40);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int x = messageX;
        int y = messageY;
        g2d.setFont(font);
        String message = "You Lost";
        g2d.drawString(message, x, y);
    }

    private void generateBloodDrops() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            int speed = 1 + random.nextInt(4);
            bloodDrops.add(new BloodDrop(x, y, speed));
        }
    }

    private void startAnimation() {
        // Start animation
        timer = new Timer(30, e -> {
            moveMessage();
            moveBloodDrops();
            updateBackgroundColor();
            repaint();
        });
        timer.start();
    }

    private void moveMessage() {
        messageX += messageVelocityX;
        messageY += messageVelocityY;

        if (messageX <= 0 || messageX + 150 >= getWidth()) {
            messageVelocityX = -messageVelocityX;
        }

        if (messageY <= 0 || messageY + 40 >= getHeight()) {
            messageVelocityY = -messageVelocityY;
        }
    }

    private void moveBloodDrops() {
        for (BloodDrop drop : bloodDrops) {
            drop.y += drop.speed;
            if (drop.y > getHeight()) {
                // If a blood drop reaches the bottom, reset it to the top with random x position
                drop.y = 0;
                drop.x = new Random().nextInt(getWidth());
                drop.radius = 1; // Reset radius
            }
        }
    }

    private void updateBackgroundColor() {
        // Gradually increase the red component of the background color
        if (redComponent < 255) {
            redComponent += 1; // Increase red component by 1
        } else {
            // If the background color is fully red, stop the timer and close the frame after 3 seconds
            timer.stop();
            backgroundClip.stop(); // Stop background audio
            Timer closeTimer = new Timer(3000, e -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.dispose();
            });
            closeTimer.setRepeats(false);
            closeTimer.start();
        }
    }

    private static class BloodDrop {
        int x, y;
        int speed;
        int radius = 1; // Initial radius

        BloodDrop(int x, int y, int speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }
    }

}
