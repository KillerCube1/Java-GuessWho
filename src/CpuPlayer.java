public class CpuPlayer {

    private static boolean ComputersTurn;
    private static boolean PlayersTurn;

    public static void RandomTurn() {

        boolean PlayersTurn = (Math.random() > 0.5);

        int x = (int) (Math.random() * GuessWhoGame.getTheDeck().getTotalCards());
        GuessWhoGame.setPlayerCharacter(GuessWhoGame.getTheDeck().getSuspect(x));

        if (!PlayersTurn) {
            CpuTurnInvoked();
        }
        else{
            PlayerTurnInvoked();
        }
    }


    private static void CpuTurnInvoked() {
        System.out.println("Computer's Turn, Computer thinking!");
        ComputersTurn = true;
        GuessWhoGUI.freezeFrame();
    }

    private static void PlayerTurnInvoked() {
        System.out.println("Received! Unfreezing the frame!");
        PlayersTurn = true;
        GuessWhoGUI.unFreezeFrame();
    }





}
