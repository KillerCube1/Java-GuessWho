import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import Style.MainMenuButton;


/**
 * The MainMenu class represents the main menu of the Guess Who game.
 * It provides options for singleplayer, multiplayer, settings, about, and exit.
 */
public class MainMenu {

    private static JFrame frame;
    private static JFrame settingsFrame;

    private static JFrame aboutFrame;
    private static BackgroundMusic backgroundMusic;

    private static boolean LAN = false;
    private static String IP; // Only in use if LAN is used

    // Settings variables
    private static int volume;

    /**
     * Displays the main menu of the game.
     */
    public static void mainMenu() {

//        backgroundMusic = new BackgroundMusic(playList(), volume);
//        backgroundMusic.play();

        frame = new JFrame("Main Menu");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Create Main Menu Guess Who Logo
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(Resource.getImageResource("/Images/logo.png")).getScaledInstance(320, 167, Image.SCALE_SMOOTH));
        JLabel logo = new JLabel("");
        logo.setBounds(10, -40, 500, 280);
        logo.setIcon(logoIcon);

        // Create Main Menu Button Panel
        JPanel menuButtons = new JPanel();
        menuButtons.setOpaque(false);
        MainMenuButton button = new MainMenuButton("Singleplayer", 25, 195, 165, 45);
        MainMenuButton button2 = new MainMenuButton("Multiplayer", 25, 250, 165, 45);
        MainMenuButton button3 = new MainMenuButton("Settings", 25, 305, 165, 45);
        MainMenuButton button4 = new MainMenuButton("About", 25, 415, 165, 45);
        MainMenuButton button5 = new MainMenuButton("Exit", 25, 470, 165, 45);
        MainMenuButton button6 = new MainMenuButton("Website", 25, 360, 165, 45);
        menuButtons.add(button);
        menuButtons.add(button2);
        menuButtons.add(button3);
        menuButtons.add(button4);
        menuButtons.add(button5);
        menuButtons.add(button6);

        frame.getContentPane().add(logo);
        frame.getContentPane().add(menuButtons);
        frame.setVisible(true);
        LoadingScreen.stop();

        button.addActionListener(e -> {
            new GuessWhoGame("Single");
            frame.dispose();
        });

        button2.addActionListener(e -> {
            openMultiplayer();
            frame.setVisible(false);
        });

        button3.addActionListener(e -> {
            openSettings();
            frame.setVisible(false);
        });

        button4.addActionListener(e -> {
            openAbout();
            frame.setVisible(false);
        });

        button5.addActionListener(e -> {
            System.exit(0);
        });

        // Add action listener for the new button
        button6.addActionListener(e -> {
            try {
                URI uri = new URI("https://javaguesswho.wolfhunter1043.com/");
                openWebpage(uri);
            } catch (Exception exception) {
                System.out.println("Something wrong with dns");
            }
        });
    }


    /**
     * Opens the multiplayer menu.
     */
    static void openMultiplayer() {
        new ServerListMenu();
    }



    /**
     * Opens the settings menu.
     */
    public static void openSettings() {
        settingsFrame = new JFrame("Settings");
        settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingsFrame.setSize(600, 400);
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(7, 1));
        panel.add(new JLabel("Settings Menu", SwingConstants.CENTER));

        // Volume controls and music buttons
        JButton playButton = new MainMenuButton("Play", 25, 20, 165, 45);
        playButton.addActionListener(e -> backgroundMusic.play());

        JButton stopButton = new MainMenuButton("Stop", 25, 70, 165, 45);
        stopButton.addActionListener(e -> backgroundMusic.stop());

        JButton nextButton = new MainMenuButton("Next Song", 25, 120, 165, 45);
        nextButton.addActionListener(e -> backgroundMusic.playNextSong());

        JButton advancedButton = new MainMenuButton("Advanced", 25, 170, 165, 45);
        advancedButton.addActionListener(e -> {
            settingsFrame.dispose();
            new AdvancedSettings();
        });

        JButton backButton = new MainMenuButton("Back", 25, 220, 165, 45);
        backButton.addActionListener(e -> {
            settingsFrame.dispose();
            frame.setVisible(true);
        });

//        JPanel volumeSlider = getjSlider(backgroundMusic.getVolume());

        panel.add(playButton);
        panel.add(stopButton);
        panel.add(nextButton);
        panel.add(advancedButton);
        panel.add(backButton);
//        panel.add(volumeSlider);

        settingsFrame.getContentPane().add(panel);

        settingsFrame.setVisible(true);
    }


    /**
     * Opens the about menu.
     */

    private static void openAbout() {
        aboutFrame = new JFrame("About");
        aboutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aboutFrame.setSize(600, 420);
        aboutFrame.setLocationRelativeTo(null);
        aboutFrame.setResizable(false);

        // Game information
        JLabel titleLabel = new JLabel("Guess Who Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel descriptionLabel = getjLabel();

        JLabel versionLabel = new JLabel("Version 1.0", SwingConstants.CENTER);
        JLabel developersLabel = new JLabel("Developed by: Rylan and Damien", SwingConstants.CENTER);

        JButton backButton = new MainMenuButton("Back", 210, 330, 165, 45);
        backButton.addActionListener(e -> {
            aboutFrame.dispose();
            frame.setVisible(true);
        });

        // Add components to panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        panel.add(descriptionLabel, gbc);

        gbc.gridy++;
        panel.add(versionLabel, gbc);

        gbc.gridy++;
        panel.add(developersLabel, gbc);

        gbc.gridy++;
        panel.add(backButton, gbc);

        // Add panel to about frame
        aboutFrame.getContentPane().add(panel);
        aboutFrame.setVisible(true);
    }


    /**
     * Retrieves the description label for the about menu.
     *
     * @return The description label.
     */
    private static JLabel getjLabel() {
        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'><br>Guess Who is a classic board game where each player<br>is assigned a mystery character. Using buttons with questions,<br>they try to figure out the other player's mystery character.<br>When they think they know who their opponent's mystery<br>character is, players can make a guess by clicking on the<br>individual image of the suspect. If the guess is wrong, the<br>player has only two more tries!</div></html>", SwingConstants.CENTER);
        descriptionLabel.setPreferredSize(new Dimension(500, 200));
        return descriptionLabel;
    }


    /**
     * Retrieves the volume slider for the settings menu.
     *
     * @return The volume slider.
     */
    private static JPanel getjSlider(int volume) {
        JPanel volumeControl = new JPanel(new GridLayout(2, 1));
        volumeControl.setPreferredSize(new Dimension(165, 45));
        volumeControl.add(new JLabel("Volume:", SwingConstants.CENTER));
        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, volume);
        volumeSlider.addChangeListener(e -> backgroundMusic.setVolume(volumeSlider.getValue()));
        volumeControl.add(volumeSlider);
        return volumeControl;
    }



    /**
     * Provides a playlist of music files for the background music in the main menu.
     */
    static List<File> playList(){
        List<File> playlist = new ArrayList<>();
        playlist.add(Resource.getAudioResource("/Music/Ambient-downtempo-music.wav"));
        LoadingScreen.update("Music1");
        playlist.add(Resource.getAudioResource("/Music/livail-oasis-114751.wav"));
        LoadingScreen.update("Music2");
        playlist.add(Resource.getAudioResource("/Music/ambient-classical-guitar-144998.wav"));
        LoadingScreen.update("Music3");
        playlist.add(Resource.getAudioResource("/Music/better-day-186374.wav"));
        LoadingScreen.update("Music4");
        playlist.add(Resource.getAudioResource("/Music/deep-future-garage-royalty-free-music-163081.wav"));
        LoadingScreen.update("Music5");
        playlist.add(Resource.getAudioResource("/Music/easy-lifestyle-137766.wav"));
        LoadingScreen.update("Music6");
        playlist.add(Resource.getAudioResource("/Music/embrace-12278.wav"));
        LoadingScreen.update("Music7");
        playlist.add(Resource.getAudioResource("/Music/once-in-paris-168895.wav"));
        LoadingScreen.update("Music8");
        playlist.add(Resource.getAudioResource("/Music/watr-fluid-10149.wav"));
        LoadingScreen.update("Music9");
        playlist.add(Resource.getAudioResource("/Music/No_Rest_Or_Endless_Rest_-_Lisofv.wav"));
        LoadingScreen.update("Music10");

        Collections.shuffle(playlist);

        return playlist;
    }

    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Retrieves the main menu frame.
     *
     * @return The main menu frame.
     */
    public static JFrame getFrame() {
        return frame;
    }

    public static void setLANIP(String lanIP) {
        IP = lanIP;
    }

    public static void setLANConnection(boolean lanUse) {
        LAN = lanUse;
    }

    public static String getIP() {
        return IP;
    }

    public static boolean usingLAN() {
        return LAN;
    }

    public static void setVolume(int value) {
        volume = value;
    }
}