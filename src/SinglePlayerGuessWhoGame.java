import java.awt.EventQueue;


/**
 * The GuessWhoGame class represents the main game logic for Guess Who.
 * It manages the game window, the suspect GUI window, the game deck, and the suspects.
 */
public class SinglePlayerGuessWhoGame {
    private static SinglePlayerGuessWhoGUI window;
    private static SuspectGUI suspectWindow;
    private static Deck theDeck;
    private static Suspect guilty;
    private static Suspect playerCharacter;


    /**
     * Constructs a GuessWhoGame object and initializes the game components.
     */
    public SinglePlayerGuessWhoGame() {
        EventQueue.invokeLater(() -> {
            try {
                window = new SinglePlayerGuessWhoGUI();
                window.getFrame().setVisible(true);
                suspectWindow = new SuspectGUI();
            } catch (Exception ignored) {
            }
        });
    }


    /**
     * Sets the game deck.
     *
     * @param theDeck The game deck to set.
     */
    public static void setTheDeck(Deck theDeck) {
        SinglePlayerGuessWhoGame.theDeck = theDeck;
    }


    /**
     * Sets the guilty suspect.
     *
     * @param guilty The guilty suspect to set.
     */
    public static void setGuilty(Suspect guilty) {
        SinglePlayerGuessWhoGame.guilty = guilty;
    }


    /**
     * Sets the player's character.
     *
     * @param playerCharacter The player's character to set.
     */
    public static void setPlayerCharacter(Suspect playerCharacter) {
        SinglePlayerGuessWhoGame.playerCharacter = playerCharacter;
    }


    /**
     * Retrieves the game window.
     *
     * @return The game window.
     */
    public static SinglePlayerGuessWhoGUI getWindow() {
        return window;
    }


    /**
     * Retrieves the suspect GUI window.
     *
     * @return The suspect GUI window.
     */
    public static SuspectGUI getSuspectWindow() {
        return suspectWindow;
    }


    /**
     * Retrieves the game deck.
     *
     * @return The game deck.
     */
    public static Deck getTheDeck() {
        return theDeck;
    }


    /**
     * Retrieves the guilty suspect.
     *
     * @return The guilty suspect.
     */
    public static Suspect getGuilty() {
        return guilty;
    }


    /**
     * Retrieves the player's character.
     *
     * @return The player's character.
     */
    public static Suspect getPlayerCharacter() {
        return playerCharacter;
    }


}