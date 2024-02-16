import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;

public class Server {

    public Server(ServerMenu UI, String serverName, Socket serverList, BufferedOutputStream output) {
        new Thread(() -> {

            // Update the UI with the server name
            UI.updateUI(serverName);

            try (ServerSocket server = new ServerSocket(100)) {
                
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
