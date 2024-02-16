import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnection extends Thread {

    Socket socket;

    // Game State
    Suspect test;

    public ServerConnection(Socket socket){
        this.socket = socket;

        Deck randDeck = new Deck();
        test = randDeck.getSuspect((int) (Math.random() * randDeck.getTotalCards()));
    }
     
    @Override
    public void run(){
        try(
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream())
        ) {
            String command = input.readLine();
            
            if (command.startsWith("get")) {
                if (command.startsWith("SUS", 3)) {
                    String[] suspectInfo = {
                        test.getAttribute("name"),
                        test.getAttribute("hairColor"),
                        test.getAttribute("bald"),
                        test.getAttribute("eyeColor"),
                        test.getAttribute("gender"),
                        test.getAttribute("hat"),
                        test.getAttribute("glasses"),
                        test.getAttribute("moustache"),
                        test.getAttribute("beard"),
                        test.getAttribute("rosyCheeks")
                    };
                    output.write((String.join(", ", suspectInfo) + "\r\n").getBytes());
                    output.flush();
                }
            }

        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }
     
    }
}