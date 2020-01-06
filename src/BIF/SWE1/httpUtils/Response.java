package BIF.SWE1.httpUtils;

import BIF.SWE1.interfaces.IResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Response serves as an Object for creating an OutputStream to send it to the client socket
 */
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

    /**
     * @return Returns a writable map of the response headers. Never returns
     *         null.
     */
    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * @return Returns the content length or 0 if no content is set yet.
     */
    @Override
    public int getContentLength() {
        return contentBytes.size();
    }

    /**
     * @return Gets the content type of the response.
     */
    @Override
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType
     *            Sets the content type of the response.
     * @throws IllegalStateException
     *             A specialized implementation may throw a
     *             InvalidOperationException when the content type is set by the
     *             implementation.
     */
    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return Gets the current status code. An Exceptions is thrown, if no status code was set.
     */
    @Override
    public int getStatusCode() {
        if (statusCode == 0)
            throw new IllegalStateException("Status code is not set!");
        else
            return statusCode;
    }

    /**
     * @param statusCode Sets the current status code.
     */
    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return Returns the status code as string. (200 OK)
     */
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

    /**
     * Adds or replaces a response header in the headers map
     *
     * @param header header string
     * @param value value string
     */
    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    /**
     * @return Returns the Server response header. Defaults to "BIF-BIF.SWE1-Server".
     */
    @Override
    public String getServerHeader() {
        return serverHeader;
    }

    /**
     * Sets the Server response header.
     * @param server Server Label
     */
    @Override
    public void setServerHeader(String server) {
        serverHeader = server;
    }

    /**
     * @param content Sets a string content. The content will be encoded in UTF-8.
     */
    @Override
    public void setContent(String content) {
        try {
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            contentBytes.write(bytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param content Sets a byte[] as content.
     */
    @Override
    public void setContent(byte[] content) {
        try {
            contentBytes.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param stream Sets the stream as content.
     */
    @Override
    public void setContent(InputStream stream) {
        try {
            contentBytes.write(stream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param network Sends the response to the network stream.
     */
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
