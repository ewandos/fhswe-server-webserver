package BIF.SWE1;

import BIF.SWE1.Impl.PluginSystem.DynamicPlugin;
import BIF.SWE1.Impl.PluginSystem.TestPlugin;
import BIF.SWE1.Impl.httpUtils.HttpRequest;
import BIF.SWE1.Impl.httpUtils.HttpResponse;
import BIF.SWE1.Impl.httpUtils.HttpResponseFactory;

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
            HttpRequest request = new HttpRequest(clientSocket.getInputStream());
            request.isValid();
            System.out.printf("Plugin?");
            TestPlugin plugin = new TestPlugin();
            if(plugin.canHandle(request) > 0) {
                plugin.handle(request).send(clientSocket.getOutputStream());
            } else {
                System.out.printf("Response?");
                String mainPage = "<!DOCTYPE html>\n<html>\n<head>\n<title>SWE Webserver</title>\n\n</head>\n<body>\n\n<h1>No Plugin found!</h1>\n<p>You tried to use a unknown plugin!</p>";
                HttpResponse response = HttpResponseFactory.create(200, "BIF-BIF.SWE1-Server", "text/html", mainPage);
                response.send(clientSocket.getOutputStream());
            }


            clientSocket.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}