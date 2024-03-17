
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * The CardImage class maintains images for each card (back and front).
 * It generates the front image using a mathematical algorithm to isolate an area of the image.
 * This class extends BufferedImage.
 *
 * @Author: Rylan, Damien
 */
public class CardImage extends BufferedImage {//CardImage

	final static int WIDTH = 96;   //The width of each card  
	final static int HEIGHT = 140;  //The height of each card  
	BufferedImage frontCardImage;
	BufferedImage backCardImage;

	/**
	 * Constructs a CardImage object with the specified card name.
	 * The width and height of the image are set to match the size of individual cards.
	 *
	 * @param cardName The name of the card image file.
	 */
	public CardImage(String cardName) {//constructor
		super(WIDTH, HEIGHT, 3);
		InputStream stream = getClass().getResourceAsStream("/Images/"+cardName);
		InputStream stream2 = getClass().getResourceAsStream("/Images/back.png");

		try {//try
			assert stream != null;
			frontCardImage = ImageIO.read(stream);
			assert stream2 != null;
			backCardImage = ImageIO.read(stream2);
		}//try 
		catch (IOException e) {//catch
			throw new RuntimeException(e);
		}//catch
	}//constructor



	/**
	 * Retrieves the front image of the card.
	 * Uses a graphics object to draw the front image onto a new BufferedImage.
	 *
	 * @return The front image of the card.
	 */
	public BufferedImage getFrontImage() {//getFrontImage()


		Graphics g = frontCardImage.createGraphics();
		g.drawImage(frontCardImage, 0, 0, WIDTH, HEIGHT, 0, 0, WIDTH, HEIGHT, null);

		return frontCardImage;
	}//getFrontImage()



	/**
	 * Retrieves the back image of the card.
	 * Uses a graphics object to draw the back image onto a new BufferedImage.
	 *
	 * @return The back image of the card.
	 */
	public BufferedImage getBackImage() {//getBackImage()

		Graphics g = backCardImage.createGraphics();
		g.drawImage(backCardImage, 0, 0, WIDTH, HEIGHT, 0, 0, WIDTH, HEIGHT, null);

		return backCardImage;
	}//getBackImage()

}//CardImage
