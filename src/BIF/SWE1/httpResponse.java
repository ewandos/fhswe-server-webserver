package BIF.SWE1;

import BIF.SWE1.interfaces.Response;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class httpResponse implements Response {
    private int statusCode;
    private Map<String, String> headers = new HashMap<String, String>();

    public httpResponse() {

    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public void setContentType(String contentType) {

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
        return null;
    }

    @Override
    public void setServerHeader(String server) {

    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public void setContent(byte[] content) {

    }

    @Override
    public void setContent(InputStream stream) {

    }

    @Override
    public void send(OutputStream network) {

    }
}
