package BIF.SWE1.Impl.httpUtils;

/**
 * Can be used for different builds of a HttpResponse,
 * like if you don't set a statusCode it 200 by default
 */
public class HttpResponseFactory {
     public HttpResponseFactory() {
    }

    /**
     * @param statusCode Http Response-Code
     * @param server Name of the Server
     * @param contentType Type of content, send to the server
     * @param content Content String to send
     * @return HttpResponse-Object
     */
    public static HttpResponse create(int statusCode, String server, String contentType, String content) {
        HttpResponse response = new HttpResponse();
        response.setStatusCode(statusCode);
        response.setContent(content);
        response.setContentType(contentType);
        response.setServerHeader(server);
        return response;
    }

    /**
     * Default StatusCode and Server
     * @param contentType Type of content, send to the server
     * @param content Content String to send
     * @return HttpResponse-Object
     */
    public static HttpResponse create(String contentType, String content) {
        HttpResponse response = new HttpResponse();
        response.setContent(content);
        response.setContentType(contentType);
        return response;
    }
}
