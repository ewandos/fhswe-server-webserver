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

            String mainPage = "<!DOCTYPE html>\n<html>\n<head>\n<title>SWE Webserver</title>\n\n</head>\n<body>\n\n<h1>Software Engineering</h1>\n<p>Krass konkrete Website!</p>";
            HttpResponse response = new HttpResponse();
            response.setStatusCode(200);
            response.setContentType("text/html");
            response.setContent(mainPage);
            response.setServerHeader("EwiServer");
            response.send(clientSocket.getOutputStream());
            clientSocket.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

/*
// Receiving the HTTP Request
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;

            // Read Lines until empty (bufferedReader is never null)
            while((line = in.readLine()) != null ) {
                if (line.equalsIgnoreCase( ""))
                    break;
                System.out.println(line);
            }
 */

/*
            OutputStream os = clientSocket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "<!DOCTYPE html>\n<html>\n<head>\n<title>SWE Webserver</title>\n" +
                    "</head>\n<body>\n\n<h1>Software Engineering</h1>\n<p>Krass konkrete Website!</p>\n\n" +
                    "</body>\n</html>";
            bw.write(httpResponse);
            bw.flush();
            */