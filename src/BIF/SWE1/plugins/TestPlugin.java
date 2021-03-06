package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;
import BIF.SWE1.pluginSystem.DynamicPlugin;

public class TestPlugin extends DynamicPlugin {
    /**
     * Creates a new Test Plugin and sets the identifier
     */
    public TestPlugin() {
        identifier = "test";
    }

    /**
     * Returns a sample Main page
     * @param req Http Request
     * @return Http Response containing a main page
     */
    @Override
    public IResponse handle(IRequest req) {
        String mainPage = "<!DOCTYPE html>\n<html>\n<head>\n<title>SWE Webserver</title>\n\n</head>\n<body>\n\n<h1>Test Plugin</h1>\n<p>This is a specific dynamic TestPlugin!</p>";
        return ResponseFactory.create(200, "BIF-BIF.SWE1-Server", "text/html", mainPage);
    }
}
