package BIF.SWE1.Impl.PluginSystem;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class WebPluginManager implements PluginManager {
    List<Plugin> plugins = new ArrayList<Plugin>();

    @Override
    public List<Plugin> getPlugins() {
        return null;
    }

    @Override
    public void add(Plugin plugin) {

    }

    @Override
    public void add(String plugin) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

    }

    @Override
    public void clear() {

    }
}
