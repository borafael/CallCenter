package ar.com.almundo.callcenter;

import ar.com.almundo.callcenter.call.Call;
import ar.com.almundo.callcenter.call.CallEmployeeHandler;
import ar.com.almundo.callcenter.call.CallHandler;
import ar.com.almundo.callcenter.call.CallHandlingException;
import ar.com.almundo.callcenter.call.NoCallHandlerException;
import ar.com.almundo.callcenter.employees.Director;
import ar.com.almundo.callcenter.employees.Operator;
import ar.com.almundo.callcenter.employees.Supervisor;

public class Dispatcher {
	
	private static Dispatcher instance = new Dispatcher();
	
	private CallEmployeeHandler firstCallEmployeeHandler;
	private CallHandler holdHandler;
	
	private Dispatcher() {
		
		CallEmployeeHandler operatorCallHandler = new CallEmployeeHandler(Operator.class);
		CallEmployeeHandler supervisorCallHandler = new CallEmployeeHandler(Supervisor.class);
		CallEmployeeHandler directorCallHandler = new CallEmployeeHandler(Director.class);
		
		operatorCallHandler.setNextHandler(supervisorCallHandler);
		supervisorCallHandler.setNextHandler(directorCallHandler);
		
		this.firstCallEmployeeHandler = operatorCallHandler;
		this.holdHandler = null;
	}
	
	public static Dispatcher getInstance() {
		return instance;
	}
	
	public void dispatchCall(Call call) {
		
		try {
			firstCallEmployeeHandler.handleCall(call);
		}
		catch(NoCallHandlerException e) {
			
			if(holdHandler != null) {
				holdHandler.handleCall(call);
			}
			else {
				throw new CallHandlingException(e);
			}
		}
	}

	public CallHandler getHoldHandler() {
		return holdHandler;
	}

	public void setHoldHandler(CallHandler holdHandler) {
		this.holdHandler = holdHandler;
	}
}