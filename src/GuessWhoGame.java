import java.awt.EventQueue;
import java.io.IOException;
import java.util.List;

/**
 * The GuessWhoGame class represents the main game logic for Guess Who.
 * It manages the game window, the suspect GUI window, the game deck, and the suspects.
 */
public class GuessWhoGame {
    private static GuessWhoGUI window;
    private static SuspectGUI suspectWindow;
    private static Deck theDeck;
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
            case "Host": startHostGame(); break;
            case "Client": startClientGame(); break;
        }
    }

    public static void setGuilty(Suspect suspect) {
    }

    public static void setPlayerCharacter(Suspect suspect) {
    }

    private void startClientGame() {
        EventQueue.invokeLater(() -> {
            try {
                window = new GuessWhoGUI(!Client.getTurn());
                GuessWhoGUI.getFrame().setVisible(true);
                suspectWindow = new SuspectGUI();
            } catch (Exception ignored) {}
        });
    }

    private void startHostGame() {
        EventQueue.invokeLater(() -> {
            try {
                window = new GuessWhoGUI(Host.getTurn());
                GuessWhoGUI.getFrame().setVisible(true);
                suspectWindow = new SuspectGUI();
            } catch (Exception ignored) {}
        });
    }

    private void initSingleplayerGame() {
        EventQueue.invokeLater(() -> {
            try {
                window = new GuessWhoGUI(true);
                GuessWhoGUI.getFrame().setVisible(true);
                suspectWindow = new SuspectGUI();


            } catch (Exception ignored) {}
        });
    }

    /**
     * Initializes the game components including the game deck with the provided JSON data.
     *
     * @param jsonData The JSON data representing the game deck.
     * @throws IOException If there is an error reading the JSON data.
     */
    public static void initialize(String jsonData) throws IOException {
        JsonParser jsonParser = new JsonParser(jsonData);
        List<SuspectData> suspectDataList = jsonParser.getSuspects();
        theDeck = new Deck(jsonParser, suspectDataList);
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
     * Retrieves the game window.
     *
     * @return The game window.
     */
    public static GuessWhoGUI getWindow() {
        return window;
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
     * Retrieves the guilty suspect based on the game state.
     *
     * @return The guilty suspect.
     */
    public static Suspect getGuilty() {
        return switch (gameState) {
            case "Single" -> guilty;
            case "Host" -> Host.getOpponentSuspect();
            case "Client" -> Client.getOpponentSuspect();
            default -> null;
        };
    }

    /**
     * Retrieves the player's character.
     *
     * @return The player's character.
     */
    public static Suspect getPlayerCharacter() {
        return playerCharacter;
    }

    /**
     * Retrieves the game state.
     *
     * @return The game state.
     */
    public static String getGameState() {
        return gameState;
    }
}
