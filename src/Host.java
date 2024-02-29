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
        } catch (IOException ex) {}

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

    // --------------------------
    // Game Response Methods
    // --------------------------

    private static void finishedInit() {
        try {
            output.write((("hostINIT") + "\r\n").getBytes());
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
                    case "getExample": sendExample(commandArgs);      break;
                    case "clientINIT": clientFinishInit(commandArgs); break;
                    case "getTurn"   : sendTurn(commandArgs);         break;
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

    private static void sendTurn(String args) {
        try {
            output.write((("turn::" + String.valueOf(hostTurn)) + "\r\n").getBytes());
            output.flush();
        } catch (IOException ex) {}
    }

    private static void clientFinishInit(String args) {
        try {
            output.write((("gameStart") + "\r\n").getBytes());
            output.flush();
        } catch (IOException ex) {}
        
        startGame();
    }

}
