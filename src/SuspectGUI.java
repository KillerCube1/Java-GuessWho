
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The SuspectGUI class provides functionality to display detailed information about a selected suspect
 * in the Guess Who game. It creates a window to show the suspect's attributes and allows the player to make a guess.
 * It implements the ActionListener interface to handle button actions.
 */
public class SuspectGUI implements ActionListener {

    // Suspect Window
    private JFrame suspectBox;
    JButton cancel;
    JButton guess;

    Suspect targetSuspect;


    /**
     * Handles button actions performed in the SuspectGUI window.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            suspectBox.dispose();
        } else if (e.getSource() == guess) {
            suspectBox.dispose();
            try {
                SuspectGuessMouseListener.guessSuspect(targetSuspect);
            } catch (InterruptedException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }



    /**
     * Retrieves the JFrame of the SuspectGUI window.
     *
     * @return The SuspectGUI JFrame.
     */
    public JFrame getFrame() {
        return suspectBox;
    }
}
