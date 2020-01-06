package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;
import BIF.SWE1.pluginSystem.DynamicPlugin;

/**
 * Is capable of transforming a String to a lowercase String
 */
public class ToLowerPlugin extends DynamicPlugin {

    /**
     * Creates a new ToLower Plugin and sets the identifier
     */
    public ToLowerPlugin() {
        identifier = "ToLower";
    }

    /**
     * Extracts the text from a POST Requests, transforms it to lowercase and returns it as Http Response
     * @param req Http Request
     * @return Http Response containing the lowercase text
     */
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
