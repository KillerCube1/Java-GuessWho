import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BackgroundMusic {

    private final List<File> playlist;
    private int currentSongIndex = 0;
    private Clip clip;
    private FloatControl volumeControl;

    public BackgroundMusic(List<File> playlist) {
        this.playlist = playlist;
        loadSong(currentSongIndex);
    }

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

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    public void setVolume(int volume) {
        if (volumeControl != null) {
            // Convert volume from 0-100 range to decibels
            float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
            volumeControl.setValue(dB);
        }
    }

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
