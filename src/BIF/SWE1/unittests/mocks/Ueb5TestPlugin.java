package BIF.SWE1.unittests.mocks;

import BIF.SWE1.interfaces.IPlugin;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;

public class Ueb5TestPlugin implements IPlugin {

	@Override
	public float canHandle(IRequest req) {
		return 0;
	}

	@Override
	public IResponse handle(IRequest req) {
		return null;
	}

}
