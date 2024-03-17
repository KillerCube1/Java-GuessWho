
import java.awt.*;
import java.awt.event.*;

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

    private static final JLabel[] susGrid = new JLabel[24];

    private static CheckButton[] buttonList;

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
            frame.setSize(1600, 900);
            frame.setResizable(false);
            Color bgd = new Color(40, 40, 40);
            frame.getContentPane().setBackground(bgd);
        } catch (Exception ignored) {
        }



        double buttonWidthPercentage = 0.1041666667; // 200 / 1920
        double buttonHeightPercentage = 0.0561797753; // 50 / 890

        //random comment for commit

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        buttonList = new CheckButton[]{
                new CheckButton("Male?", "gendmale", new Color(0xFF4343)),
                new CheckButton("Female?", "gendfemale", new Color(0xFF8100)),
                new CheckButton("Hat?", "hat", new Color(0xFFD300)),
                new CheckButton("Bald?", "bald", new Color(0x61FF13)),
                new CheckButton("Beard?", "beard", new Color(0x0FFF93)),
                new CheckButton("Moustache?", "moustache", new Color(0x00D8FF)),
                new CheckButton("RosyCheeks?", "rosyCheeks", new Color(0x0095FF)),
                new CheckButton("Glasses?", "glasses", new Color(0x0066FF)),
                new CheckButton("BlackHair?", "hairblack", new Color(0x3000FF)),
                new CheckButton("RedHair?", "hairred", new Color(0x7C58FF)),
                new CheckButton("BrownHair?", "hairbrown", new Color(0xD41CFF)),
                new CheckButton("BlondeHair?", "hairblonde", new Color(0xFF049B)),
                new CheckButton("WhiteHair?", "hairwhite", new Color(0xFF65B2)),
                new CheckButton("Blue Eyes?", "eyesblue", new Color(0xFF9393)),
                new CheckButton("Brown Eyes?", "eyesbrown", new Color(0xFFB7B7)),
        };

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < buttonList.length; j++) {
                int xPosition = (int) (frameWidth * (0.875 - (i * -0.00104))); // Calculating x position
                int yPosition = (int) (frameHeight * (0.0225 + (j * 0.0557))); // Calculating y position

                int buttonWidth = (int) (frameWidth * buttonWidthPercentage);
                int buttonHeight = (int) (frameHeight * buttonHeightPercentage);

                buttonList[j].setBounds(xPosition, yPosition, buttonWidth, buttonHeight);
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
        SuspectGuessMouseListener.guessCounterMethod();

        // Freeze-frame if not turn
        if (!turn){
            freezeFrame();

        }
        else{
            unFreezeFrame();
        }

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                playerCardFrameStuff();
            }
        });


    }//constructor


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
        int cardsPerRow = 6;
        int row;
        int col;

        double cardWidthPercentage = 0.09375; // 180 / 1920
        double cardHeightPercentage = 0.2247191011; // 200 / 890

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        for (int i = 0; i < GuessWhoGame.getTheDeck().getTotalCards(); i++) {
            row = i / cardsPerRow;
            col = i % cardsPerRow;

            int cardWidth = (int) (frameWidth * cardWidthPercentage);
            int cardHeight = (int) (frameHeight * cardHeightPercentage);

            int xPosition = 20 + col * cardWidth;
            int yPosition = row * cardHeight;

            JLabel cardLabel = new JLabel(new ImageIcon(GuessWhoGame.getTheDeck().getSuspect(i).getCard().getFrontImage()));
            cardLabel.setBounds(xPosition, yPosition, cardWidth, cardHeight);
            cardLabel.setIcon(new ImageIcon(GuessWhoGame.getTheDeck().getSuspect(i).getCard().getFrontImage()));

            SuspectGuessMouseListener labelListener = new SuspectGuessMouseListener(cardLabel, i);
            cardLabel.addMouseListener(labelListener);

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



    private void playerCardFrameStuff() {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int titleXPosition = (int) (frameWidth * 0.7291666667);
        int titleYPosition = (int) (frameHeight * 0.3651685393);

        int cardXPosition = (int) (frameWidth * 0.7291666667);
        int cardYPosition = (int) (frameHeight * 0.0561797753);

        // Player Suspect Title Display
        JLabel cardTitle = new JLabel("Your Character");
        cardTitle.setBackground(Color.WHITE);
        cardTitle.setForeground(Color.WHITE);
        cardTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        cardTitle.setBounds(titleXPosition, titleYPosition, 192, 25);
        frame.getContentPane().add(cardTitle);

        // Player Suspect Card Display
        ImageIcon cardIcon = new ImageIcon(GuessWhoGame.getPlayerCharacter().getCard().getFrontImage());
        Image image = cardIcon.getImage();
        Image newimg = image.getScaledInstance(153, 224,  Image.SCALE_SMOOTH);
        cardIcon = new ImageIcon(newimg);

        JLabel chosenCard = new JLabel("");
        chosenCard.setBounds(cardXPosition, cardYPosition, 192, 280);
        frame.getContentPane().add(chosenCard);
        chosenCard.setIcon(cardIcon);
    }


    private void assignGuiltySuspect(){
        // Assign Random Player Suspect (guilty)
        int x = (int) (Math.random() * GuessWhoGame.getTheDeck().getTotalCards());
        GuessWhoGame.setGuilty(GuessWhoGame.getTheDeck().getSuspect(x));

        int randomPlayerCharacter = (int) (Math.random() * GuessWhoGame.getTheDeck().getTotalCards());
        GuessWhoGame.setPlayerCharacter(GuessWhoGame.getTheDeck().getSuspect(randomPlayerCharacter));

        System.out.println(GuessWhoGame.getGuilty().getAttribute("name"));
    }

    static void showPlayersTurnNotification() {
        JLabel notificationLabel = new JLabel("<html><div align='center'><font size='12' color='#FFFFFF'>Opponent's Turn</font></div></html>");
        frame.add(notificationLabel);

        Font labelFont = notificationLabel.getFont();
        notificationLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 24));

    }



    public static void freezeFrame() {
        showPlayersTurnNotification();
        SuspectGuessMouseListener.setListenerEnabled(false);
        for(CheckButton button : buttonList) {
            button.setEnabled(false);
        }
    }

    public static void unFreezeFrame() {
        SuspectGuessMouseListener.setListenerEnabled(true);
        for(CheckButton button : buttonList) {
            button.setEnabled(true);
        }
    }


}
