import java.awt.EventQueue;


/**
 * The GuessWhoGame class represents the main game logic for Guess Who.
 * It manages the game window, the suspect GUI window, the game deck, and the suspects.
 */
public class GuessWhoGame {
    private static GuessWhoGUI window;
    private static SuspectGUI suspectWindow;
    private static Deck theDeck = new Deck();
    private static Suspect guilty;
    private static Suspect playerCharacter;

    private static String gameState;

    /**
     * Constructs a GuessWhoGame object and initializes the game components.
     */
    public GuessWhoGame(String givenGameState) {
        gameState = givenGameState;
        switch (gameState) {
            case "Single": initSingleplayerGame(); break;
            case "Host"  : startHostGame();        break;
            case "Client": startClientGame();      break;
        }
    }


    private void startClientGame() {
        EventQueue.invokeLater(() -> {
            try {
                window = new GuessWhoGUI(!Client.getTurn());
                window.getFrame().setVisible(true);
                suspectWindow = new SuspectGUI();
            } catch (Exception ignored) {}
        });
    }


    private void startHostGame() {
        EventQueue.invokeLater(() -> {
            try {
                window = new GuessWhoGUI(Host.getTurn());
                window.getFrame().setVisible(true);
                suspectWindow = new SuspectGUI();
            } catch (Exception ignored) {}
        });
    }


    private void initSingleplayerGame() {
        EventQueue.invokeLater(() -> {
            try {
                window = new GuessWhoGUI(true);
                window.getFrame().setVisible(true);
                suspectWindow = new SuspectGUI();
                GuessWhoGUI.freezeFrame();

            } catch (Exception ignored) {}
        });
    }


    /**
     * Sets the game deck.
     *
     * @param theDeck The game deck to set.
     */
    public static void setTheDeck(Deck theDeck) {
        GuessWhoGame.theDeck = theDeck;
    }


    /**
     * Sets the guilty suspect.
     *
     * @param guilty The guilty suspect to set.
     */
    public static void setGuilty(Suspect guilty) {
        GuessWhoGame.guilty = guilty;
    }


    /**
     * Sets the player's character.
     *
     * @param playerCharacter The player's character to set.
     */
    public static void setPlayerCharacter(Suspect playerCharacter) {
        GuessWhoGame.playerCharacter = playerCharacter;
    }


    /**
     * Retrieves the game window.
     *
     * @return The game window.
     */
    public static GuessWhoGUI getWindow() {
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
        switch (gameState) {
            case "Single": return guilty;
            case "Host"  : return Host.getOpponentSuspect();
            case "Client": return Client.getOpponentSuspect();
            default: return null;
        }
    }

    /**
     * Retrieves the player's character.
     *
     * @return The player's character.
     */
    public static Suspect getPlayerCharacter() {
        return playerCharacter;
    }

    public String getGameState() {
        return gameState;
    }

}