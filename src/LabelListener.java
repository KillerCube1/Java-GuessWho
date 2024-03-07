import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LabelListener extends MouseAdapter {

    private final Component cardLabel;
    private final int index;

    private static int wrongGuesses = 0;
    private static int guessesLeft = 3;

    private static JLabel guessCounter;

    private static boolean listenerEnabled = true;

    public LabelListener(Component cardLabel, int index) {
        this.cardLabel = cardLabel;
        this.index = index;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (listenerEnabled) {
            System.out.println("Clicked on suspect: " + GuessWhoGame.getTheDeck().getSuspect(index).getAttribute("name"));
            try {
                guessSuspect(GuessWhoGame.getTheDeck().getSuspect(index));
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            cardLabel.removeMouseListener(this);
        }
    }

    public static void guessSuspect(Suspect character) throws InterruptedException {
        if (listenerEnabled) {
            if (character == GuessWhoGame.getGuilty()) {
                System.out.println("CORRECT!");

                showResultFrame("CONGRATS YOU WIN!");
            } else {
                guessesLeft -= 1;
                wrongGuesses += 1;
                guessCounterMethod();

                if (wrongGuesses >= 3) {
                    showResultFrame("YOU LOSE WHAT A BOT!");
                }
            }
        }
    }

    static void guessCounterMethod() {
        if (guessCounter == null) {
            guessCounter = new JLabel();
            guessCounter.setBackground(Color.WHITE);
            guessCounter.setForeground(Color.WHITE);
            guessCounter.setFont(new Font("Calibri", Font.BOLD, 25));
            guessCounter.setBounds(1400, 50, 192, 25);
            GuessWhoGUI.getFrame().getContentPane().add(guessCounter);
        }

        guessCounter.setText("Guesses: " + guessesLeft);
    }

    private static void showResultFrame(String message) {
        JFrame resultFrame = new JFrame("Guess Who");
        resultFrame.setSize(1920, 1080);
        resultFrame.setLocationRelativeTo(null);
        try {
            resultFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/Images/colorful-confetti-background-with-text-space_1017-32374.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel resultLabel = new JLabel(message);
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        resultFrame.add(resultLabel);
        resultFrame.setVisible(true);

        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                resultFrame.setVisible(false);
                GuessWhoGUI.getFrame().dispose();
                MainMenu.mainMenu();
            }
        }, 3000);
    }

    public static void setListenerEnabled(boolean enabled) {
        listenerEnabled = enabled;
    }
}
