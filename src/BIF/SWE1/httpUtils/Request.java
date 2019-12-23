package BIF.SWE1.httpUtils;

import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IUrl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * httpRequest takes an InputStream containing a HTTP Request validates it by RegExp and gathers all segments
 */
public class Request implements IRequest {
    private InputStreamReader stream;
    private BufferedReader reader;
    private String method;
    private Url url;
    private Map<String, String> headers = new HashMap<String, String>();
    private int headersCount;
    private String content;
    private boolean isValid;

    private final String pattern = "^(GET|POST|get|post)\\s/?((http(s)?://)?(www\\.)?[a-zA-Z0-9/_\\-]+(\\.[a-z]+)*\\??([a-zA-Z0-9/_]+=[a-zA-Z0-9]+&?)*)?\\sHTTP/(1\\.0|1\\.1|2)$";
    private final Pattern REGEXP = Pattern.compile(pattern, Pattern.MULTILINE);

    public Request(InputStream stream) {
        this.stream = new InputStreamReader(stream);
        try {
            reader = new BufferedReader(this.stream);
            String line = reader.readLine();

            if (validRequestLine(line)) {
                parseHeaders(reader);

                // POST-Requests terminate their body without a "\n"
                if (getMethod().equalsIgnoreCase("post"))
                    // POST-Requests need to be read by char
                    parsePostContent(reader);
                else if (getMethod().equalsIgnoreCase("post"))
                    // GET-Requests can be read by line
                    parseGetContent(reader);

                isValid = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Takes the first line of a HttpRequest and validates it, by parsing the method and url
     *
     * @param line first line of a HttpRequest
     * @return true/false if request-line is valid or not
     * @throws Exception if there is an error at parsing the line
     */
    private boolean validRequestLine(String line) throws Exception {
        Matcher regexp = REGEXP.matcher(line);

        if (regexp.matches()) {
            method = line.trim().substring(0, line.indexOf("/")).toUpperCase();
            url = new Url(line.trim().substring(line.indexOf("/"), line.lastIndexOf(" ")));
            //System.out.println("Received a valid request!");
        } else {
            System.out.println(line);
            throw new Exception("RegExp doesn't match!");
        }
        return true;
    }

    private void parseHeaders(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        while (line.length() != 0) {
            String key = line.substring(0, line.indexOf(":")).trim().toLowerCase();
            String value = line.substring(line.indexOf(":") + 2).trim();
            headers.put(key, value);
            line = reader.readLine();
            headersCount++;
        }
    }

    private void parsePostContent(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int i = 0;

        // ready() checks if something is ready to be read (works with nextChar and nChars fields)
        while (reader.ready() && (i = reader.read()) != -1) {
            builder.append((char) i);
        }

        content = builder.toString();
    }

    private void parseGetContent(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = "";

        // ready() checks if something is ready to be read (works with nextChar and nChars fields)
        if (reader.ready())
            line = reader.readLine();

        while (line != null && !line.equals("")) {
            builder.append(line);
            line = reader.readLine();
        }

        content = builder.toString();
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public String getMethod() {
        return method.trim();
    }

    @Override
    public IUrl getUrl() {
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
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String getContentString() {
        return content;
    }

    @Override
    public byte[] getContentBytes() {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)).readAllBytes();
    }
}
