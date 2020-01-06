package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

/**
 * The Error plugin is responsible to return a 404 page if no other plugin can handle the request.
 */
public class ErrorPlugin implements IPlugin {

    /**
     * Returns always 0.1f to serve as last option if no other plugin can handle the request
     * @param req Http Request
     * @return 0.1f
     */
    @Override
    public float canHandle(IRequest req) {
        return 0.1f;
    }

    /**
     * Returns a 404 Page as HTML
     * @param req Http Request
     * @return Response with HTML Content
     */
    @Override
    public IResponse handle(IRequest req) {
        String errorPage = "<!DOCTYPE html>\n<html>\n<head>\n<title>SWE Webserver</title>\n\n</head>\n<body>\n\n<h1>404</h1>\n<p>No plugin or static file found.</p>";
        return ResponseFactory.create(404, "BIF-BIF.SWE1-Server", "text/html", errorPage);
    }
}
