package BIF.SWE1.httpUtils;

import BIF.SWE1.interfaces.IResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Response implements IResponse {
    private int statusCode;
    private Map<String, String> headers = new HashMap<>();
    private ByteArrayOutputStream contentBytes = new ByteArrayOutputStream();
    private String contentType = "text/plain";
    private String serverHeader = "BIF-BIF.SWE1-Server";

    /**
     * Takes the response line, general headers, custom headers and content and concat them
     * The Response-Class works with ByteArrays, to ensure that no data is lost
     * @return Entire httpResponse, ready for sending
     */
    private byte[] buildResponse() {
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

        // append head- and content OutputStreams
        ByteArrayOutputStream httpResponseBytes = new ByteArrayOutputStream();
        try {
            httpResponseBytes.write(head.toString().getBytes(StandardCharsets.UTF_8));
            httpResponseBytes.write(contentBytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpResponseBytes.toByteArray();
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public int getContentLength() {
        return contentBytes.size();
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
            case 302:
                return "302 REDIRECT";
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
        try {
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            contentBytes.write(bytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setContent(byte[] content) {
        try {
            contentBytes.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContent(InputStream stream) {
        try {
            contentBytes.write(stream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(OutputStream network) {
        if (this.getContentType() != null && this.getContentLength() <= 0)
            throw new IllegalStateException("Trying to send response without content while content type set!");

        // get content as ByteArray
        byte[] httpResponseBytes = buildResponse();
        try {
            network.write(httpResponseBytes);
            network.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
