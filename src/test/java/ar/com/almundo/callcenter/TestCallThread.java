package ar.com.almundo.callcenter;

import ar.com.almundo.callcenter.call.Call;

public class TestCallThread extends Thread {
	
	private int callDuration;
	
	public TestCallThread(int callDuration) {
		this.callDuration = callDuration;
	}
	
	@Override
	public void run() {

		final Call call = new Call();
		
		Dispatcher.getInstance().dispatchCall(call);
		
		try {
			Thread.sleep(callDuration);
		} 
		catch (InterruptedException ignore) {}
		
		call.end();
	}
}