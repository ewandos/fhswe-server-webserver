package BIF.SWE1.pluginSystem;

import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IPluginManager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;


public class PluginManager implements IPluginManager {
    private List<IPlugin> mountedPlugins = new ArrayList<IPlugin>();
    private List<String> namesOfStagedPlugins;

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
            System.out.println("No plugins found!");
            return null;
        } else {
            for (String name : pluginNames) {
                name = name.substring(0, name.indexOf("."));
                foundPlugins.add(name);
            }
            System.out.println(foundPlugins);
            return foundPlugins;
        }
    }

    @Override
    public List<IPlugin> getPlugins() {
        return mountedPlugins;
    }

    @Override
    public void add(IPlugin plugin) {
        mountedPlugins.add(plugin);
    }

    @Override
    public void add(String plugin) {
        if (namesOfStagedPlugins.contains(plugin)) {
            PluginLoader loader = new PluginLoader();
            try {
                Class cl = loader.findClass(pack + plugin);
                System.out.println(cl);
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
