package BIF.SWE1.httpUtils;

import BIF.SWE1.interfaces.IUrl;

import java.util.HashMap;
import java.util.Map;

/**
 * Serves as data container and parser of an URL
 */
public class Url implements IUrl {
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
    public Url(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    /**
     * @return Returns the raw url.
     */
    @Override
    public String getRawUrl() {
        return rawUrl;
    }

    /**
     * @return Returns the path of the url, without parameter.
     */
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

    /**
     * @return Returns a dictionary with the parameter of the url. Never returns
     *         null.
     */
    @Override
    public Map<String, String> getParameter() {
        if (rawUrl != null && !parametersGathered && rawUrl.contains("?")) {
            String rawParameters = rawUrl.substring(rawUrl.indexOf('?') + 1);

            // split rawParameters to parameterSegments
            String[] parameterSegments = rawParameters.split("&");
            numOfParameters = parameterSegments.length;

            // split parameterSegments to key and value
            for (String segment : parameterSegments) {
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

    /**
     * @return Returns the segments of the url path. A segment is divided by '/'
     *         chars. Never returns null.
     */
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

    /**
     * @return Returns the filename (with extension) of the url path. If the url
     *         contains no filename, a empty string is returned. Never returns
     *         null. A filename is present in the url, if the last segment
     *         contains a name with at least one dot.
     */
    @Override
    public String getFileName() {
        return null;
    }

    /**
     * @return Returns the extension of the url filename, including the leading
     *         dot. If the url contains no filename, a empty string is returned.
     *         Never returns null.
     */
    @Override
    public String getExtension() {
        return null;
    }

    /**
     * @return Returns the url fragment. A fragment is the part after a '#' char
     *         at the end of the url. If the url contains no fragment, a empty
     *         string is returned. Never returns null.
     */
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
