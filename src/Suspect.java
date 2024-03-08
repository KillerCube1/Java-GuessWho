public class Suspect {
	SuspectData data;
	CardImage card;

	public Suspect(SuspectData data) {
		this.data = data;
	}

	public Suspect(String name, String hairColor, boolean bald, String eyeColor, String gender, boolean hat, boolean glasses, boolean moustache, boolean beard, boolean rosyCheeks) {
	}

	public SuspectData getData() {
		return data;
	}

	public void setData(SuspectData data) {
		this.data = data;
	}



	public CardImage getCard() {
		return card;
	}
}



