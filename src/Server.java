import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;


/**
 * Represents a server for handling client connections.
 */
public class Server {


    /**
     * Constructs a server instance.
     *
     * @param UI         The user interface associated with the server.
     * @param serverName The name of the server.
     * @param serverList The server list socket for communication.
     * @param output     The output stream for sending data.
     */
    public Server(ServerMenu UI, String serverName, Socket serverList, BufferedOutputStream output) {
        new Thread(() -> {

            // Update the UI with the server name
            UI.updateUI(serverName);

            try (ServerSocket server = new ServerSocket(28040)) {
                
                // Start host server and begin server list communication (keep the server alive)
                System.out.println("Server started!");
                Alive keepAlive = new Alive(output, serverName);
                keepAlive.start();

                // Wait for a client to connect
                System.out.println("Waiting for connection...");
                Socket socket = server.accept();
                System.out.println("Connected");

                // Close server connection with the server list after client connects
                keepAlive.disable();
                serverList.close();
                UI.disposeFrame();
                try {
                output.write(("SERDEL" + serverName + "\r\n").getBytes());
                output.flush();
                } catch (IOException e) {}

                // Start server - client direct connection
                new ServerConnection(socket).start();

            } catch (IOException ex) {}

        }).start();
    }

}
