import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Deck {
	private final int totalCards = 24;
	private final Suspect[] susDeck = new Suspect[totalCards];

	public Deck(JsonParser jsonParser, List<SuspectData> suspectDataList) throws IOException {
		// Initialize the deck with suspects
		for (int i = 0; i < totalCards; i++) {
			SuspectData suspectData = suspectDataList.get(i);
			susDeck[i] = new Suspect(
					suspectData.getName(),
					suspectData.getHairColor(),
					suspectData.isBald(),
					suspectData.getEyeColor(),
					suspectData.getGender(),
					suspectData.isHat(),
					suspectData.isGlasses(),
					suspectData.isMoustache(),
					suspectData.isBeard(),
					suspectData.isRosyCheeks());
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
