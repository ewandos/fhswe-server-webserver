package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.imp.pluginSystem.WebPluginManager;
import BIF.SWE1.imp.httpUtils.Request;
import BIF.SWE1.imp.httpUtils.Response;
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
		return new WebPluginManager();
	}
}
