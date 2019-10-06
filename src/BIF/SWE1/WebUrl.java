package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

import java.util.HashMap;
import java.util.Map;

public class WebUrl implements Url{
    private String rawUrl;
    private String path;
    private int numOfParameters = 0;
    private Map<String, String> parametersMap = new HashMap<String, String>();

    public WebUrl(String rawUrl) {
        if (rawUrl != null) {
            this.rawUrl = rawUrl;

            // if url contains parameters
            if (rawUrl.contains("?")) {
                this.path = rawUrl.substring(0, rawUrl.indexOf('?'));
                String rawParameters = rawUrl.substring(rawUrl.indexOf('?') + 1);
                gatherParameters(rawParameters);
            } else {
                this.path = rawUrl;
            }
        }
    }

    private void gatherParameters(String rawParameters) {
        // split rawParameters to segments
        String[] parameterSegments = rawParameters.split("&");
        numOfParameters = parameterSegments.length;

        // split segments to key and value
        for (String segment: parameterSegments) {
            String[] segmentVars = segment.split("=");
            // put key and value into a HashMap
            if (segmentVars.length == 2)
                this.parametersMap.put(segmentVars[0], segmentVars[1]);
        }
    }

    @Override
    public String getRawUrl() {
        return rawUrl;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Map<String, String> getParameter() {
        return parametersMap;
    }

    @Override
    public int getParameterCount() {
        return numOfParameters;
    }

    @Override
    public String[] getSegments() {
        return new String[0];
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
        return null;
    }
}
