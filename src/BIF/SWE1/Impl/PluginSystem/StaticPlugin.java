package BIF.SWE1.Impl.PluginSystem;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

/**
 * "StaticPlugin" is a full-functional, low priority plugin for loading static files.
 */
public class StaticPlugin implements Plugin {
    @Override
    public float canHandle(Request req) {
        // if it can find the file to the requested path, it returns a 0.5, otherwise a 0.0
        return 0;
    }

    @Override
    public Response handle(Request req) {
        // the response is the content of the requested file
        return null;
    }
}
