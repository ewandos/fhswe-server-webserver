package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;
import BIF.SWE1.pluginSystem.DynamicPlugin;

public class ToLowerPlugin extends DynamicPlugin {
    public ToLowerPlugin() {
        identifier = "ToLower";
    }

    @Override
    public IResponse handle(IRequest req) {
        String postData = req.getContentString();

        if (postData.isEmpty())
            postData = "undefined";

        String string = postData.substring(postData.indexOf("=") + 1);

        return ResponseFactory.create("text/html", identifier + " works: " + string.toLowerCase());
    }
}
