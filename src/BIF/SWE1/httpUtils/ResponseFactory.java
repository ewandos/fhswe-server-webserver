package BIF.SWE1.httpUtils;

/**
 * Can be used for different builds of a HttpResponse,
 * like if you don't set a statusCode it 200 by default
 */
public class ResponseFactory {
    /**
     * @param contentType Type of content, send to the server
     * @param content     Content String to send
     * @return HttpResponse-Object
     */
    public static Response create(String contentType, String content) {
        Response response = new Response();
        response.setContentType(setContentTypeByFileType(contentType));
        response.setStatusCode(200);
        response.setContent(content);

        return response;
    }

    /**
     * @param contentType Type of content, send to the server
     * @param content     Content byte Array to send
     * @return HttpResponse-Object
     */
    public static Response create(String contentType, byte[] content) {
        Response response = new Response();
        response.setContentType(setContentTypeByFileType(contentType));
        response.setStatusCode(200);
        response.setContent(content);
        return response;
    }

    /**
     * @param statusCode  Http Response-Code
     * @param contentType Type of content, send to the server
     * @param content     Content String to send
     * @return HttpResponse-Object
     */
    public static Response create(int statusCode, String contentType, String content) {
        Response response = ResponseFactory.create(contentType, content);
        response.setStatusCode(statusCode);
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
        Response response = ResponseFactory.create(statusCode, contentType, content);
        response.setServerHeader(server);
        return response;
    }

    private static String setContentTypeByFileType(String fileType) {
        switch(fileType) {
            case ".html":
                return "text/html";
            case ".css":
                return "text/css";
            case ".js":
                return "application/javascript";
            case ".ico":
                return "image/x-icon";
            default:
                return fileType;
        }
    }
}
