package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

import java.io.*;

/**
 * "StaticPlugin" is a full-functional, low priority plugin for loading static files.
 */
public class StaticPlugin implements IPlugin {
    private final String directory = "statics/";
    private String requestedFile = null;
    private String requestedPath = null;

    @Override
    public float canHandle(IRequest req) {
        String tempFile = req.getUrl().getPath().substring(1);
        String tempPath = directory + tempFile;

        File file = new File(tempPath);

        // if it can find the file to the requested path, it returns a 0.5, otherwise a 0.0
        if (file.isFile() || tempFile.isEmpty()) {
            requestedFile = tempFile;
            requestedPath = tempPath;
            return 0.5f;
        } else {
            return 0.0f;
        }
    }

    @Override
    public IResponse handle(IRequest req) {
        // if root-directory is requested load the index.html
        if (requestedFile.isEmpty())
            requestedPath = directory + "index.html";
        else
            requestedPath = directory + requestedFile;

        // the response is the content of the requested file
        String content = getContent();
        String fileType = requestedPath.substring(requestedPath.lastIndexOf("."));

        // ResponseFactory also handles fileTypes
        return ResponseFactory.create(fileType, content);
    }

    private String getContent() {
        // reference one line at a time
        String line;
        StringBuilder builder = new StringBuilder();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(requestedPath);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + requestedPath + "'");

        } catch (IOException ex) {
            System.out.println("Error reading file '" + requestedPath + "'");
        }

        return builder.toString();
    }
}
