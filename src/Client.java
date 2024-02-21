
import java.io.IOException;
import java.net.Socket;


/**
 * The Client class represents a client-side connection to a server.
 * It manages communication with the server, including sending and receiving data.
 */
public class Client {

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
            System.out.println("Connected");
            new Multiplayer(socket, false);
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
            new ServerListMenu();
        }
    }

}