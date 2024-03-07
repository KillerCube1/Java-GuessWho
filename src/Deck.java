import java.io.IOException;
import java.util.Random;

public class Deck {
	private final int totalCards = 24;
	private final Suspect[] susDeck = new Suspect[totalCards];

	public Deck(JsonParser jsonParser) throws IOException {

		// Initialize the deck with suspects
		for (int i = 0; i < totalCards; i++) {
			susDeck[i] = new Suspect(
					jsonParser.getName(i),
					jsonParser.getHairColor(i),
					jsonParser.isBald(i),
					jsonParser.getEyeColor(i),
					jsonParser.getGender(i),
					jsonParser.hasHat(i),
					jsonParser.hasGlasses(i),
					jsonParser.hasMoustache(i),
					jsonParser.hasBeard(i),
					jsonParser.hasRosyCheeks(i),
					jsonParser.index);
		}

		shuffle();
	}

	// Fisher-Yates shuffle algorithm
	public void shuffle() {
		Random rnd = new Random();
		for (int i = totalCards - 1; i > 0; i--) {
			int j = rnd.nextInt(i + 1);
			Suspect temp = susDeck[i];
			susDeck[i] = susDeck[j];
			susDeck[j] = temp;
		}
	}

	public int getTotalCards() {
		return totalCards;
	}

	public Suspect[] getSusDeck() {
		return susDeck;
	}

	public Suspect getSuspect(int index) {
		return susDeck[index];
	}
}
