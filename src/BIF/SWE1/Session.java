package BIF.SWE1;

import BIF.SWE1.httpUtils.Request;
import BIF.SWE1.httpUtils.Response;
import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.pluginSystem.PluginManager;

import java.net.Socket;

/**
 * A Session represents one client-server connection and is used for multithreading.
 */
public class Session implements Runnable {
    private final Socket clientSocket;
    private final PluginManager plManager;

    /**
     * @param clientSocket A Session ist started by giving it a socket that represents the client.
     */
    Session(Socket clientSocket, PluginManager plManager) {
        this.clientSocket = clientSocket;
        this.plManager = plManager;
    }

    /**
     * Receive the HTTP request and responses it.
     */
    @Override
    public void run() {
        try {
            synchronized (plManager) {
                // get HTTP-Request by InputStream of the clientSocket
                System.out.println("LOG " + this.hashCode() + ": received a request.");
                Request request = new Request(clientSocket.getInputStream());

                // iterate through mountedPlugins and validate which can handle the response
                IPlugin plugin = plManager.getBestHandlePlugin(request);
                // System.out.println("LOG " + this.hashCode() + ": working plugin: " + plugin);


                if (plugin != null) {
                    // get response-string of IPlugin and send it
                    plugin.handle(request).send(clientSocket.getOutputStream());
                    System.out.println("LOG " + this.hashCode() + ": response send.");
                }
            }

            clientSocket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}