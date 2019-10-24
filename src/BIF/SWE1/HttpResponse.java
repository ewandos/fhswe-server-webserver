package BIF.SWE1;

import BIF.SWE1.interfaces.Response;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse implements Response {
    private int statusCode;
    private Map<String, String> headers = new HashMap<String, String>();
    private StringBuilder content = new StringBuilder();
    private String contentType;
    private String serverHeader;

    public HttpResponse() {

    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public int getContentLength() {
        return content.length();
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
                return "404 NOT FOUND";
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
        StringBuilder builder = new StringBuilder();
        builder.append("HTTP/1.1 ").append(getStatus()).append("\r\n");
        builder.append("Server: ").append(server).append("\r\n");
        builder.append("Content-Length: ").append(getContentLength()).append("\r\n");
        builder.append("Content-Type: ").append(getContentType()).append("\r\n\r\n");
        serverHeader = builder.toString();
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
        try {
            OutputStreamWriter osw = new OutputStreamWriter(network);
            BufferedWriter bw = new BufferedWriter(osw);
            String httpResponse = serverHeader + content.toString();
            bw.write(httpResponse);
            bw.flush();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
