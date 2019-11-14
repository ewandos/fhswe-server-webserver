package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.plugins.TestPlugin;
import BIF.SWE1.httpUtils.Request;
import BIF.SWE1.httpUtils.Response;
import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

public class UEB3 {

	public void helloWorld() {

	}

	public IRequest getRequest(InputStream inputStream) {
		return new Request(inputStream);
	}

	public IResponse getResponse() {
		return new Response();
	}

	public IPlugin getTestPlugin() {
		return new TestPlugin();
	}
}
