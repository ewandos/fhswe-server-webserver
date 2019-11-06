package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.Impl.PluginSystem.WebPluginManager;
import BIF.SWE1.Impl.httpUtils.HttpRequest;
import BIF.SWE1.Impl.httpUtils.HttpResponse;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class UEB4 {

	public void helloWorld() {

	}

	public Request getRequest(InputStream inputStream) {
		return new HttpRequest(inputStream);
	}

	public Response getResponse() {
		return new HttpResponse();
	}

	public PluginManager getPluginManager() {
		return new WebPluginManager();
	}
}
