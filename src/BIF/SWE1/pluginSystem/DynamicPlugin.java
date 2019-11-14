package BIF.SWE1.pluginSystem;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * "DynamicPlugin" is a base-class for all dynamic plugins that print don't return static files.
 * Most dynamic plugins process data of a database and return them to the client.
 * This class is used as base-class because the method "canHandle()" stays always the same and would be
 * redundant if implemented in every plugin.
 */
public class DynamicPlugin implements IPlugin {
    /** The name of the plugin. The plugin will be accessible by this name.*/
    protected String identifier = "dynamic";

    @Override
    public float canHandle(IRequest req) {
        // create regexp pattern
        String pattern ="(\\/"+ identifier + "\\/.*)|(\\/.*\\?" + identifier + "_plugin=true)";
        Pattern REGEXP = Pattern.compile(pattern, Pattern.MULTILINE);

        // get requestedPlugin
        String requestedPath = req.getUrl().getRawUrl();
        Matcher regexp = REGEXP.matcher(requestedPath);

        // if url matches "/dynamic/foo/foo2" or "/foo/foo2?dynamic_plugin=true" it returns 0.9 otherwise 0.0
        if (regexp.matches())
            return 0.9f;
        else
            return 0;
    }

    @Override
    public IResponse handle(IRequest req) {
        /*
        * if someone accidentally creates an instance of DynamicPlugin instead of a
        * specific dynamicPlugin-Implementation it should load a basic response
        */
        String basicResponse = "This is the base plugin for dynamic plugins. Please use a specific implementation!";
        return ResponseFactory.create(200, "BIF-BIF.SWE1-Server", "text/html", basicResponse);
    }
}
