import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

import ClassExtensions.CheckButton;

/**
 * The CheckAction class implements the ActionListener interface and defines actions to be performed
 * when attribute-checking buttons are clicked.
 * It checks the attributes of suspects and updates the game board accordingly.
 */
public class CheckAction implements ActionListener {

    private final JsonParser jsonParser;

    public CheckAction(String json) throws IOException {
        this.jsonParser = new JsonParser(json);
    }

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
                Compare = GuessWhoGame.getGuilty().getData().getHairColor();
                trait = value.substring(4);
                value = "hairColor";
            } else if (value.startsWith("eyes")) {
                Compare = GuessWhoGame.getGuilty().getData().getEyeColor();
                trait = value.substring(4);
                value = "eyeColor";
            } else if (value.startsWith("gend")) {
                Compare = GuessWhoGame.getGuilty().getData().getGender();
                trait = value.substring(4);
                value = "gender";
            } else {
                Compare = jsonParser.getAttribute(GuessWhoGame.getGuilty(), value);
                trait = "true";
            }
        } else {
            Compare = jsonParser.getAttribute(GuessWhoGame.getGuilty(), value);
            trait = "true";
        }

        for (int i = 0; i < GuessWhoGame.getTheDeck().getTotalCards(); i++) {
            if (!String.valueOf(jsonParser.getAttribute(i, value).equals(trait)).equals(Compare)) {
                switch (GuessWhoGame.getGameState()) {
                    case "Single" -> singleplayerTrait(i);
                    case "Host" -> {
                        try { hostTrait(i); } catch (IOException ignored) {}
                    }
                    case "Client" -> {
                        try { clientTrait(i); } catch (IOException ignored) {}
                    }
                } ;
            }
        }
    }

    private void singleplayerTrait(int i) {
        GuessWhoGame.getWindow().flipCard(i);
    }

    private void hostTrait(int i) throws IOException {
        if (Host.getTurn()) {
            GuessWhoGame.getWindow().flipCard(i);
            Host.endTurn();
        }
    }

    private void clientTrait(int i) throws IOException {
        if (!Client.getTurn()) {
            GuessWhoGame.getWindow().flipCard(i);
            Client.endTurn();
        }
    }
}
