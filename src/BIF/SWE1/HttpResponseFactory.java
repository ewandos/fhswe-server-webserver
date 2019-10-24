package BIF.SWE1;

public class HttpResponseFactory {
     public HttpResponseFactory() {
    }

    public static HttpResponse createHttpResponse(int statusCode, String server, String contentType, String content) {
        HttpResponse response = new HttpResponse();
        response.setStatusCode(statusCode);
        response.setServerHeader(server);
        response.setContentType(contentType);
        response.setContent(content);
        return response;
    }
}
