package BIF.SWE1;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class WebRequest implements Request {
    private InputStreamReader stream;
    String method;
    WebUrl url;

    public WebRequest(InputStream stream) {
        this.stream = new InputStreamReader(stream);
    }

    @Override
    public boolean isValid() {
        // validate by RegExp

        // method and url could be parsed
        if (url == null && method == null) {
            String method;
            String url;

            try (BufferedReader reader = new BufferedReader(this.stream)) {
                String line = reader.readLine();
                method = line.trim().substring(0, line.indexOf("/"));
                url = line.trim().substring(line.indexOf("/") + 1, line.lastIndexOf(" "));
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public Url getUrl() {
        return url;
    }

    @Override
    public Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public int getHeaderCount() {
        return 0;
    }

    @Override
    public String getUserAgent() {
        return null;
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
    public InputStream getContentStream() {
        return null;
    }

    @Override
    public String getContentString() {
        return null;
    }

    @Override
    public byte[] getContentBytes() {
        return new byte[0];
    }
}
