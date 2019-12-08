package BIF.SWE1.httpUtils;

/**
 * Can be used for different builds of a HttpResponse,
 * like if you don't set a statusCode it 200 by default
 */
public class ResponseFactory {
    /**
     * Default StatusCode and Server
     *
     * @param contentType Type of content, send to the server
     * @param content     Content String to send
     * @return HttpResponse-Object
     */
    public static Response create(String contentType, String content) {
        Response response = new Response();
        switch(contentType) {
            case ".html":
                response.setContentType("text/html");
                break;
            case ".css":
                response.setContentType("text/css");
                break;
            case ".js":
                response.setContentType("text/javascript");
                break;
            case ".ico":
                response.setContentType("image/x-icon");
                break;
            default:
                response.setContentType(contentType);
                break;
        }

        response.setStatusCode(200);
        response.setContent(content);

        return response;
    }

    /**
     * @param statusCode  Http Response-Code
     * @param server      Name of the Server
     * @param contentType Type of content, send to the server
     * @param content     Content String to send
     * @return HttpResponse-Object
     */
    public static Response create(int statusCode, String server, String contentType, String content) {
        Response response = ResponseFactory.create(contentType, content);
        response.setStatusCode(statusCode);
        response.setServerHeader(server);
        return response;
    }
}
