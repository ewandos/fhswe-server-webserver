package BIF.SWE1;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.pluginSystem.PluginLoader;
import BIF.SWE1.pluginSystem.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class Main {
    /**
     * Waits for a connection from a client. If a connection ist established, a thread will be created.
     * @param args No parameters are needed.
     */
    public static void main(String[] args) {
        PluginManager pl = new PluginManager();
        pl.add("TestPlugin");
        pl.add("StaticPlugin");
        System.out.println("Ready");

        /* S E R V E R
        try {
            System.out.println("Listening for connection on port 8080 ....");
            ServerSocket listener = new ServerSocket(8080);
            while(true) {
                // Create a new Thread for Client-Communication
                new Thread(new Session(listener.accept())).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
    }
}
