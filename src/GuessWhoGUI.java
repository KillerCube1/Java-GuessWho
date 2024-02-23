import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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


public class GuessWhoGUI extends JFrame {//GuessWhoGUI

    private final JFrame frame;
    private int wrongGuesses = 0;
    private int guessesLeft = 3;
    private JLabel guessCounter;
    private final JLabel[] susGrid = new JLabel[24];



    /**
     * Constructs a GuessWhoGUI object and initializes the graphical user interface.
     */
    public GuessWhoGUI() {//constructor
        // Set up the game board
        // Initialize components such as buttons, suspect grid, and guess counter
        // App Set Up
        frame = new JFrame("Guess Who");
        try {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setSize(1920, 870);
            Color bgd = new Color(40, 40, 40);
            frame.getContentPane().setBackground(bgd);
            GuessWhoGame.setTheDeck(new Deck());
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
                new CheckButton("WhiteHair?", "hairwhite", new Color(0x95CC99))
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

            winnerFrame();

        } else {
            guessesLeft -= 1;
            wrongGuesses += 1;
            guessCounterMethod();

            if (wrongGuesses >= 3) {
                loserFrame();

            }
        }
    }


    /**
     * Retrieves the main frame of the game GUI.
     *
     * @return The main JFrame object.
     */
    public JFrame getFrame() {//getFrame
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
            guessCounter.setBounds(1400, 50, 192, 20);
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
        cardTitle.setBounds(1400, 325, 192, 20);
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
        System.out.println(GuessWhoGame.getTheDeck().getSuspect(x).getAttribute("name"));
        GuessWhoGame.setGuilty(GuessWhoGame.getTheDeck().getSuspect(x));

        int randomPlayerCharacter = (int) (Math.random() * GuessWhoGame.getTheDeck().getTotalCards());
        GuessWhoGame.setPlayerCharacter(GuessWhoGame.getTheDeck().getSuspect(randomPlayerCharacter));
    }

    public void winnerFrame(){
        JFrame winnerFrame = new JFrame("Guess Who");
        winnerFrame.setSize(300, 300);
        winnerFrame.setLocationRelativeTo(null);

        JLabel winnerLabel = new JLabel("Game Over you win");
        winnerLabel.setHorizontalAlignment(JLabel.CENTER);
        winnerFrame.add(winnerLabel);
        winnerFrame.setVisible(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                winnerFrame.setVisible(false);
                // Exit the game after hiding the frame
                System.exit(0);
            }
        }, 3000);
    }

    public void loserFrame(){
        JFrame loserFrame = new JFrame("Guess Who");
        loserFrame.setSize(300, 300);
        loserFrame.setLocationRelativeTo(null);

        JLabel loserLabel = new JLabel("Game Over you lose!");
        loserLabel.setHorizontalAlignment(JLabel.CENTER);
        loserFrame.add(loserLabel);
        loserFrame.setVisible(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                loserFrame.setVisible(false);
                System.exit(0);
            }
        }, 3000);
    }




}//GuessWhoGUI