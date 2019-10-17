package BIF.SWE1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Listening for connection on port 8080 ....");
            ServerSocket listener = new ServerSocket(8080);
            while(true) {
                // Getting the HTTP Request
                Socket clientSocket = listener.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                while((line = in.readLine()) != null ) {
                    if (line.equalsIgnoreCase( ""))
                        break;
                    System.out.println(line);
                }

                // Sending a Response
                OutputStream os = clientSocket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                // String httpResponse = "HTTP/1.1 200 OK\r\nContent-Length: 8\r\nContent-Type: text/plain\r\n\r\n" + "Response";
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "<h1>Response</h1>";
                bw.write(httpResponse);
                bw.flush();
                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
