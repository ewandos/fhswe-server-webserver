package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;
import BIF.SWE1.interfaces.Url;

public class UEB2 {

	public void helloWorld() {

	}

	public Url getUrl(String path) {
		return new WebUrl(path);
	}

	public Request getRequest(InputStream inputStream) {
		return new httpRequest(inputStream);
	}

	public Response getResponse() {
		return new httpResponse();
	}
}
