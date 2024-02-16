import java.util.Random;

/**
 * This class allows for the creation of a Deck object.  A Deck is a collection of Suspects (suspect cards)
 * All decks are - by default- 24 Suspects in length 
 * @author jbutka
 *
 */
public class Deck {// Deck

	Suspect[] susDeck = new Suspect[24]; // There are 24 suspects in the game
	int totalCards = 24;
	SusData list = new SusData();

	/**
	 * This constructor allows the user to create a 24-card deck of Suspects, Deck
	 * object.
	 * 
	 */
	public Deck() {
		susDeck = new Suspect[totalCards];

		// Initialize the deck with suspects
		for (int i = 0; i < totalCards; i++) {
			susDeck[i] = new Suspect(list.getName(i), list.getHairColor(i), list.getBald(i), list.getEyeColor(i),
					list.getGender(i), list.getHat(i), list.getGlasses(i), list.getMoustache(i), list.getBeard(i),
					list.getRosyCheeks(i));
		}

		//Fisher Yates shuffle Algorithm
		shuffle();
	}

	/**
	 * This method simulates the shuffling of a deck.
	 * It does so using the Fisher Yates shuffle Algorithm
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


	



	public int getTotalCards() {// getTotalCards
		return totalCards;
	}// getTotalCards



	public Suspect[] getSusDeck() {
		return susDeck;
	}


	public SusData getList() {
		return list;
	}

	

	public Suspect getSuspect(int index)
	{
		return susDeck[index];
	}

}// Deck
