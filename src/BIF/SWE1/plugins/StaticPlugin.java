package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        byte[] content = getContent();
        String fileType = requestedPath.substring(requestedPath.lastIndexOf("."));

        // ResponseFactory also handles fileTypes
        return ResponseFactory.create(fileType, content);
    }

    private byte[] getContent() {
        try {
            Path fileLocation = Paths.get(requestedPath);
            return Files.readAllBytes(fileLocation);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new byte[0];
    }
}
