package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.pluginSystem.PluginManager;
import BIF.SWE1.httpUtils.Request;
import BIF.SWE1.httpUtils.Response;
import BIF.SWE1.interfaces.IPluginManager;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

public class UEB4 {

	public void helloWorld() {

	}

	public IRequest getRequest(InputStream inputStream) {
		return new Request(inputStream);
	}

	public IResponse getResponse() {
		return new Response();
	}

	public IPluginManager getPluginManager() {
		return new PluginManager();
	}
}
