import java.util.Random;

/**
 * The Deck class represents a collection of suspect cards.
 * It allows for the creation of a deck with default size of 24 suspects.
 *
 * @Author: Rylan, Damien
 */
public class Deck {// Deck

	Suspect[] susDeck = new Suspect[24]; // There are 24 suspects in the game
	int totalCards = 24;
	SusData list = new SusData();

	/**
	 * Constructs a Deck object with 24 suspect cards.
	 * Initializes the deck with suspects and shuffles them.
	 */

	public Deck() {
		susDeck = new Suspect[totalCards];

		// Initialize the deck with suspects
		for (int i = 0; i < totalCards; i++) {
			susDeck[i] = new Suspect(list.getName(i), list.getHairColor(i), list.getBald(i), list.getEyeColor(i),
					list.getGender(i), list.getHat(i), list.getGlasses(i), list.getMoustache(i), list.getBeard(i),
					list.getRosyCheeks(i));
		}

		shuffle();
	}

	/**
	 * Simulates shuffling of the deck using the Fisher-Yates shuffle algorithm.
	 */

	public void shuffle() {
		Random rnd = new Random();
		for (int i = totalCards - 1; i > 0; i--) {
			int j = rnd.nextInt(i + 1);
			Suspect temp = susDeck[i];
			susDeck[i] = susDeck[j];
			susDeck[j] = temp;
		}
	}




	/**
	 * Retrieves the total number of cards in the deck.
	 *
	 * @return The total number of cards in the deck.
	 */



	public int getTotalCards() {// getTotalCards
		return totalCards;
	}// getTotalCards


	/**
	 * Retrieves the array of suspect cards in the deck.
	 *
	 * @return The array of suspect cards.
	 */



	public Suspect[] getSusDeck() {
		return susDeck;
	}




	/**
	 * Retrieves the SusData object associated with the deck.
	 *
	 * @return The SusData object.
	 */
	public SusData getList() {
		return list;
	}




	/**
	 * Retrieves the suspect card at the specified index in the deck.
	 *
	 * @param index The index of the suspect card.
	 * @return The suspect card at the specified index.
	 */

	public Suspect getSuspect(int index)
	{
		return susDeck[index];
	}

}// Deck
