import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private static Socket socket;
    private static BufferedReader input;
    private static BufferedOutputStream output;

    public Client(Socket connection) {
        try {
            socket = connection;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {}

        Client.listenResponse();
    }

    // --------------------------
    // Game Phases
    // --------------------------

    public static void initGame() {

        // Init Host Game Variables
        int x = (int) (Math.random() * GuessWhoGame.getTheDeck().getTotalCards());
        GuessWhoGame.setGuilty(GuessWhoGame.getTheDeck().getSuspect(x));

        // Init Complete
        finishedInit();

    }

    public static void startGame() {
        new GuessWhoGame("Client");
    }

    public static void endTurn() throws IOException {
        System.out.println("Freezing Frame");
        GuessWhoGUI.freezeFrame();
        System.out.println("Sending Turn...");
        output.write((("endTurn") + "\r\n").getBytes());
        output.flush();
    }

    // --------------------------
    // Game Response Methods
    // --------------------------
    
    private static void finishedInit() {
        try {
            output.write((("clientINIT") + "\r\n").getBytes());
            output.flush();
        } catch (IOException ignored) {}
    }

    private static void pauseClient() {
        try {
            output.write((("pauseClient") + "\r\n").getBytes());
            output.flush();
            Thread.sleep(200);
        } catch (IOException | InterruptedException ignored) {}
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
                } catch (IOException ignored) {}

                // Split command up into command name and arguments
                String[] commandSections = command.split("::");
                String commandName = commandSections[0];
//                String commandArgs = "";
//                if (commandSections.length > 1) commandArgs = commandSections[1];

                // Execute response based off command name
                switch(commandName) {
                    // Game Information
                    case "hostINIT" : hostFinishInit(); break;
                    case "gameStart" : startGame(); break;
                    case "endTurn" : myTurn(); break;

                    // Get commands
                    case "getSuspect" : sendSuspect(); break;

                    // Set commands


                    // Stop Host listener
                    case "pauseHost" : pauseHost(); break;

                    // Stop listener
                    case "pauseEvent" : loop = false; break;
                }

            }
        }).start();
    }

    // --------------------------
    // Listener Responses
    // --------------------------

    private static void pauseHost() {
        try {
            output.write((("pauseEvent") + "\r\n").getBytes());
            output.flush();
        } catch (IOException ignored) {}
    }

    private static void myTurn() {
        // Start Clients Turn
        GuessWhoGUI.unFreezeFrame();
    }

    private static void sendSuspect() {
        try {
            output.write(((GuessWhoGame.getPlayerCharacter().getAttribute("name")) + "\r\n").getBytes());
            output.flush();
        } catch (IOException ignored) {}
    }

    private static void hostFinishInit() {
        Client.initGame();
    }

    // --------------------------
    // Server Getters
    // --------------------------

    public static boolean getTurn() {
        try {
            pauseClient(); // pauses command listener

            // get turn value
            output.write((("getTurn") + "\r\n").getBytes());
            output.flush();
            boolean value = Boolean.parseBoolean(input.readLine());

            listenResponse(); // start command listener
            return value;
        } catch (IOException ex) {
            return false;
        }
    }

    public static Suspect getOpponentSuspect() {
        try {
            pauseClient(); // pauses command listener

            // get turn value
            output.write((("getSuspect") + "\r\n").getBytes());
            output.flush();
            String value = input.readLine();

            Suspect suspect = null;
            for(Suspect person : GuessWhoGame.getTheDeck().susDeck) {
                if (person.getAttribute("name").equals(value)) {
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
