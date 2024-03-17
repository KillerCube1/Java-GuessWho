
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * The BackgroundMusic class manages background music playback using Java Sound API.
 * It supports playing a playlist of audio files, adjusting volume, stopping,
 * and playing the next song in the playlist.
 */
public class BackgroundMusic {

    private final List<File> playlist;
    private int currentSongIndex = 0;
    private Clip clip;
    private FloatControl volumeControl;
    private int volume;


    /**
     * Constructs a BackgroundMusic object with the given playlist of audio files.
     *
     * @param playlist The list of audio files to be played.
     */
    public BackgroundMusic(List<File> playlist) {
        this.playlist = playlist;
        loadSong(currentSongIndex);
    }


    /**
     * Loads the audio file at the specified index into the Clip for playback.
     *
     * @param index The index of the audio file in the playlist.
     */
    private void loadSong(int index) {
        try {
            File audioFile = playlist.get(index);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Get the volume control from the clip
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ignored) {
        }
    }

    /**
     * Stops the currently playing audio clip.
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Starts playing the audio clip if it's not already playing.
     */
    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }


    /**
     * Sets the volume of the audio clip.
     *
     * @param volume The volume level (0-100).
     */
    public void setVolume(int volume) {
        if (volumeControl != null) {
            // Convert volume from 0-100 range to decibels
            float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
            volumeControl.setValue(dB);
            this.volume = volume;
            Settings.updateVolume(volume);
        }
    }

    /**
     * Gets the volume of the audio clip.
     *
     * @return The volume level (0-100).
     */
    public int getVolume() {
        return volume;
    }


    /**
     * Plays the next song in the playlist.
     * Stops the current song, increments the index, loads the next song, and starts playing it.
     */
    public void playNextSong() {
        stop();
        currentSongIndex++;
        if (currentSongIndex >= playlist.size()) {
            currentSongIndex = 0;
        }
        loadSong(currentSongIndex);
        play();
    }
}
