import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LabelListener extends MouseAdapter {

    private final Component cardLabel;
    private final int index;

    public LabelListener(Component cardLabel, int index) {
        this.cardLabel = cardLabel;
        this.index = index;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked on suspect: " + GuessWhoGame.getTheDeck().getSuspect(index).getName());
        try {
            guessSuspect(GuessWhoGame.getTheDeck().getSuspect(index));
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        cardLabel.removeMouseListener(this);


    }

    public void guessSuspect(Suspect character) throws InterruptedException {
    }
}
