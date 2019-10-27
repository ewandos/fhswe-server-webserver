package BIF.SWE1.unittests.mocks;

import BIF.SWE1.HttpResponseFactory;
import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.Request;
import BIF.SWE1.interfaces.Response;

public class Ueb5TestPlugin implements Plugin {

	@Override
	public float canHandle(Request req) {
		// TODO Auto-generated method stub
		return 0.5f;
	}

	@Override
	public Response handle(Request req) {
		// TODO Auto-generated method stub
		String mainPage = "<!DOCTYPE html>\n<html>\n<head>\n<title>SWE Webserver</title>\n\n</head>\n<body>\n\n<h1>Software Engineering</h1>\n<p>Krass konkrete Website!</p>";
		return HttpResponseFactory.create(200, "EwiServer", "text/plain", mainPage);
	}

}
