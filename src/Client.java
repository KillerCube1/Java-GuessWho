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

        new GuessWhoGame("Client");
    }

    // Game Phases
    public static void initGame() {

    }

    // Listener
    public static void listenResponse() {
        new Thread(() -> {
            while (true) {
                String command = "";
                try {
                    command = input.readLine();
                } catch (IOException e) {}

                String[] commandSections = command.split("::");

                switch(commandSections[0]) {
                    case "getSUS": sendSuspect(); break;
                    case "hostINIT": hostFinishInit(); break;
                }
            }
        }).start();
    }

    // Listener Responses
    private static void sendSuspect() {
        try {
            output.write((("Blap") + "\r\n").getBytes());
            output.flush();
        } catch (IOException ex) {}
    }

    private static void hostFinishInit() {
        Client.initGame();
    }

    // Getters

    // Setters

}
