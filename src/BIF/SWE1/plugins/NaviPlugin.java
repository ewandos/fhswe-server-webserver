package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;
import BIF.SWE1.pluginSystem.DynamicPlugin;

public class NaviPlugin extends DynamicPlugin {
    public NaviPlugin() {
        identifier = "Navi";
    }

    @Override
    public IResponse handle(IRequest req) {
        return ResponseFactory.create("text/html", identifier + " works!");
    }
}
