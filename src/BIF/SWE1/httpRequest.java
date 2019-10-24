package BIF.SWE1;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * httpRequest takes an InputStream containing a HTTP Request validates it by RegExp and gathers all segments
 */
public class httpRequest implements Request {
    private InputStreamReader stream;
    private String method;
    private Url url;

    private final String pattern = "^(GET|POST|get|post)\\s/((http(s)?://)?(www\\.)?[a-zA-Z0-9]+\\.[a-z]+\\??([a-zA-Z0-9]+=[a-zA-Z0-9]+&?)*)?\\sHTTP/(1\\.0|1\\.1|2)$";
    private final Pattern REGEXP = Pattern.compile(pattern, Pattern.MULTILINE);

    public httpRequest(InputStream stream) {
        this.stream = new InputStreamReader(stream);
    }

    @Override
    public boolean isValid() {
        // validate by RegExp
        if (url == null && method == null) {
            try (BufferedReader reader = new BufferedReader(stream)) {
                String line = reader.readLine();
                Matcher regexp = REGEXP.matcher(line);

                // save method and url
                if (regexp.matches()) {
                    method = line.trim().substring(0, line.indexOf("/")).toUpperCase();
                    url = new WebUrl(line.trim().substring(line.indexOf("/"), line.lastIndexOf(" ")));
                    reader.close();
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
