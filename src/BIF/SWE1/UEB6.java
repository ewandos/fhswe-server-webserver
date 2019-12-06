package BIF.SWE1;

import java.io.InputStream;
import java.time.LocalDate;

import BIF.SWE1.httpUtils.Request;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IPluginManager;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.pluginSystem.PluginManager;
import BIF.SWE1.plugins.NaviPlugin;
import BIF.SWE1.plugins.TemperaturePlugin;
import BIF.SWE1.plugins.ToLowerPlugin;

public class UEB6 {

	public void helloWorld() {

	}

	public IRequest getRequest(InputStream inputStream) {
		return new Request(inputStream);
	}

	public IPluginManager getPluginManager() {
		return new PluginManager();
	}

	public IPlugin getTemperaturePlugin() {
		return new TemperaturePlugin();
	}

	public IPlugin getNavigationPlugin() {
		return new NaviPlugin();
	}

	public IPlugin getToLowerPlugin() {
		return new ToLowerPlugin();
	}

	public String getTemperatureUrl(LocalDate localDate, LocalDate localDate1) {
		return null;
	}

	public String getTemperatureRestUrl(LocalDate localDate, LocalDate localDate1) {
		return null;
	}

	public String getNaviUrl() {
		return null;
	}

	public String getToLowerUrl() {
		return null;
	}
}
