package Style;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ConfettiWinner extends JPanel {

    private final ArrayList<Confetti> confettiList = new ArrayList<>();

    private int messageX = 400;
    private int messageY = 300;
    private int messageVelocityX = 2;
    private int messageVelocityY = 2;

    public ConfettiWinner() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 600));
        playBackgroundAudio();

        // Generate confetti
        generateConfetti();

        // Start animation
        Timer timer = new Timer(30, e -> {
            moveConfetti();
            moveMessage();
            repaint();
        });
        timer.start();

        // Schedule frame close after 7 seconds
        Timer closeTimer = new Timer(7000, e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ConfettiWinner.this);
            frame.dispose();
        });
        closeTimer.setRepeats(false); // Ensure the timer only runs once
        closeTimer.start();
    }

    private void playBackgroundAudio() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/music/tadaa-47995.wav"));
            Clip backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioInputStream);
            backgroundClip.start(); // Start playing the audio
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void generateConfetti() {
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            float x = random.nextFloat() * getWidth();
            float y = random.nextFloat() * getHeight();
            float speedX = -1f + random.nextFloat() * 2f;
            float speedY = 1f + random.nextFloat() * 2f;
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            confettiList.add(new Confetti(x, y, speedX, speedY, color));
        }
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

    private void moveConfetti() {
        for (Confetti confetti : confettiList) {
            confetti.x += confetti.speedX;
            confetti.y += confetti.speedY;

            // Wrap around screen
            if (confetti.x > getWidth())
                confetti.x = 0;
            else if (confetti.x < 0)
                confetti.x = getWidth();

            if (confetti.y > getHeight())
                confetti.y = 0;
            else if (confetti.y < 0)
                confetti.y = getHeight();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Draw confetti
        for (Confetti confetti : confettiList) {
            g2d.setColor(confetti.color);
            g2d.fill(new Ellipse2D.Float(confetti.x, confetti.y, 10, 10));
        }

        // Draw "Winner" message
        g2d.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 40);
        FontMetrics metrics = g2d.getFontMetrics(font);
        String message = "Winner";
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.setFont(font);
        g2d.drawString(message, x, y);
    }

    private static class Confetti {
        float x, y;
        float speedX, speedY;
        Color color;

        Confetti(float x, float y, float speedX, float speedY, Color color) {
            this.x = x;
            this.y = y;
            this.speedX = speedX;
            this.speedY = speedY;
            this.color = color;
        }
    }

}
