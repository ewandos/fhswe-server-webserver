package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.httpUtils.Request;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.pluginSystem.PluginManager;
import BIF.SWE1.plugins.StaticPlugin;

public class UEB5 {

	public void helloWorld() {

	}

	public IRequest getRequest(InputStream inputStream) {
		return new Request(inputStream);
	}

	public PluginManager getPluginManager() {
		return new PluginManager();
	}

	public IPlugin getStaticFilePlugin() {
		return new StaticPlugin();
	}

	public void setStatiFileFolder(String s) {
	}

	public String getStaticFileUrl(String s) {
		return "statics/tmp-static-files/" + s;
	}
}
