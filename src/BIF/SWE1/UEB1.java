package BIF.SWE1;

import BIF.SWE1.imp.httpUtils.Url;
import BIF.SWE1.interfaces.IUrl;

public class UEB1 {

	public IUrl getUrl(String path) {
		return new Url(path);
	}

	public void helloWorld() {
	    System.out.println("Hello World");
	}
}
