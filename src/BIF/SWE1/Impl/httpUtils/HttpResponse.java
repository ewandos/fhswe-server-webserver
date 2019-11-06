package BIF.SWE1.Impl.httpUtils;

import BIF.SWE1.interfaces.Response;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse implements Response {
    private int statusCode;
    private Map<String, String> headers = new HashMap<String, String>();
    private StringBuilder content = new StringBuilder();
    private String contentType = "text/plain";
    private String serverHeader = "BIF-BIF.SWE1-Server";

    /**
     * Takes the response line, general headers, custom headers and content and concat them
     * @return Entire httpResponse, ready for sending
     */
    private String buildResponse() {
        // append general headers to string
        StringBuilder head = new StringBuilder();
        head.append("HTTP/1.1 ").append(getStatus()).append("\r\n");
        head.append("Server: ").append(serverHeader).append("\r\n");
        head.append("Content-Length: ").append(getContentLength()).append("\r\n");
        head.append("Content-Type: ").append(getContentType()).append("\r\n");

        // append custom headers to string
        for (Map.Entry<String, String> entry : headers.entrySet())
            head.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        head.append("\r\n");

        // concat responseHead and content
        String httpResponse = head.toString() + content.toString();

        return httpResponse;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public int getContentLength() {
        return content.toString().getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public int getStatusCode() {
        if (statusCode == 0)
            throw new IllegalStateException("Status code is not set!");
         else
            return statusCode;
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getStatus() {
        switch (statusCode) {
            case 200:
                return "200 OK";
            case 404:
                return "404 Not Found";
            case 500:
                return "500 INTERNAL SERVER ERROR";
            default:
                return null;
        }
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public String getServerHeader() {
        return serverHeader;
    }

    @Override
    public void setServerHeader(String server) {
        serverHeader = server;
    }

    @Override
    public void setContent(String content) {
        this.content.append(content);
    }

    @Override
    public void setContent(byte[] content) {
        this.content.append(Arrays.toString(content));
    }

    @Override
    public void setContent(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        try {
            while((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void send(OutputStream network) {
        /* Valid for the UnitTest, but throws Error at every other test, which tries to send no content*/
        if (this.getContentType() != null && this.getContentLength() <= 0)
            throw new IllegalStateException("Trying to send response without content while content type set!");


        String httpResponse = buildResponse();

        try {
            OutputStreamWriter osw = new OutputStreamWriter(network);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(httpResponse);
            bw.flush();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
