package BIF.SWE1;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebRequest implements Request {
    private InputStreamReader stream;
    private String method;
    private Url url;

    private final String pattern = "^(GET|POST)\\s\\/((http(s)?:\\/\\/)?(www\\.)?[a-zA-Z0-9]+\\.[a-z]+)?\\sHTTP\\/(1\\.0|1\\.1|2)$";
    private final Pattern REGEXP = Pattern.compile(pattern, Pattern.MULTILINE);

    public WebRequest(InputStream stream) {
        this.stream = new InputStreamReader(stream);
    }

    @Override
    public boolean isValid() {
        // validate by RegExp
        if (url == null && method == null) {

            try (BufferedReader reader = new BufferedReader(stream)) {
                String line = reader.readLine();
                Matcher m = REGEXP.matcher(line);
                System.out.println(line);

                if (m.matches()) {
                    method = line.trim().substring(0, line.indexOf("/"));
                    url = new WebUrl(line.trim().substring(line.indexOf("/") + 1, line.lastIndexOf(" ")));
                    return true;
                } else {
                    throw new Exception("RegExp doesn't match!");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
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
