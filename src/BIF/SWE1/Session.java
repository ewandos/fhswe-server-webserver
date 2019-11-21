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
public class Session implements Runnable{
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
        try{
            // create new PluginManager to mount every plugin
            PluginManager plManager = new PluginManager();
            plManager.add("TestPlugin");
            plManager.add("StaticPlugin");
            System.out.println("SESSION: plugins ready");

            // get HTTP-Request by InputStream of the clientSocket
            Request request = new Request(clientSocket.getInputStream());
            request.isValid();
            System.out.println("SESSION: received valid request");

            // iterate through mountedPlugins and validate which can handle the response
            IPlugin plugin = plManager.getBestHandlePlugin(request);
            System.out.println("SESSION: working plugin: " + plugin);

            if(plugin != null) {
                // get response-string of IPlugin and send it
                plugin.handle(request).send(clientSocket.getOutputStream());
                System.out.println("SESSION: response send");
            } else {
                // send 404 Response
                String mainPage = "<!DOCTYPE html>\n<html>\n<head>\n<title>SWE Webserver</title>\n\n</head>\n<body>\n\n<h1>No Plugin found!</h1>\n<p>You tried to use a unknown plugin!</p>";
                Response response = ResponseFactory.create(404, "BIF-BIF.SWE1-Server", "text/html", mainPage);
                response.send(clientSocket.getOutputStream());
                System.out.println("SESSION: 404 send");
            }

            clientSocket.close();
            System.out.println("SESSION: close socket");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}