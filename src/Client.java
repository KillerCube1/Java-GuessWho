
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Client {

    private static BufferedReader input;
    private static BufferedOutputStream output;
    private static Socket socket;

    public Client(String IP, int Port) {
        try {
            socket = new Socket(IP, Port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
            new ServerListMenu();
        }
    }

    // Client Methods

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