package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebPlugin implements Plugin {
    private String name = "test";
    // TODO: Scheme for validating if Plugin can handle request or not isn't reasonable
    private String pattern ="(\\/"+ name + "\\/.*)|(\\/.*\\?" + name + "_plugin=true)";
    private final Pattern REGEXP = Pattern.compile(pattern, Pattern.MULTILINE);

    @Override
    public float canHandle(Request req) {
        String requestedPath = req.getUrl().getRawUrl();
        Matcher regexp = REGEXP.matcher(requestedPath);

        if (regexp.matches())
            return 0.5f;
        else
            return 0;
    }

    @Override
    public Response handle(Request req) {
        String mainPage = "<!DOCTYPE html>\n<html>\n<head>\n<title>SWE Webserver</title>\n\n</head>\n<body>\n\n<h1>Web Plugin Access</h1>\n<p>Das Web-Plugin wurde erfolgreich angesprochen!</p>";
        return HttpResponseFactory.create(200, "EwiServer", "text/html", mainPage);
    }
}
