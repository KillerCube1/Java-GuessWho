import java.io.BufferedOutputStream;
import java.io.IOException;

public class Alive extends Thread {

    private boolean active = true;
    private BufferedOutputStream output;
    private String name;

    public Alive(BufferedOutputStream output, String serverName) {
        this.output = output;
        this.name = serverName;
    }

    public void disable() {
        this.active = false;
    }

    @Override
    public void run(){
        while (active) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            try {
                output.write(("SERPIN" + name + "\r\n").getBytes());
                output.flush();
            } catch (IOException e) {}
        }
    }

}
