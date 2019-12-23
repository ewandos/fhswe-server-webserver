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
        // in post requests the parameters can be found in the content of the request
        String postData = req.getContentString();

        if (postData.isEmpty())
            postData = "Error: The ToLower Plugin needs to be accessed by a Http-Post-Request!";
        else
            postData = postData.substring(postData.indexOf("=") + 1).toLowerCase();

        return ResponseFactory.create("text/html", postData);
    }
}
