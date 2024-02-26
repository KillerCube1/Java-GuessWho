import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Multiplayer {
    
    private static Socket opponent;
    private static BufferedReader input;
    private static BufferedOutputStream output;

    private static boolean listening;

    // Multiplayer variables
    private static boolean clientTurn;
    private static float myMom;

    public Multiplayer(Socket socket, boolean isHost){

        // Set up multiplayer communication
        try {
            Multiplayer.opponent = socket;
            Multiplayer.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Multiplayer.output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {}

        // Pre-Game Init

        // --------------

        if (isHost) {

            // Run on Host game start
            new GuessWhoGame("Host");
            // --------------

        } else {

            // Run on Client game start
            new GuessWhoGame("Client");
            // --------------

        }

    }

    // Multiplayer Listener
    public static void listenResponse() {
        listening = true;
        new Thread(() -> {
            while (listening) {
                try {
                    String command = input.readLine();

                    if (command.startsWith("get")) {
                        if (command.startsWith("SUS", 3)) {
                            output.write((("Blap") + "\r\n").getBytes());
                            output.flush();
                        } else if (command.startsWith("TRN", 3)) {
                            output.write((String.valueOf(clientTurn) + "\r\n").getBytes());
                            output.flush();
                        } else if (command.startsWith("MMV", 3)) {
                            output.write((String.valueOf(myMom) + "\r\n").getBytes());
                            output.flush();
                        }
                    }

                } catch (IOException ex) {}
            }
        }).start();
    }

    public static void breakListener() {
        listening = false;
    }

    // Multiplayer Methods

    /**
     * Sends a request to the opponent to get the opponent's suspect.
     *
     * @return The opponent's suspect received from the server.
     */
    public static String getOpponentSuspect() {
        try {
            output.write("getSUS\r\n".getBytes());
            output.flush();
            return input.readLine();
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
            return null;
        }
    }

    public static String getTurn() {
        try {
            output.write("getTRN\r\n".getBytes());
            output.flush();
            return input.readLine();
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
            return null;
        }
    }

    public static String getMyMom() {
        try {
            output.write("getMMV\r\n".getBytes());
            output.flush();
            return input.readLine();
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
            return null;
        }
    }

    // Host Methods
    public static void setTurn(boolean turn) {
        clientTurn = turn;
    }

    public static void setMyMom(float value) {
        myMom = value;
    }


}
