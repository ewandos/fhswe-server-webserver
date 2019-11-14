package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.httpUtils.Request;
import BIF.SWE1.httpUtils.Response;
import BIF.SWE1.httpUtils.Url;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

public class UEB2 {

	public void helloWorld() {

	}

	public Url getUrl(String path) {
		return new Url(path);
	}

	public IRequest getRequest(InputStream inputStream) {
		return new Request(inputStream);
	}

	public IResponse getResponse() {
		return new Response();
	}
}
