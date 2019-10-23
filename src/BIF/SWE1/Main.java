package BIF.SWE1;
import java.net.ServerSocket;

public class Main {
    /**
     * Waits for a connection from a client. If a connection ist established, a thread will be created.
     * @param args No parameters are needed.
     */
    public static void main(String[] args) {
        try {
            System.out.println("Listening for connection on port 8080 ....");
            ServerSocket listener = new ServerSocket(8080);
            while(true) {
                /* Create a new Thread for Client-Communication */
                new Thread(new Session(listener.accept())).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
