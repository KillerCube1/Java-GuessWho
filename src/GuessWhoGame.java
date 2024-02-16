import java.awt.EventQueue;

public class GuessWhoGame {
	private static GuessWhoGUI window;
	private static SuspectGUI suspectWindow;
	private static Deck theDeck;
	private static Suspect guilty;
	private static Suspect playerCharacter;

	public GuessWhoGame() {
		EventQueue.invokeLater(() -> {
			try {
				window = new GuessWhoGUI();
				window.getFrame().setVisible(true);
				suspectWindow = new SuspectGUI();
			} catch (Exception ignored) {
			}
		});
	}

	public static void setTheDeck(Deck theDeck) {
		GuessWhoGame.theDeck = theDeck;
	}

	public static void setGuilty(Suspect guilty) {
		GuessWhoGame.guilty = guilty;
	}

	public static void setPlayerCharacter(Suspect playerCharacter) {
		GuessWhoGame.playerCharacter = playerCharacter;
	}

	public static GuessWhoGUI getWindow() {
		return window;
	}

	public static SuspectGUI getSuspectWindow() {
		return suspectWindow;
	}

	public static Deck getTheDeck() {
		return theDeck;
	}

	public static Suspect getGuilty() {
		return guilty;
	}

	public static Suspect getPlayerCharacter() {
		return playerCharacter;
	}

	// Ends the turn of the current client
	public void endTurn(){

	}

	// Start the turn of the current client
	//
	// This method will also handle whether the player
	// lost or not
	public void startTurn() {

	}

	// Get the opponent suspect attribute
	public void getOpponentAttribute(String attribute){

	}

	// Guess the suspect and return whether it was correctly guessed
	public void guessSuspect(){

	}

	

}