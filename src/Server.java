import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class Server {

    private static String ipAddress;

    public Server(ServerMenu UI, String serverName, Socket serverList, BufferedOutputStream output) {
        new Thread(() -> {
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                            ipAddress = addr.getHostAddress();
                            break; // Stop searching if a valid non-loopback IPv4 address is found
                        }
                    }
                    if (ipAddress != null) {
                        break; // Stop searching if a valid IPv4 address is found
                    }
                }
            } catch (Exception ignored) {
            }

            // If no valid IPv4 address is found, set a default IP address
            if (ipAddress == null) {
                ipAddress = "127.0.0.1"; // Default to loopback address
            }

            // Update the UI with the IP address
            UI.updateUI(serverName);

            try (ServerSocket server = new ServerSocket(100)) {
                System.out.println("Server started!");
                Alive keepAlive = new Alive(output, serverName);
                keepAlive.start();
                Socket socket = server.accept();

                keepAlive.disable();
                serverList.close();
                UI.disposeFrame();
                System.out.println("Connected");
                new ServerConnection(socket).start();
            } catch (IOException ex) {
                System.out.println("Error " + ex.getMessage());
            }
        }).start();
    }

}
