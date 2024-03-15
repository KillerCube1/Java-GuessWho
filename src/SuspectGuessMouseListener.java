import Style.ConfettiLoser;
import Style.ConfettiWinner;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SuspectGuessMouseListener extends MouseAdapter {

    private final Component cardLabel;
    private final int index;

    private static int wrongGuesses = 0;
    private static int guessesLeft = 3;

    private static JLabel guessCounter;

    private static boolean listenerEnabled = true;

    public SuspectGuessMouseListener(Component cardLabel, int index) {
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

    static void guessCounterMethod() {
        if (guessCounter == null) {
            guessCounter = new JLabel();
            guessCounter.setBackground(Color.WHITE);
            guessCounter.setForeground(Color.WHITE);
            guessCounter.setFont(new Font("Calibri", Font.BOLD, 25));
            GuessWhoGUI.getFrame().getContentPane().add(guessCounter);
        }

        int frameWidth = GuessWhoGUI.getFrame().getWidth();
        int frameHeight = GuessWhoGUI.getFrame().getHeight();

        int counterXPosition = (int) (frameWidth * 0.7291666667);
        int counterYPosition = (int) (frameHeight * 0.0561797753);

        guessCounter.setBounds(counterXPosition, counterYPosition, 192, 25);
        guessCounter.setText("Guesses: " + guessesLeft);
    }
//random comment for commit

    public static void guessSuspect(Suspect character) throws InterruptedException {
        if (listenerEnabled) {
            if (character == GuessWhoGame.getGuilty()) {
                System.out.println("CORRECT!");

                showResultFrame();
            } else {
                guessesLeft -= 1;
                wrongGuesses += 1;
                guessCounterMethod();

                if (wrongGuesses >= 3) {
                    showResultFrame();
                }
            }
        }
    }


    private static void showResultFrame() {
        if(wrongGuesses >= 3) {
            GuessWhoGUI.getFrame().dispose();
            SwingUtilities.invokeLater(() -> {
                MainMenu.muteMusic();
                JFrame frame = new JFrame("Loser Screen");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new ConfettiLoser());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        } else {
            GuessWhoGUI.getFrame().dispose();
            SwingUtilities.invokeLater(() -> {
                MainMenu.muteMusic();
                JFrame frame = new JFrame("Confetti Winner");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new ConfettiWinner());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        }

    }


    public static void setListenerEnabled(boolean enabled) {
        listenerEnabled = enabled;
    }
}
