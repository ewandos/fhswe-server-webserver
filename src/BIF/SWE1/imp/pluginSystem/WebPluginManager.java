package BIF.SWE1.imp.pluginSystem;

import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IPluginManager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

// Searching for Files - Tutorial
// https://www.tutorialspoint.com/javaexamples/dir_search_file.htm

public class WebPluginManager implements IPluginManager {
    private List<IPlugin> plugins = new ArrayList<IPlugin>();
    private File directory = new File("plugins");
    private static String nameSuffix = "_plugin.class";
    private FilenameFilter filter = (dir, name) -> name.endsWith(nameSuffix);

    public WebPluginManager() {
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    public boolean pluginExists(String pluginName) {
        String[] children = directory.list(filter);
        String searchedPlugin = pluginName + nameSuffix;

        if (children == null) {
            System.out.println("Either dir does not exist or is not a directory");
        } else {
            for (String filename : children) {
                if (filename.equalsIgnoreCase(searchedPlugin)) {
                    System.out.println("Found: " + filename);
                    return true;
                }
            }
        }
        return false;
    }

    private void gatherPlugins() {
    }

    @Override
    public List<IPlugin> getPlugins() {
        return plugins;
    }

    @Override
    public void add(IPlugin plugin) {
        plugins.add(plugin);
    }

    @Override
    public void add(String plugin) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        // searches for jars in given path
    }

    @Override
    public void clear() {
        plugins.clear();
    }
}
