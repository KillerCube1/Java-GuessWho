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
import java.util.ArrayList;
import java.util.Enumeration;

public class ServerListMenu extends JFrame {

    // Main Server IP (Game Servers)
    private final String listIP = "10.87.128.169";

    private JList<String> serverList;
    private MainMenuButton addButton;
    private MainMenuButton joinButton;
    private MainMenuButton refreshButton;
    private MainMenuButton exitButton;
    private DefaultListModel<String> serverListModel;
    private ArrayList<String> servers;
    private JFrame frame;

    private Socket socket;
    private BufferedReader input;
    private BufferedOutputStream output;

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
        addButton = new MainMenuButton("Host", 25, 20, 165, 45);
        joinButton = new MainMenuButton("Join", 25, 70, 165, 45);
        refreshButton = new MainMenuButton("Refresh", 25, 120, 165, 45);
        exitButton = new MainMenuButton("Back", 25, 170, 165, 45);

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
            socket = new Socket(listIP, 420);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }

        refreshServerList();
    }

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

                if (command.substring(0, 3).equals("FIN")) {
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

    private void joinServer() {
        String selectedServer = serverList.getSelectedValue();
        if (selectedServer != null) {
            try {
                output.write(("CLISJN" + selectedServer + "\r\n").getBytes());
                output.flush();

                String serverGet = input.readLine();
                String[] serverInfo = serverGet.split(",");

                String IP = serverInfo[0];
                int Port = Integer.valueOf(serverInfo[1]);

                frame.dispose();
                
                new Client(IP, Port);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void refreshServerList() {
        try {
            output.write(("CLIGSL" + "\r\n").getBytes());
            output.flush();

            String command = input.readLine();

            if (command.substring(0, 3).equals("SLL")) {
                servers.clear();

                int size = Integer.valueOf(command.substring(3, command.length()));

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

    private void backToMainMenu() {
        frame.dispose();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainMenu.getFrame().setVisible(true);
    }

    private void updateServerList() {
        serverListModel.clear();
        for (String server : servers) {
            serverListModel.addElement(server);
        }
    }
}