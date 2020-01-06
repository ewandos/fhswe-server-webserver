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

    /**
     * Creates a new Request Object and validates it instantly
     * @param stream Inputstream of the client socket
     */
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
            System.out.println("LOG " + this.hashCode() + ": " + line);
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

    /**
     * @return Returns true if the request is valid. A request is valid, if
     *         method and url could be parsed. A header is not necessary.
     */
    @Override
    public boolean isValid() {
        return isValid;
    }

    /**
     * @return Returns the request method in UPPERCASE. get -> GET
     */
    @Override
    public String getMethod() {
        return method.trim();
    }

    /**
     * @return Returns a URL object of the request. Never returns null.
     */
    @Override
    public IUrl getUrl() {
        return url;
    }

    /**
     * @return Returns the request header. Never returns null. All keys must be
     *         lower case.
     */
    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * @return Returns the number of header or 0, if no header where found.
     */
    @Override
    public int getHeaderCount() {
        return headersCount;
    }

    /**
     * @return Returns the user agent from the request header
     */
    @Override
    public String getUserAgent() {
        return headers.getOrDefault("user-agent", null);
    }

    /**
     * @return Returns the parsed content length request header. Never returns
     *         null.
     */
    @Override
    public int getContentLength() {
        return content.length();
    }

    /**
     * @return Returns the parsed content type request header. Never returns
     *         null.
     */
    @Override
    public String getContentType() {
        return headers.getOrDefault("content-type", "text/plain");
    }

    /**
     * @return Returns the request content (body) stream or null if there is no
     *         content stream.
     */
    @Override
    public InputStream getContentStream() {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @return Returns the request content (body) as string or null if there is
     *         no content.
     */
    @Override
    public String getContentString() {
        return content;
    }

    /**
     * @return Returns the request content (body) as byte[] or null if there is
     *         no content.
     */
    @Override
    public byte[] getContentBytes() {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)).readAllBytes();
    }
}
