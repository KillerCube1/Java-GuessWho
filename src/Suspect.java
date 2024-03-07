public class Suspect {
	SuspectData data;
	CardImage card;
	private final int index;

	public Suspect(SuspectData data, int index) {
		this.data = data;
		this.index = index;
	}

	public SuspectData getData() {
		return data;
	}

	public void setData(SuspectData data) {
		this.data = data;
	}

	public int getIndex() {
		return index;

	}

	public CardImage getCard() {
		return card;
	}
}



