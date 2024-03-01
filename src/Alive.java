import java.io.BufferedOutputStream;
import java.io.IOException;


/**
 * The Alive class represents a thread that sends periodic "alive" messages to a server.
 * It extends the Thread class and sends messages at regular intervals until disabled.
 */
public class Alive extends Thread {

    private boolean active = true;
    private final BufferedOutputStream output;
    private final String name;


    /**
     * Constructs an Alive object with the given output stream and server name.
     *
     * @param output The BufferedOutputStream to write messages to.
     * @param serverName The name of the server.
     */
    public Alive(BufferedOutputStream output, String serverName) {
        this.output = output;
        this.name = serverName;
    }

    /**
     * Disables the thread by setting the active flag to false.
     */
    public void disable() {
        this.active = false;
    }


    /**
     * The run method of the thread.
     * Sends "alive" messages to the server at regular intervals.
     */
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
