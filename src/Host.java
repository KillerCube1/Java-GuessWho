import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Host {

    private static Socket socket;
    private static BufferedReader input;
    private static BufferedOutputStream output;
    
    // Server Variables
    private static boolean hostTurn;

    public Host(Socket connection) {
        try {
            socket = connection;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {}

        Host.listenResponse();
        Host.initGame();
    }

    // --------------------------
    // Game Phases
    // --------------------------

    public static void initGame() {

        // Init Server Variables
        hostTurn = (Math.random() > 0.5);

        // Init Host Game Variables
        int x = (int) (Math.random() * GuessWhoGame.getTheDeck().getTotalCards());
        GuessWhoGame.setPlayerCharacter(GuessWhoGame.getTheDeck().getSuspect(x));

        // Init Complete
        finishedInit();

    }

    public static void startGame() {
        new GuessWhoGame("Host");
    }

    public static void endTurn() throws IOException {
        GuessWhoGUI.freezeFrame();
        hostTurn = false;
        output.write((("endTurn") + "\r\n").getBytes());
        output.flush();
    }

    // --------------------------
    // Game Response Methods
    // --------------------------

    private static void finishedInit() {
        try {
            output.write((("hostINIT") + "\r\n").getBytes());
            output.flush();
        } catch (IOException ignored) {}
    }

    private static void pauseHost() throws IOException {
        output.write((("pauseHost") + "\r\n").getBytes());
        output.flush();
    }

    // --------------------------
    // Listener
    // --------------------------

    public static void listenResponse() {
        new Thread(() -> {
            boolean loop = true;
            while (loop) {

                // Wait for command
                String command = "";
                try {
                    command = input.readLine();
                } catch (IOException e) {}

                // Split command up into command name and arguments
                String[] commandSections = command.split("::");
                String commandName = commandSections[0];
                String commandArgs = "";
                if (commandSections.length > 1) commandArgs = commandSections[1];

                // Execute response based off command name
                switch(commandName) {
                    // Game Information
                    case "clientINIT" : clientFinishInit(); break;
                    case "endTurn" : myTurn(); break;

                    // Get commands
                    case "getTurn" : sendTurn(); break;
                    case "getSuspect" : sendSuspect(commandArgs); break;

                    // Set commands


                    // Stop Client listener
                    case "pauseClient" : pauseClient(); break;

                    // Stop listener
                    case "pauseEvent" : loop = false; break;
                }

            }
        }).start();
    }

    // --------------------------
    // Listener Responses
    // --------------------------

    private static void pauseClient() {
        try {
            output.write((("pauseEvent") + "\r\n").getBytes());
            output.flush();
        } catch (IOException ignored) {}
    }

    private static void myTurn() {
        hostTurn = true;
        // Start Host Turn
        GuessWhoGUI.unFreezeFrame();
    }

    private static void sendTurn() {
        try {
            output.write(((hostTurn) + "\r\n").getBytes());
            output.flush();
        } catch (IOException ignored) {}
    }

    private static void sendSuspect(String args) {
        try {
            output.write(((GuessWhoGame.getPlayerCharacter().getName()) + "\r\n").getBytes());
            output.flush();
        } catch (IOException ignored) {}
    }

    private static void clientFinishInit() {
        try {
            output.write((("gameStart") + "\r\n").getBytes());
            output.flush();
        } catch (IOException ignored) {}
        
        startGame();
    }
    
    // --------------------------
    // Direct Getters
    // --------------------------

    public static boolean getTurn() {
        return hostTurn;
    }

    // --------------------------
    // Server Getters
    // --------------------------

    public static Suspect getOpponentSuspect() {
        try {
            pauseHost(); // pauses command listener

            // get turn value
            output.write((("getSuspect") + "\r\n").getBytes());
            output.flush();
            String value = input.readLine();

            Suspect suspect = null;
            for(Suspect person : GuessWhoGame.getTheDeck().getSusDeck()) {
                if (person.getName().equals(value)) {
                    suspect = person;
                    break;
                }
            }

            listenResponse(); // start command listener
            return suspect;
        } catch (IOException ex) {
            return null;
        }
    }

}
