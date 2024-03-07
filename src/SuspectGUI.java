import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


/**
 * The SuspectGUI class provides functionality to display detailed information about a selected suspect
 * in the Guess Who game. It creates a window to show the suspect's attributes and allows the player to make a guess.
 * It implements the ActionListener interface to handle button actions.
 */
public class SuspectGUI implements ActionListener {

    // Main Window
    GuessWhoGUI mainGUI = GuessWhoGame.getWindow();
    JFrame frame = GuessWhoGUI.getFrame();

    // Suspect Window
    private JFrame suspectBox;
    JButton cancel;
    JButton guess;

    Suspect targetSuspect;



    /**
     * Displays the SuspectGUI window with detailed information about the selected suspect.
     *
     * @param label The label representing the selected suspect in the main game window.
     */
    public void ShowSuspectGUI(JLabel label) {

        // Get The Suspect From The Deck
        targetSuspect = null;
        for (Suspect i : mainGUI.getDeck().getSusDeck()) {
            if (Objects.equals(i.getName(), label.getText())) {
                targetSuspect = i;
                break;
            }
        }

        // Create Suspect Window
        if (suspectBox != null) suspectBox.dispose();
        suspectBox = new JFrame("Selected Suspect");
        suspectBox.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        suspectBox.setLayout(null);
        suspectBox.setSize(232, 600);
        suspectBox.getContentPane().setBackground(new Color(200, 150, 170));
        suspectBox.setLocation((int) frame.getLocation().getX() + label.getX() - 60, (int) frame.getLocation().getY() + label.getY() - 15);
        suspectBox.setVisible(true);
        suspectBox.setResizable(false);

        JLabel suspectLabel = new JLabel("");
        suspectLabel.setBounds(60, 15, 96, 140);
        suspectBox.getContentPane().add(suspectLabel);
        suspectLabel.setIcon(label.getIcon());

        JLabel suspectInfoTitle = new JLabel("Suspect Information:");
        suspectInfoTitle.setFont(new Font("Calibri", Font.BOLD, 20));
        suspectInfoTitle.setBounds(20, 155, 232, 20);
        suspectBox.getContentPane().add(suspectInfoTitle);

        String[] suspectDesc = {
                "Name - " + targetSuspect.getName(),
                "Gender - " + targetSuspect.getGender(),
                "Eye Color - " + targetSuspect.getEyeColor(),
                "Hair Color - " + targetSuspect.getHairColor(),
                "Is Bald: " + false,
                "Has Beard: " + (targetSuspect.hasBeard() ? "yes" : "no"),
                "Has Glasses: " + (targetSuspect.hasGlasses() ? "yes" : "no"),
                "Has Hat: " + (targetSuspect.hasHat() ? "yes" : "no"),
                "Has Moustache: " + (targetSuspect.hasMoustache() ? "yes" : "no"),
                "Has Rosy Cheeks: " + (targetSuspect.hasRosyCheeks() ? "yes" : "no")
        };

        JLabel suspectInfo = new JLabel("<html>" + String.join("<br/>", suspectDesc) + "</html>");
        suspectInfo.setVerticalAlignment(SwingConstants.TOP);
        suspectInfo.setFont(new Font("Calibri", Font.BOLD, 16));
        suspectInfo.setBounds(10, 175, 232, 400);
        suspectBox.getContentPane().add(suspectInfo);

        guess = new JButton();
        guess.setBounds(30, 460, 150, 30);
        guess.addActionListener(this);
        guess.setText("Guess");
        guess.setFocusable(false);

        cancel = new JButton();
        cancel.setBounds(30, 490, 150, 30);
        cancel.addActionListener(this);
        cancel.setText("Cancel");
        cancel.setFocusable(false);

        suspectBox.add(guess);
        suspectBox.add(cancel);

    }


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
                mainGUI.guessSuspect(targetSuspect);
            } catch (InterruptedException ex) {
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



    /**
     * Disposes the SuspectGUI window.
     */
    public void disposeFrame() {
        suspectBox.dispose();
    }

}