package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.pluginSystem.PluginManager;
import BIF.SWE1.httpUtils.Request;
import BIF.SWE1.httpUtils.Response;

public class UEB4 {

	public void helloWorld() {

	}

	public Request getRequest(InputStream inputStream) {
		return new Request(inputStream);
	}

	public Response getResponse() {
		return new Response();
	}

	public PluginManager getPluginManager() {
		return new PluginManager();
	}
}
