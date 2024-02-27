import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Host {

    private static Socket socket;
    private static BufferedReader input;
    private static BufferedOutputStream output;

    public Host(Socket connection) {
        try {
            socket = connection;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {}

        new GuessWhoGame("Host");
    }

    // Game Phases
    public static void initGame() {

    }

    // Listener

    // Getters

    // Setters

}
