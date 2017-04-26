package ar.com.almundo.callcenter;

import ar.com.almundo.callcenter.call.Call;
import ar.com.almundo.callcenter.call.CallHandlingException;

public class TestCallThread extends Thread {
	
	private int callDuration;
	
	public TestCallThread(int callDuration) {
		this.callDuration = callDuration;
	}
	
	@Override
	public void run() {

		final Call call = new Call();
		
		try {
			Dispatcher.getInstance().dispatchCall(call);
		}
		catch(CallHandlingException e) {
			System.out.println("La llamada no pudo ser atendida");
		}
		
		try {
			Thread.sleep(callDuration);
		} 
		catch (InterruptedException ignore) {}
		
		call.end();
	}
}