package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

import java.util.HashMap;
import java.util.Map;

/**
 * WebUrl takes a raw URL as a string and gather every segment like the path or parameters.
 */
public class WebUrl implements Url{
    private String rawUrl;
    private String path;
    private String fragment;
    private int numOfParameters = 0;

    // Collections
    private Map<String, String> parametersMap = new HashMap<String, String>();
    private String[] segments;

    // Flags for Caching
    private boolean pathGathered = false;
    private boolean parametersGathered = false;
    private boolean fragmentsGathered = false;
    private boolean segmentsGathered = false;

    /**
     * @param rawUrl The raw URL as a String.
     */
    public WebUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    @Override
    public String getRawUrl() {
        return rawUrl;
    }

    @Override
    public String getPath() {
        if (rawUrl != null && !pathGathered) {
            path = rawUrl;

            // deleting HTTP Operators
            if (rawUrl.contains("?"))
                path = rawUrl.substring(0, rawUrl.indexOf('?'));
            if (rawUrl.contains("#"))
                path = rawUrl.substring(0, rawUrl.indexOf('#'));
        }

        pathGathered = true;
        return path;
    }

    @Override
    public Map<String, String> getParameter() {
        if (rawUrl != null && !parametersGathered && rawUrl.contains("?")) {
            String rawParameters = rawUrl.substring(rawUrl.indexOf('?') + 1);

            // split rawParameters to parameterSegments
            String[] parameterSegments = rawParameters.split("&");
            numOfParameters = parameterSegments.length;

            // split parameterSegments to key and value
            for (String segment: parameterSegments) {
                String[] segmentVars = segment.split("=");
                // put key and value into a HashMap
                if (segmentVars.length == 2)
                    parametersMap.put(segmentVars[0], segmentVars[1]);
            }
        }

        parametersGathered = true;
        return parametersMap;
    }

    /**
     * @return Returns the number of parameter of the url. Returns 0 if there are no parameter.
     */
    @Override
    public int getParameterCount() {
        if (!parametersGathered)
            getParameter();
        return numOfParameters;
    }

    @Override
    public String[] getSegments() {
        if (rawUrl != null && !segmentsGathered) {
            // trim the first '/'
            String rawSegments = rawUrl.substring(1);
            segments = rawSegments.split("/");
        }

        segmentsGathered = true;
        return segments;
    }

    @Override
    public String getFileName() {
        return null;
    }

    @Override
    public String getExtension() {
        return null;
    }

    @Override
    public String getFragment() {
        if (rawUrl != null && !fragmentsGathered && rawUrl.contains("#")) {
            if (rawUrl.contains("?"))
                fragment = rawUrl.substring(rawUrl.indexOf("#") + 1, rawUrl.indexOf("?"));
            else
                fragment = rawUrl.substring(rawUrl.indexOf("#") + 1);
        }

        fragmentsGathered = true;
        return fragment;
    }
}
