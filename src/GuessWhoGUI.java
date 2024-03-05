import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.*;

import ClassExtensions.CheckButton;

/**
 * GuessWhoGUI class represents the graphical user interface for the Guess Who game.
 * It sets up the game board, displays suspect cards, and handles user interactions.
 * This class extends JFrame and includes features such as attribute-checking buttons,
 * a grid of suspect cards, and a guess counter.
 * @author Rylan, Damien
 * @version 1.0
 */


//GuessWhoGUI
public class GuessWhoGUI extends JFrame {

    private static JFrame frame = null;
    private int wrongGuesses = 0;
    private int guessesLeft = 3;
    private JLabel guessCounter;
    private static final JLabel[] susGrid = new JLabel[24];



    /**
     * GuessWhoGUI represents the graphical user interface for the Guess Who game.
     * This class extends JFrame and provides functionality to set up the game board,
     * initialize components such as buttons and suspect grid, and perform application setup.
     * It allows players to interact with the game through mouse clicks and button presses.
     */

    public GuessWhoGUI(boolean turn) {

        frame = new JFrame("Guess Who");
        try {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setSize(1920, 890);
            Color bgd = new Color(40, 40, 40);
            frame.getContentPane().setBackground(bgd);
        } catch (Exception ignored) {
        }

        int FrameButtonWidth = 200;
        int FrameButtonHeight = 50;
        int xFramePosition = 1676;
        int yFramePosition = 20;

        CheckButton[] buttonList = {
                new CheckButton("Male?", "gendmale", new Color(0xFF7F6E)),
                new CheckButton("Female?", "gendfemale", new Color(0x7A67E2)),
                new CheckButton("Hat?", "hat", new Color(0x00CCCC)),
                new CheckButton("Bald?", "bald", new Color(0x6BB536)),
                new CheckButton("Beard?", "beard", new Color(0x4E93CC)),
                new CheckButton("Moustache?", "moustache", new Color(0xFFE7B5)),
                new CheckButton("RosyCheeks?", "rosyCheeks", new Color(0x9D37E1)),
                new CheckButton("Glasses?", "glasses", new Color(0x7EFF8B)),
                new CheckButton("BlackHair?", "hairblack", new Color(0xFF824D)),
                new CheckButton("RedHair?", "hairred", new Color(0xE65A79)),
                new CheckButton("BrownHair?", "hairbrown", new Color(0xFFE53A)),
                new CheckButton("BlondeHair?", "hairblonde", new Color(0x9FDFFF)),
                new CheckButton("WhiteHair?", "hairwhite", new Color(0x95CC99)),
                new CheckButton("Blue Eyes?", "eyesblue", new Color(0x789A63)),
                new CheckButton("Brown Eyes?", "eyesbrown", new Color(0x12B2BF)),
        };

        for(int i = 0; i < 12; i++) {
            yFramePosition += 55;

            for(int j=0;j<buttonList.length;j++) {
                buttonList[j].setBounds(xFramePosition - (i * -2), yFramePosition - (i * (60 - j * 5)), FrameButtonWidth, FrameButtonHeight);
                frame.getContentPane().add(buttonList[j]);
            }

        }
        createSuspectGrid();

        for(CheckButton button : buttonList) {
            button.addActionListener(new CheckAction());
            button.setFocusable(false);
            button.setVisible(true);
        }


        assignGuiltySuspect();
        playerCardFrameStuff();
        guessCounterMethod();

        // Freeze-frame if not turn
        if (!turn) freezeFrame();

    }//constructor


    /**
     * Handles the process of guessing a suspect.
     * Checks if the guessed suspect is correct and updates the game state accordingly.
     *
     * @param character The suspect being guessed.
     * @throws InterruptedException If the thread is interrupted.
     */

    public void guessSuspect(Suspect character) throws InterruptedException {
        // Process the guess and update game state
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


    /**
     * Retrieves the main frame of the game GUI.
     *
     * @return The main JFrame object.
     */
    public static JFrame getFrame() {//getFrame
        return frame;
    }//getFrame


    /**
     * Retrieves the game deck containing suspect cards.
     *
     * @return The Deck object representing the game deck.
     */
    public Deck getDeck() {
        return GuessWhoGame.getTheDeck();
    }


    /**
     * Method to create the grid of suspect cards on the game board.
     * Displays the suspect cards and sets up mouse listeners for guessing.
     */
    public void createSuspectGrid() {
        int cardLabelWidth = 180;
        int cardLabelHeight = 200;
        int cardsPerRow = 6;
        int row;
        int col;

        for (int i = 0; i < GuessWhoGame.getTheDeck().getTotalCards(); i++) {
            row = i / cardsPerRow;
            col = i % cardsPerRow;

            int xPosition = 20 + col * (cardLabelWidth);
            int yPosition = row * (cardLabelHeight);

            JLabel cardLabel = new JLabel(new ImageIcon(GuessWhoGame.getTheDeck().getSuspect(i).getCard().getFrontImage()));
            cardLabel.setBounds(xPosition, yPosition, cardLabelWidth, cardLabelHeight);
            cardLabel.setIcon(new ImageIcon(GuessWhoGame.getTheDeck().getSuspect(i).getCard().getFrontImage()));


            final int index = i;
            cardLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Clicked on suspect: " + GuessWhoGame.getTheDeck().getSuspect(index).getAttribute("name"));
                    try {
                        guessSuspect(GuessWhoGame.getTheDeck().getSuspect(index));
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    cardLabel.removeMouseListener(this);
                }
            });

            frame.getContentPane().add(cardLabel);
            susGrid[i] = cardLabel;
        }
    }

    /**
     * Method to flip a suspect card on the game board.
     *
     * @param index The index of the suspect card to flip.
     */
    public void flipCard(int index) {
        JLabel cardLabel = susGrid[index];
        cardLabel.setIcon(new ImageIcon(GuessWhoGame.getTheDeck().getSuspect(index).getCard().getBackImage()));
    }


    /**
     * Method to update and display the guess counter on the game board.
     */
    private void guessCounterMethod() {

        if (guessCounter == null) {
            guessCounter = new JLabel();
            guessCounter.setBackground(Color.WHITE);
            guessCounter.setForeground(Color.WHITE);
            guessCounter.setFont(new Font("Calibri", Font.BOLD, 25));
            guessCounter.setBounds(1400, 50, 192, 25);
            frame.getContentPane().add(guessCounter);
        }

        guessCounter.setText("Guesses: " + guessesLeft);
    }

    private void playerCardFrameStuff(){
        // Player Suspect Card Image
        ImageIcon cardIcon = new ImageIcon(GuessWhoGame.getPlayerCharacter().getCard().getFrontImage());
        Image image = cardIcon.getImage();
        Image newimg = image.getScaledInstance(153, 224,  Image.SCALE_SMOOTH);
        cardIcon = new ImageIcon(newimg);

        // Player Suspect Title Display
        JLabel cardTitle = new JLabel("Your Character");
        cardTitle.setBackground(Color.WHITE);
        cardTitle.setForeground(Color.WHITE);
        cardTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        cardTitle.setBounds(1400, 325, 192, 25);
        frame.getContentPane().add(cardTitle);

        // Player Suspect Card Display
        JLabel chosenCard = new JLabel("");
        chosenCard.setBounds(1400, 50, 192, 280);
        frame.getContentPane().add(chosenCard);
        chosenCard.setIcon(cardIcon);
    }

    private void assignGuiltySuspect(){
        // Assign Random Player Suspect (guilty)
        int x = (int) (Math.random() * GuessWhoGame.getTheDeck().getTotalCards());
        GuessWhoGame.setGuilty(GuessWhoGame.getTheDeck().getSuspect(x));

        int randomPlayerCharacter = (int) (Math.random() * GuessWhoGame.getTheDeck().getTotalCards());
        GuessWhoGame.setPlayerCharacter(GuessWhoGame.getTheDeck().getSuspect(randomPlayerCharacter));
    }

    private static void showPlayersTurnNotification() {
        // Create a semi-transparent panel to darken the background
        JPanel darkenPanel = new JPanel();
        darkenPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        darkenPanel.setBackground(new Color(0, 0, 0, 100));
        darkenPanel.setLayout(new BorderLayout());

        // Create a label for the notification text
        JLabel notificationLabel = new JLabel("<html><center><font size='1200' color='#FFFFFF'>Opponent's Turn</font></center></html>");
        notificationLabel.setBounds(1400, 150, 192, 25);

        // Customize font and alignment
        Font labelFont = notificationLabel.getFont();
        notificationLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 24)); // Example: Increase font size and set it to bold


        darkenPanel.add(notificationLabel, BorderLayout.CENTER);

        frame.add(darkenPanel);

        // Close the dialog after 3 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                darkenPanel.setVisible(false);
            }
        }, 3000); // Adjust the delay as needed
    }

    private static void showResultFrame(String message) {
        JFrame resultFrame = new JFrame("Guess Who");
        resultFrame.setSize(200, 100);
        resultFrame.setLocationRelativeTo(null);

        JLabel resultLabel = new JLabel(message);
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        resultFrame.add(resultLabel);
        resultFrame.setVisible(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                resultFrame.setVisible(false);
                GuessWhoGUI.getFrame().dispose();
                MainMenu.mainMenu();
            }
        }, 3000);
    }




    public static void freezeFrame() {
        showPlayersTurnNotification();
        for (int i = 0; i < susGrid.length; i++) {
            JLabel label = susGrid[i];
            for (MouseListener listener : label.getMouseListeners()) {
                label.removeMouseListener(listener);
            }
            label.addMouseListener(new LabelListener(label, i));
        }
    }


    public static void unFreezeFrame() {
        for (int i = 0; i < susGrid.length; i++) {
            JLabel label = susGrid[i];
            for (MouseListener listener : label.getMouseListeners()) {
                label.removeMouseListener(listener);
            }
            label.addMouseListener(new LabelListener(label, i));
        }
    }

    public static void removeLabels(){

    }


}//GuessWhoGUI