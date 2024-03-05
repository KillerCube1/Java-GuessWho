import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LabelListener extends MouseAdapter {

    private Component cardLabel;

    @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Clicked on suspect: " + GuessWhoGame.getTheDeck().getSuspect(index).getAttribute("name"));
            try {
                guessSuspect(GuessWhoGame.getTheDeck().getSuspect(index));
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            cardLabel.removeMouseListener(this);
        }
}
