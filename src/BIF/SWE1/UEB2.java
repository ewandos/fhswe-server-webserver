package BIF.SWE1;

import java.io.InputStream;

import BIF.SWE1.imp.httpUtils.HttpRequest;
import BIF.SWE1.imp.httpUtils.HttpResponse;
import BIF.SWE1.imp.httpUtils.WebUrl;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

public class UEB2 {

	public void helloWorld() {

	}

	public WebUrl getUrl(String path) {
		return new WebUrl(path);
	}

	public IRequest getRequest(InputStream inputStream) {
		return new HttpRequest(inputStream);
	}

	public IResponse getResponse() {
		return new HttpResponse();
	}
}
