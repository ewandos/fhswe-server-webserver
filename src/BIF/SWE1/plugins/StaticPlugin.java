package BIF.SWE1.plugins;

import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

/**
 * "StaticPlugin" is a full-functional, low priority plugin for loading static files.
 */
public class StaticPlugin implements IPlugin {
    @Override
    public float canHandle(IRequest req) {
        // if it can find the file to the requested path, it returns a 0.5, otherwise a 0.0
        return 0;
    }

    @Override
    public IResponse handle(IRequest req) {
        // the response is the content of the requested file
        return null;
    }
}
