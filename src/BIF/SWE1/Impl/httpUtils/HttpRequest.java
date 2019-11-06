package BIF.SWE1.Impl.httpUtils;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * httpRequest takes an InputStream containing a HTTP Request validates it by RegExp and gathers all segments
 */
public class HttpRequest implements Request {
    private InputStreamReader stream;
    private String method;
    private Url url;
    private Map<String, String> headers = new HashMap<String, String>();
    private int headersCount;
    private String content;

    private final String pattern = "^(GET|POST|get|post)\\s/((http(s)?://)?(www\\.)?/?[a-zA-Z0-9/]+\\.[a-z]+\\??([a-zA-Z0-9/_]+=[a-zA-Z0-9]+&?)*)?\\sHTTP/(1\\.0|1\\.1|2)$";
    private final Pattern REGEXP = Pattern.compile(pattern, Pattern.MULTILINE);

    public HttpRequest(InputStream stream) {
        this.stream = new InputStreamReader(stream);
    }

    /**
     * Takes the first line of a HttpRequest and validates it, by parsing the method and url
     * @param line first line of a HttpRequest
     * @return true/false if request-line is valid or not
     * @throws Exception if there is an error at parsing the line
     */
    private boolean parseRequestLine(String line) throws Exception{
        Matcher regexp = REGEXP.matcher(line);

        if (regexp.matches()) {
            method = line.trim().substring(0, line.indexOf("/")).toUpperCase();
            url = new WebUrl(line.trim().substring(line.indexOf("/"), line.lastIndexOf(" ")));
            System.out.println("Received a valid request!");
        } else {
            throw new Exception("RegExp doesn't match!");
        }
        return true;
    }

    /**
     * Parses all headers of a HttpRequest to a HashedMap
     * @param reader takes a BufferedReader to get all lines
     * @throws Exception if there is an error at parsing the Headers to a HashMap
     */
    private void parseHeaders(BufferedReader reader) throws Exception {
        String line = reader.readLine();
        while(line.length() != 0) {
            String key = line.substring(0, line.indexOf(":")).trim().toLowerCase();
            String value = line.substring(line.indexOf(":") + 2).trim();
            headers.put(key, value);
            line = reader.readLine();
            headersCount++;
        }
    }

    /**
     * Parses the content/body of a httpRequest
     * @param reader takes a BufferedReader to get all lines
     * @throws Exception if there is an Exception at parsing the content
     */
    private void parseContent(BufferedReader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        String line = reader.readLine();
        while(line != null && !line.equals("")) {
            builder.append(line);
            line = reader.readLine();
        }

        content = builder.toString();
    }

    @Override
    public boolean isValid() {
        if (url == null && method == null) {
            try {
                BufferedReader reader = new BufferedReader(stream);
                String line = reader.readLine();

                if (!parseRequestLine(line))
                    return false;

                parseHeaders(reader);
                parseContent(reader);

                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public String getMethod() {
        return method.trim();
    }

    @Override
    public Url getUrl() {
        isValid();
        return url;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public int getHeaderCount() {
        return headersCount;
    }

    @Override
    public String getUserAgent() {
        isValid();
        return headers.getOrDefault("user-agent", null);
    }

    @Override
    public int getContentLength() {
        return content.length();
    }

    @Override
    public String getContentType() {
        return headers.getOrDefault("content-type", "text/plain");
    }

    @Override
    public InputStream getContentStream() {
        return null;
    }

    @Override
    public String getContentString() {
        return content;
    }

    @Override
    public byte[] getContentBytes() {
        return new byte[0];
    }
}