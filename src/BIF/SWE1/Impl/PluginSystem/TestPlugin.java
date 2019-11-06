package BIF.SWE1.Impl.PluginSystem;

import BIF.SWE1.Impl.httpUtils.HttpResponseFactory;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class TestPlugin extends DynamicPlugin {
    public TestPlugin() {
        this.identifier = "test";
    }

    @Override
    public Response handle(Request req) {
        String mainPage = "<!DOCTYPE html>\n<html>\n<head>\n<title>SWE Webserver</title>\n\n</head>\n<body>\n\n<h1>Test Plugin</h1>\n<p>This is a specific dynamic TestPlugin!</p>";
        return HttpResponseFactory.create(200, "BIF-BIF.SWE1-Server", "text/html", mainPage);
    }
}
