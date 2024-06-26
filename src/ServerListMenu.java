
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Style.MainMenuButton;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class ServerListMenu extends JFrame {

    private final JList<String> serverList;
    private final DefaultListModel<String> serverListModel;
    private final ArrayList<String> servers;
    private final JFrame frame;

    private Socket socket;
    private BufferedReader input;
    private BufferedOutputStream output;


    /**
     * Represents a server list menu for selecting or hosting game servers.
     */
    public ServerListMenu() {
        frame = new JFrame();
        // Main Server IP (Game Servers)
        String listIP;
        if (MainMenu.usingLAN()) {
            listIP = MainMenu.getIP();
        } else {
            listIP = resolveDomainToIP();
        }

        servers = new ArrayList<>();
        serverListModel = new DefaultListModel<>();

        frame.setTitle("Server Selection Menu");
        frame.setSize(600, 400); // Increased height for the scroll box
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Initialize components
        serverList = new JList<>(serverListModel);
        JPanel panel = getjPanel();

        frame.add(panel);
        frame.setVisible(true);

        try {
            socket = new Socket(listIP, 28040);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            frame.dispose();
            MainMenu.getFrame().setVisible(true);
            JOptionPane.showMessageDialog(null, "There was an issue when connecting to the server");
        }

        refreshServerList();
    }

    private JPanel getjPanel() {
        JScrollPane scrollPane = new JScrollPane(serverList);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        MainMenuButton addButton = new MainMenuButton("Host", 25, 20, 165, 45);
        MainMenuButton joinButton = new MainMenuButton("Join", 25, 70, 165, 45);
        MainMenuButton refreshButton = new MainMenuButton("Refresh", 25, 120, 165, 45);
        MainMenuButton exitButton = new MainMenuButton("Back", 25, 170, 165, 45);

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
        return panel;
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

                final String[] ip = new String[1];
                try {
                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    while (interfaces.hasMoreElements()) {
                        NetworkInterface networkInterface = interfaces.nextElement();
                        if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                            while (addresses.hasMoreElements()) {
                                InetAddress addr = addresses.nextElement();
                                if (addr.getAddress().length == 4) { // Check for IPv4 addresses
                                    ip[0] = addr.getHostAddress();
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {
                }

                output.write((ip[0] + "\r\n").getBytes());
                output.write((100 + "\r\n").getBytes());
                output.flush();

                String command = input.readLine();

                if (command.startsWith("FIN")) {
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
                String[] serverInfo = serverGet.split(",");

                String IP = serverInfo[0];
                int Port = Integer.parseInt(serverInfo[1]);

                System.out.println(IP + ",  " + Port);

                frame.dispose();

                try {
                    System.out.println("Client started!\nConnecting...");
                    Socket client = new Socket(IP, Port);
                    System.out.println("Connected");
                    new Client(client);
                } catch (IOException ex) {
                    System.out.println("Error " + ex.getMessage());
                    new ServerListMenu();
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

            if (command.startsWith("SLL")) {
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
        serverListModel.clear();
        for (String server : servers) {
            serverListModel.addElement(server);
        }
    }

    private String resolveDomainToIP() {
        try {
            InetAddress address = InetAddress.getByName("gameservers.wolfhunter1043.com");
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Error resolving domain to IP: " + e.getMessage());
            return null; // or handle the error accordingly
        }
    }
}
