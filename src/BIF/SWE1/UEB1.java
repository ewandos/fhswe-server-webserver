package BIF.SWE1;

import BIF.SWE1.interfaces.Url;

public class UEB1 {

	public Url getUrl(String path) {
		return new WebUrl(path);
	}

	public void helloWorld() {
	    System.out.println("Hello World");
	}
}
