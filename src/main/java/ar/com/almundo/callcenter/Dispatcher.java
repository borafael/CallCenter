package ar.com.almundo.callcenter;

import ar.com.almundo.callcenter.call.Call;
import ar.com.almundo.callcenter.call.CallHandler;
import ar.com.almundo.callcenter.call.NoCallHandlerException;
import ar.com.almundo.callcenter.employees.Director;
import ar.com.almundo.callcenter.employees.Operator;
import ar.com.almundo.callcenter.employees.Supervisor;

public class Dispatcher {
	
	private CallHandler callHandler;
	
	public Dispatcher() {
		
		CallHandler operatorCallHandler = new CallHandler(Operator.class);
		CallHandler supervisorCallHandler = new CallHandler(Supervisor.class);
		CallHandler directorCallHandler = new CallHandler(Director.class);
		
		operatorCallHandler.setNextHandler(supervisorCallHandler);
		supervisorCallHandler.setNextHandler(directorCallHandler);
		
		this.callHandler = operatorCallHandler;
	}
	
	public void dispatchCall(Call call) {
		
		try {
			callHandler.handleCall(call);
		}
		catch(NoCallHandlerException e) {
			System.out.println("No se puede atender la llamada...");
		}
	}
}
