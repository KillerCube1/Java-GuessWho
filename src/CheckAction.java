

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
                Compare =  String.valueOf(Objects.requireNonNull(GuessWhoGame.getGuilty()).getAttribute("hairColor").equals(value.substring(4)));
                trait = value.substring(4);
                value = "hairColor";
            } else if (value.startsWith("eyes")) {
                Compare = String.valueOf(Objects.requireNonNull(GuessWhoGame.getGuilty()).getAttribute("eyeColor").equals(value.substring(4)));
                trait = value.substring(4);
                value = "eyeColor";
            } else if (value.startsWith("gend")) {
                Compare = String.valueOf(Objects.requireNonNull(GuessWhoGame.getGuilty()).getAttribute("gender").equals(value.substring(4)));
                trait = value.substring(4);
                value = "gender";
            } else {
                Compare = Objects.requireNonNull(GuessWhoGame.getGuilty()).getAttribute(value);
                trait = "true";
            }
        } else {
            Compare = Objects.requireNonNull(GuessWhoGame.getGuilty()).getAttribute(value);
            trait = "true";
        }

        switch (GuessWhoGame.getGameState()) {
            case "Single" -> singleplayerTrait(value, trait, Compare);
            case "Host" -> {
                try { hostTrait(value, trait, Compare); } catch (IOException ignored) {}
            }
            case "Client" -> {
                try { clientTrait(value, trait, Compare); } catch (IOException ignored) {}
            }
        }
    }

    private void singleplayerTrait(String value, String trait, String Compare) {
        for (int i = 0; i < GuessWhoGame.getTheDeck().getTotalCards(); i++) {
            if (!String.valueOf(GuessWhoGame.getTheDeck().getSuspect(i).getAttribute(value).equals(trait)).equals(Compare)) {
                GuessWhoGame.getWindow().flipCard(i);
            }
        }
    }

    private void hostTrait(String value, String trait, String Compare) throws IOException {
        boolean storeTest = Host.getTurn();
        System.out.println(storeTest);
        if (storeTest) return;

        for (int i = 0; i < GuessWhoGame.getTheDeck().getTotalCards(); i++) {
            if (!String.valueOf(GuessWhoGame.getTheDeck().getSuspect(i).getAttribute(value).equals(trait)).equals(Compare)) {
                GuessWhoGame.getWindow().flipCard(i);
            }
        }

        System.out.println("Host End Turn");
        Host.endTurn();
    }

    private void clientTrait(String value, String trait, String Compare) throws IOException {
        boolean storeTest = !Client.getTurn();
        System.out.println(storeTest);
        if (!storeTest) return;

        for (int i = 0; i < GuessWhoGame.getTheDeck().getTotalCards(); i++) {
            if (!String.valueOf(GuessWhoGame.getTheDeck().getSuspect(i).getAttribute(value).equals(trait)).equals(Compare)) {
                GuessWhoGame.getWindow().flipCard(i);
            }
        }

        System.out.println("Client End Turn");
        Client.endTurn();
    }

}