package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;
import BIF.SWE1.pluginSystem.DynamicPlugin;

/**
 * Is capable of searching for city names based on an entered street name
 */
public class NaviPlugin extends DynamicPlugin {

    /**
     * Creates a new Navi Plugin and sets the identifier
     */
    public NaviPlugin() {
        identifier = "Navi";
    }

    /**
     * WIP
     * @param req Http Request
     * @return Http Response
     */
    @Override
    public IResponse handle(IRequest req) {
        return ResponseFactory.create("text/html", identifier + " works!");
    }
}
