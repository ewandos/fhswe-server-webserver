package BIF.SWE1;

import java.io.*;
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
            // Receiving the HTTP Request
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;

            // Read Lines until empty (bufferedReader is never null)
            while((line = in.readLine()) != null ) {
                if (line.equalsIgnoreCase( ""))
                    break;
                System.out.println(line);
            }

            /*
            httpRequest request = new httpRequest(clientSocket.getInputStream());
            request.isValid();

            Is not possible, cause the clientSocket is closed when the request-object closes the bufferedReader
            https://stackoverflow.com/questions/15769035/java-net-socketexception-socket-closed-tcp-client-server-communication
             */

            // Sending a Response
            OutputStream os = clientSocket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            // String httpResponse = "HTTP/1.1 200 OK\r\nContent-Length: 8\r\nContent-Type: text/plain\r\n\r\n" + "Response";
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "<h1>Response</h1>";
            bw.write(httpResponse);
            bw.flush();
            clientSocket.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}