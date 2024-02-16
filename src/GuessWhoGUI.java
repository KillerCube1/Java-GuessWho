import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.*;

import ClassExtensions.CheckButton;

/**
 * This class contains the code responsible for displaying the game board for Guess Who.
 *
 * @author jbutka
 *
 */
public class GuessWhoGUI extends JFrame {//GuessWhoGUI

	private final JFrame frame;
	private int wrongGuesses = 0;
	private int guessesLeft = 3;

	private final JLabel[] susGrid = new JLabel[24];



	/**
	 * This constructor sets up the UI for Guess Who
	 */
	public GuessWhoGUI() {//constructor

		// App Set Up
		frame = new JFrame("Guess Who");

		try {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(null);
			frame.setSize(1600, 850);
			Color bgd = new Color(40, 40, 40);
			frame.getContentPane().setBackground(bgd);
			GuessWhoGame.setTheDeck(new Deck());
		} catch (Exception ignored) {
		}

		int FrameButtonWidth = 200;
		int FrameButtonHeight = 50;
		int xFramePosition = 1370;
		int yFramePosition = 20;

		CheckButton[] buttonList = {
			new CheckButton("Male?", "gendmale", new Color(0xFF7F6E)),
			new CheckButton("Female?", "gendfemale", new Color(0x7A67E2)),
			new CheckButton("Hat?", "hat", new Color(0x00CCCC)),
			new CheckButton("Bald?", "bald", new Color(0x6BB536)),
			new CheckButton("Beard?", "beard", new Color(0x4E93CC)),
			new CheckButton("Moustache?", "moustache", new Color(0xFFE7B5)),
			new CheckButton("RosyCheeks?", "rosyCheeks", new Color(0x9D37E1)),
			new CheckButton("Glasses?", "glasses", new Color(0x7EFF8B)),
			new CheckButton("BlackHair?", "hairblack", new Color(0xFF824D)),
			new CheckButton("RedHair?", "hairred", new Color(0xE65A79)),
			new CheckButton("BrownHair?", "hairbrown", new Color(0xFFE53A)),
			new CheckButton("BlondeHair?", "hairblonde", new Color(0x9FDFFF)),
			new CheckButton("WhiteHair?", "hairwhite", new Color(0x95CC99))
		};

		for(int i = 0; i < 12; i++) {
			yFramePosition += 55;

			for(int j=0;j<buttonList.length;j++) {
				buttonList[j].setBounds(xFramePosition - (i * -2), yFramePosition - (i * (60 - j * 5)), FrameButtonWidth, FrameButtonHeight);
				frame.getContentPane().add(buttonList[j]);
			}

		}
		createSuspectGrid();

		for(CheckButton button : buttonList) {
			button.addActionListener(new CheckAction());
			button.setFocusable(false);
			button.setVisible(true);
		}


		// Assign Random Player Suspect (guilty)
		int x = (int) (Math.random() * GuessWhoGame.getTheDeck().getTotalCards());
		System.out.println(GuessWhoGame.getTheDeck().getSuspect(x).getAttribute("name"));
		GuessWhoGame.setGuilty(GuessWhoGame.getTheDeck().getSuspect(x));

		int randomPlayerCharacter = (int) (Math.random() * GuessWhoGame.getTheDeck().getTotalCards());
		GuessWhoGame.setPlayerCharacter(GuessWhoGame.getTheDeck().getSuspect(randomPlayerCharacter));

		// Player Suspect Card Image
		ImageIcon cardIcon = new ImageIcon(GuessWhoGame.getPlayerCharacter().getCard().getFrontImage());
		Image image = cardIcon.getImage();
		Image newimg = image.getScaledInstance(153, 224,  Image.SCALE_SMOOTH);
		cardIcon = new ImageIcon(newimg);

		// Player Suspect Title Display
		JLabel cardTitle = new JLabel("Your Character:");
		cardTitle.setBackground(Color.WHITE);
		cardTitle.setForeground(Color.WHITE);
		cardTitle.setFont(new Font("Calibri", Font.BOLD, 25));
		cardTitle.setBounds(1200, 40, 192, 20);
		frame.getContentPane().add(cardTitle);


		// Player Suspect Card Display
		JLabel chosenCard = new JLabel("");
		chosenCard.setBounds(1200, 50, 192, 280);
		frame.getContentPane().add(chosenCard);
		chosenCard.setIcon(cardIcon);

	}//constructor



	public void guessSuspect(Suspect character) {
		if (character == GuessWhoGame.getGuilty()) {
			System.out.println("CORRECT!");
			JFrame winnerFrame = new JFrame("Guess Who");
			winnerFrame.setSize(300, 300);
			winnerFrame.setLocationRelativeTo(null);

			JLabel winnerLabel = new JLabel("Game Over you win");
			winnerLabel.setHorizontalAlignment(JLabel.CENTER);
			winnerFrame.add(winnerLabel);
			winnerFrame.setVisible(true);

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					winnerFrame.setVisible(false);
				}
			}, 3000);
		} else {
			guessesLeft -= 1;
			System.out.print("You have " + guessesLeft + " guesses left\n");
			wrongGuesses += 1;
			while (wrongGuesses == 3) {
				System.out.println("You have " + wrongGuesses + " wrong guesses.\n");
				if (wrongGuesses >= 3) {
					JFrame loserFrame = new JFrame("Guess Who");
					loserFrame.setSize(300, 300);
					loserFrame.setLocationRelativeTo(null);

					JLabel loserLabel = new JLabel("Game Over you lose!");
					loserLabel.setHorizontalAlignment(JLabel.CENTER);
					loserFrame.add(loserLabel);
					loserFrame.setVisible(true);

					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							loserFrame.setVisible(false);
						}
					}, 3000);
					System.exit(0);
				}
			}
		}
	}

	public JFrame getFrame() {//getFrame
		return frame;
	}//getFrame

	public Deck getDeck() {
		return GuessWhoGame.getTheDeck();
	}

	public void createSuspectGrid() {
		int cardLabelWidth = 180;
		int cardLabelHeight = 200;
		int cardsPerRow = 6;
		int row;
		int col;

		for (int i = 0; i < GuessWhoGame.getTheDeck().getTotalCards(); i++) {
			row = i / cardsPerRow;
			col = i % cardsPerRow;

			int xPosition = 20 + col * (cardLabelWidth);
			int yPosition = row * (cardLabelHeight);

			JLabel cardLabel = new JLabel(new ImageIcon(GuessWhoGame.getTheDeck().getSuspect(i).getCard().getFrontImage()));
			cardLabel.setBounds(xPosition, yPosition, cardLabelWidth, cardLabelHeight);
			cardLabel.setIcon(new ImageIcon(GuessWhoGame.getTheDeck().getSuspect(i).getCard().getFrontImage()));


			final int index = i;
			cardLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("Clicked on suspect: " + GuessWhoGame.getTheDeck().getSuspect(index).getAttribute("name"));

					guessSuspect(GuessWhoGame.getTheDeck().getSuspect(index));

				}
			});


			frame.getContentPane().add(cardLabel);
			susGrid[i] = cardLabel;

		}
	}

	public void flipCard(int index) {
		JLabel cardLabel = susGrid[index];
		cardLabel.setIcon(new ImageIcon(GuessWhoGame.getTheDeck().getSuspect(index).getCard().getBackImage()));

	}

}//GuessWhoGUI
