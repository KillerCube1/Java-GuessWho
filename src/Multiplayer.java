import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Multiplayer {
    
    private static Socket opponent;
    private static BufferedReader input;
    private static BufferedOutputStream output;

    public Multiplayer(Socket socket, boolean isHost){

        // Set up multiplayer communication
        try {
            Multiplayer.opponent = socket;
            Multiplayer.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Multiplayer.output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {}

        if (isHost) {

            // Run on Host game start

        } else {

            // Run on Client game start

        }

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

}
