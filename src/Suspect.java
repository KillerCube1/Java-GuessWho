public class Suspect {
	private final String name;
	private final String eyeColor;
	private final boolean beard;
	private final boolean glasses;
	private final boolean moustache;
	private final boolean bald;
	private final boolean rosyCheeks;
	private final String hairColor;
	private final boolean hat;
	private final String gender;

	private final CardImage card;

	public Suspect(String name, String hairColor, boolean bald, String eyeColor, String gender, boolean hat, boolean glasses, boolean moustache, boolean beard, boolean rosyCheeks) {
		this.name = name;
		this.eyeColor = eyeColor;
		this.beard = beard;
		this.glasses = glasses;
		this.moustache = moustache;
		this.bald = bald;
		this.rosyCheeks = rosyCheeks;
		this.hairColor = hairColor;
		this.hat = hat;
		this.gender = gender;
		card = new CardImage(getName() +".png");

	}

	public String getName() {
		return name;
	}


	public String getEyeColor() {
		return eyeColor;
	}



	public boolean hasBeard() {
		return beard;
	}


	public boolean hasGlasses() {
		return glasses;
	}


	public boolean hasMoustache() {
		return moustache;
	}



	public boolean isBald() {
		return bald;
	}



	public boolean hasRosyCheeks() {
		return rosyCheeks;
	}



	public String getHairColor() {
		return hairColor;
	}


	public boolean hasHat() {
		return hat;
	}



	public String getGender() {
		return gender;
	}


	public CardImage getCard() {
		return card;
	}
}
