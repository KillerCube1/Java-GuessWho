import java.util.HashMap;
import java.util.Map;

/**
 * This class represents Suspects from the game Guess Who as well as their corresponding attributes.
 * Each Suspect has a set of attributes such as name, hair color, eye color, etc.
 * It also includes a CardImage representing the Suspect's image.
 *
 * @author Rylan, Damien
 *
 */
public class Suspect {
	
	// Attribute map
	private final Map<String, String> attributes;

	// Card image
	private final CardImage card;



	/**
	 * Constructor method to create the Suspect.
	 *
	 * @param n     name of this Suspect
	 * @param hC    hair color of this Suspect
	 * @param bld   whether this Suspect is bald
	 * @param eC    eye color of this Suspect
	 * @param g     gender of this Suspect
	 * @param h     whether this Suspect is wearing a hat
	 * @param gls   whether this Suspect wears glasses
	 * @param moust whether this Suspect has a moustache
	 * @param brd   whether this Suspect has a beard
	 * @param rC    whether this Suspect has Rosy Cheeks
	 */
	public Suspect(String n, String hC, boolean bld, String eC, String g, boolean h, boolean gls, boolean moust,
			boolean brd, boolean rC) {// constructor

		attributes = new HashMap<>();

		attributes.put("name", n);
		attributes.put("hairColor", hC);
		attributes.put("eyeColor", eC);
		attributes.put("gender", g);
		attributes.put("bald", String.valueOf(bld));
		attributes.put("hat", String.valueOf(h));
		attributes.put("glasses", String.valueOf(gls));
		attributes.put("moustache", String.valueOf(moust));
		attributes.put("beard", String.valueOf(brd));
		attributes.put("rosyCheeks", String.valueOf(rC));

		card = new CardImage(n +".png");
	}// constructor




	/**
	 * Retrieves the value of the specified attribute.
	 *
	 * @param key the key of the attribute
	 * @return the value of the attribute
	 */
	public String getAttribute(String key) {
		return attributes.get(key);
	}



	/**
	 * Retrieves the CardImage associated with this Suspect.
	 *
	 * @return the CardImage of this Suspect
	 */
	public CardImage getCard() {
		return card;
	}


}//Suspect
