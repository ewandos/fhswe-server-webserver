package BIF.SWE1;

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

            DynamicPlugin plugin = new DynamicPlugin();
            if(plugin.canHandle(request) > 0) {
                plugin.handle(request).send(clientSocket.getOutputStream());
            } else {
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