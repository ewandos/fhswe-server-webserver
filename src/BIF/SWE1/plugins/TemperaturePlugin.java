package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;
import BIF.SWE1.pluginHelper.Sensor;
import BIF.SWE1.pluginSystem.DynamicPlugin;

import java.util.Set;

public class TemperaturePlugin extends DynamicPlugin {
    public TemperaturePlugin() {
        identifier = "Temperature";
    }

    @Override
    public IResponse handle(IRequest req) {

        // only one sensor thread at once!
        boolean sensorExists = false;

        // get all current thread of the JVM
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        for (Thread thread: threadSet) {
            if (thread.getName().equalsIgnoreCase(identifier)) {
                sensorExists = true;
                break;
            }
        }

        // if thread "Temperature" doesn't exists => create one
        if (!sensorExists) {
            Thread th = new Thread(new Sensor());
            th.setName(identifier);
            th.start();
        } else {
            System.out.println("Sensor already activated!");
        }

        IResponse response = ResponseFactory.create("text/html", identifier + " works!");
        return response;
    }
}
