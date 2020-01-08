package BIF.SWE1;

import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.pluginHelper.Temperature;
import BIF.SWE1.pluginSystem.PluginManager;
import BIF.SWE1.plugins.ErrorPlugin;
import BIF.SWE1.plugins.ToLowerPlugin;

/*
 * This Unit is responsible for testing the Temperature, ResponseFactory
 */
public class UEB7 {
    public void helloWorld() {
        System.out.println("Hello World!");
    }

    public Temperature getTemperature(double temperature, int seconds, String date) {
        return new Temperature(temperature, seconds, date);
    }

    public IPlugin getErrorPlugin() {
        return new ErrorPlugin();
    }

    public IPlugin getToLowerPlugin() {
        return new ToLowerPlugin();
    }
}
