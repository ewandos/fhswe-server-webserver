package BIF.SWE1.pluginSystem;

import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IPluginManager;
import BIF.SWE1.interfaces.IRequest;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;


public class PluginManager implements IPluginManager {
    private List<IPlugin> mountedPlugins = new ArrayList<IPlugin>();
    private List<String> namesOfStagedPlugins;

    // TODO: currently it's not possible to instantiate Class-Files from outside the package-files!
    private static String pack = "BIF.SWE1.plugins.";
    private static String nameSuffix = "Plugin.java";

    private File directory = new File("src" + File.separatorChar + pack.replace('.', File.separatorChar));
    private FilenameFilter filter = (dir, name) -> name.endsWith(nameSuffix);

    public PluginManager() {
        namesOfStagedPlugins = gatherPlugins();
    }

    private List<String> gatherPlugins() {
        List<String> foundPlugins = new ArrayList<String>();
        String[] pluginNames = directory.list(filter);
        if (pluginNames == null) {
            System.out.println("WARNING: No plugins found!");
            return null;
        } else {
            for (String name : pluginNames) {
                name = name.substring(0, name.indexOf("."));
                foundPlugins.add(name);
            }
            return foundPlugins;
        }
    }

    /**
     * Interates through the mounted plugins and evaluates which one can handle the HTTP-Request best.
     * @param request A HTTP-Request
     * @return Plugin that is best at handling the request.
     */
    public IPlugin getBestHandlePlugin(IRequest request) {
        float maxHandleValue = 0.0f;
        IPlugin suitablePlugin = null;
        for(IPlugin plugin : mountedPlugins)
        {
            float handleValue = plugin.canHandle(request);
            if (handleValue > maxHandleValue) {
                maxHandleValue = handleValue;
                suitablePlugin = plugin;
            }
        }
        return suitablePlugin;
    }

    /**
     * Goes through all staged plugins and mounts them.
     */
    public void mountAllPlugins() {
        for (String pluginName: namesOfStagedPlugins) {
            add(pluginName);
        }
    }

    @Override
    public List<IPlugin> getPlugins() {
        gatherPlugins();
        mountAllPlugins();
        return mountedPlugins;
    }

    @Override
    public void add(IPlugin plugin) {
        mountedPlugins.add(plugin);
    }

    @Override
    public void add(String plugin) {
        // check if searched plugin is available
        // TODO: currently case-sensitive
        if (namesOfStagedPlugins.contains(plugin)) {
            PluginLoader loader = new PluginLoader();
            try {
                Class cl = loader.findClass(pack + plugin);
                Object object = cl.getDeclaredConstructor().newInstance();
                mountedPlugins.add((IPlugin) object);
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } else {
            System.out.println("Plugin not found!");
        }
    }

    @Override
    public void clear() {
        mountedPlugins.clear();
    }
}
