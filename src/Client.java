import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private static Socket host;
    private static BufferedReader input;
    private static BufferedOutputStream output;

    public Client(Socket socket) {
        try {
            host = socket;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {}

        new GuessWhoGame("Client");
    }

    // Game Phases
    public static void initGame() {

    }

    // Listener

    // Getters

    // Setters

}