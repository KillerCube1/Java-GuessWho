
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 * The Client class represents a client-side connection to a server.
 * It manages communication with the server, including sending and receiving data.
 */
public class Client {

    private static BufferedReader input;
    private static BufferedOutputStream output;
    private static Socket socket;


    /**
     * Constructs a Client object and establishes a connection to the server
     * with the specified IP address and port number.
     *
     * @param IP The IP address of the server.
     * @param Port The port number of the server.
     */
    public Client(String IP, int Port) {
        try {
            System.out.println("Client started!\nConnecting...");
            Client.socket = new Socket(IP, Port);
            Client.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Client.output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
            new ServerListMenu();
        }
    }

    // Client Methods


    /**
     * Sends a request to the server to get the opponent's suspect.
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