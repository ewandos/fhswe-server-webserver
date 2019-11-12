package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.imp.pluginSystem.TestPlugin;
import BIF.SWE1.imp.httpUtils.HttpRequest;
import BIF.SWE1.imp.httpUtils.HttpResponse;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

public class UEB3 {

	public void helloWorld() {

	}

	public IRequest getRequest(InputStream inputStream) {
		return new HttpRequest(inputStream);
	}

	public IResponse getResponse() {
		return new HttpResponse();
	}

	public IPlugin getTestPlugin() {
		return new TestPlugin();
	}
}
