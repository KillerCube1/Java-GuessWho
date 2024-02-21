import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ServerListMenu extends JFrame {

    // Main Server IP
    private static final String SERVER_IP = "208.109.39.185";

    private JList<String> serverList;
    private JButton addButton;
    private JButton joinButton;
    private JButton refreshButton;
    private JButton exitButton;
    private DefaultListModel<String> serverListModel;
    private ArrayList<String> servers;
    private JFrame frame;

    private Socket socket;
    private BufferedReader input;
    private BufferedOutputStream output;

    /**
     * Represents a server list menu for selecting or hosting game servers.
     */
    public ServerListMenu() {
        frame = new JFrame();
        servers = new ArrayList<>();
        serverListModel = new DefaultListModel<>();

        frame.setTitle("Server Selection Menu");
        frame.setSize(600, 400); // Increased height for the scroll box
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Initialize components
        serverList = new JList<>(serverListModel);
        JScrollPane scrollPane = new JScrollPane(serverList);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        addButton = new JButton("Host");
        joinButton = new JButton("Join");
        refreshButton = new JButton("Refresh");
        exitButton = new JButton("Back");

        // Action Listeners for buttons
        addButton.addActionListener(e -> hostServer());
        joinButton.addActionListener(e -> joinServer());
        refreshButton.addActionListener(e -> refreshServerList());
        exitButton.addActionListener(e -> backToMainMenu());

        buttonPanel.add(addButton);
        buttonPanel.add(joinButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(exitButton);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add spacing
        panel.setLayout(new GridLayout(1, 2));
        panel.add(buttonPanel);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setVisible(true);

        try {
            // Attempt to initialize socket and input/output streams
            socket = new Socket(SERVER_IP, 28040);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedOutputStream(socket.getOutputStream());

            // If initialization successful, refresh the server list
            refreshServerList();
        } catch (IOException ex) {
            // Handle initialization failure
            System.out.println("Error initializing socket: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Method to host a server.
     */
    private void hostServer() {
        String serverName = JOptionPane.showInputDialog("Enter server name:");
        if (serverName != null && !serverName.isEmpty()) {
            try {
                output.write(("SERADD" + "\r\n").getBytes());
                output.write((serverName + "\r\n").getBytes());

                InetAddress localHost = InetAddress.getLocalHost();
                output.write((localHost.getHostAddress() + "\r\n").getBytes());
                output.write(("100\r\n").getBytes());
                output.flush();

                String command = input.readLine();

                if (command != null && command.substring(0, 3).equals("FIN")) {
                    frame.dispose();
                    new Server(new ServerMenu(), serverName, socket, output);
                } else {
                    System.out.println("ERROR");
                }
            } catch (IOException ex) {
                System.out.println("Error " + ex.getMessage());
            }
        }
    }

    /**
     * Method to join a server.
     */
    private void joinServer() {
        String selectedServer = serverList.getSelectedValue();
        if (selectedServer != null) {
            try {
                output.write(("CLISJN" + selectedServer + "\r\n").getBytes());
                output.flush();

                String serverGet = input.readLine();
                if (serverGet != null) {
                    String[] serverInfo = serverGet.split(",");
                    String IP = serverInfo[0];
                    int Port = Integer.parseInt(serverInfo[1]);

                    frame.dispose();
                    new Client(IP, Port);
                } else {
                    System.out.println("No response from server.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to refresh the server list.
     */
    private void refreshServerList() {
        try {
            output.write(("CLIGSL" + "\r\n").getBytes());
            output.flush();

            String command = input.readLine();

            if (command != null && command.startsWith("SLL")) {
                servers.clear();

                int size = Integer.parseInt(command.substring(3));

                for (int i = 0; i < size; i++) {
                    String serverName = input.readLine();
                    servers.add(serverName);
                }

                updateServerList();
            }
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }

    /**
     * Method to return to the main menu.
     */
    private void backToMainMenu() {
        frame.dispose();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainMenu.getFrame().setVisible(true);
    }

    /**
     * Method to update the server list.
     */
    private void updateServerList() {
        SwingUtilities.invokeLater(() -> {
            serverListModel.clear();
            for (String server : servers) {
                serverListModel.addElement(server);
            }
        });
    }

}
