import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * This class is responsible for maintaining the correct images for each card (back and front of card)
 * The back image is a stand-alone jpeg.
 * The front image is a result of a mathematical algorithm that isolates an area of the image to show a specific face
 * This can be seen in the getFrontImage()   
 * @author jbutka
 *
 */
public class CardImage extends BufferedImage {//CardImage
	
	final static int WIDTH = 96;   //The width of each card  
	final static int HEIGHT = 140;  //The height of each card  
	BufferedImage frontCardImage;
	BufferedImage backCardImage;

	/**
	 * Constructor to create a CardImage object whose width will be 600 and height 873 
	 * which are the size of each individual card found on the back.png and various Suspect image files.
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


	public BufferedImage getFrontImage() {//getFrontImage()
	

		Graphics g = frontCardImage.createGraphics();
		g.drawImage(frontCardImage, 0, 0, WIDTH, HEIGHT, 0, 0, WIDTH, HEIGHT, null);
		
		return frontCardImage;
	}//getFrontImage()


	public BufferedImage getBackImage() {//getBackImage()
		
		Graphics g = backCardImage.createGraphics();
		g.drawImage(backCardImage, 0, 0, WIDTH, HEIGHT, 0, 0, WIDTH, HEIGHT, null);
		
		return backCardImage;
	}//getBackImage()

}//CardImage
