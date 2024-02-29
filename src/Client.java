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
        } catch (IOException ex) {}

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

    // --------------------------
    // Game Response Methods
    // --------------------------
    
    private static void finishedInit() {
        try {
            output.write((("clientINIT") + "\r\n").getBytes());
            output.flush();
        } catch (IOException e) {}
    }

    // --------------------------
    // Listener
    // --------------------------

    public static void listenResponse() {
        new Thread(() -> {
            while (true) {

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
                    case "getExample": sendExample(commandArgs);    break;
                    case "hostINIT"  : hostFinishInit(commandArgs); break;
                    case "gameStart" : startGame();                 break;
                    case "turn"      : turnValue(commandArgs);      break;
                }

            }
        }).start();
    }

    // --------------------------
    // Listener Responses
    // --------------------------

    private static void sendExample(String args) {
        try {
            output.write((("Example") + "\r\n").getBytes());
            output.flush();
        } catch (IOException ex) {}
    }

    private static void turnValue(String args) {

    }

    private static void hostFinishInit(String args) {
        Client.initGame();
    }

    // --------------------------
    // Variable Getters
    // --------------------------

    private static boolean getTurn() {
        try {
            output.write((("getTurn") + "\r\n").getBytes());
            output.flush();
        } catch (IOException ex) {}
        
    }

}
