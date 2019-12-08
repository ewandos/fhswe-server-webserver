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
    private Socket clientSocket;

    /**
     * @param clientSocket A Session ist started by giving it a socket that represents the client.
     */
    Session(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Receive the HTTP request and responses it.
     */
    @Override
    public void run() {
        try {
            // create new PluginManager to mount every plugin
            PluginManager plManager = new PluginManager();
            System.out.println("LOG " + this.hashCode() + ": plugins ready");

            // get HTTP-Request by InputStream of the clientSocket
            Request request = new Request(clientSocket.getInputStream());
            System.out.println("LOG " + this.hashCode() + ": received valid request");

            // iterate through mountedPlugins and validate which can handle the response
            IPlugin plugin = plManager.getBestHandlePlugin(request);
            System.out.println("LOG " + this.hashCode() + ": working plugin: " + plugin);

            if (plugin != null) {
                // get response-string of IPlugin and send it
                plugin.handle(request).send(clientSocket.getOutputStream());
                System.out.println("LOG " + this.hashCode() + ": response send");
            }

            clientSocket.close();
            System.out.println("LOG " + this.hashCode() + ": close socket");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}