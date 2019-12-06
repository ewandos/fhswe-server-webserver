package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;
import BIF.SWE1.pluginSystem.DynamicPlugin;

import java.util.Map;

public class ToLowerPlugin extends DynamicPlugin {
    public ToLowerPlugin() {
        identifier = "ToLower";
    }

    @Override
    public IResponse handle(IRequest req) {
        Map<String, String> headers = req.getHeaders();
        System.out.println(headers);

        return ResponseFactory.create("text/html", identifier + " works!");
    }
}
