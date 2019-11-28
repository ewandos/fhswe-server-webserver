package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.Response;
import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

import java.io.*;

/**
 * "StaticPlugin" is a full-functional, low priority plugin for loading static files.
 */
public class StaticPlugin implements IPlugin {
    @Override
    public float canHandle(IRequest req) {
        // if it can find the file to the requested path, it returns a 0.5, otherwise a 0.0
        String path = req.getUrl().getPath();
        System.out.println("searched path: " + path);
        File file = new File("." + path);

        if(file.isFile() || path.equals(File.separator)) {
            return 0.5f;
        } else {
            return 0.0f;
        }
    }

    @Override
    public IResponse handle(IRequest req) {
        // The name of the file to open.
        String path = req.getUrl().getPath();
        if (path.equals(File.separator))
            path = "index.html";
        else
            path = "." + path;

        // This will reference one line at a time
        String line = null;
        StringBuilder builder = new StringBuilder();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(path);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + path + "'");

        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + path + "'");
        }

        // the response is the content of the requested file
        String content = builder.toString();
        Response response = ResponseFactory.create(200, "BIF-BIF.SWE1-Server", "text/html", content);
        return response;
    }
}
