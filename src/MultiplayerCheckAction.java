

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ClassExtensions.CheckButton;


/**
 * The CheckAction class implements the ActionListener interface and defines actions to be performed
 * when attribute-checking buttons are clicked.
 * It checks the attributes of suspects and updates the game board accordingly.
 */
public class MultiplayerCheckAction implements ActionListener {


    /**
     * Performs the action when a button is clicked.
     * Checks the attributes of suspects and flips cards based on the comparison results.
     *
     * @param e The ActionEvent representing the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        CheckButton Suspect = (CheckButton) e.getSource();

        String Compare;
        String value = Suspect.getCompareValue();
        String trait;

        if (value.length() >= 4) {
            if (value.startsWith("hair")) {
                Compare =  String.valueOf(MultiplayerGuessWhoGame.getGuilty().getAttribute("hairColor").equals(value.substring(4)));
                trait = value.substring(4);
                value = "hairColor";
            } else if (value.startsWith("eyes")) {
                Compare = String.valueOf(MultiplayerGuessWhoGame.getGuilty().getAttribute("eyeColor").equals(value.substring(4)));
                trait = value.substring(4);
                value = "eyeColor";
            } else if (value.startsWith("gend")) {
                Compare = String.valueOf(MultiplayerGuessWhoGame.getGuilty().getAttribute("gender").equals(value.substring(4)));
                trait = value.substring(4);
                value = "gender";
            } else {
                Compare = MultiplayerGuessWhoGame.getGuilty().getAttribute(value);
                trait = "true";
            }
        } else {
            Compare = MultiplayerGuessWhoGame.getGuilty().getAttribute(value);
            trait = "true";
        }

        for (int i = 0; i < MultiplayerGuessWhoGame.getTheDeck().getTotalCards(); i++) {
            if (!String.valueOf(MultiplayerGuessWhoGame.getTheDeck().getSuspect(i).getAttribute(value).equals(trait)).equals(Compare)) {
                MultiplayerGuessWhoGame.getWindow().flipCard(i);
            }
        }

    }

}
