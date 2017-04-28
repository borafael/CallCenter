package ar.com.almundo.callcenter;

import ar.com.almundo.callcenter.call.Call;
import ar.com.almundo.callcenter.call.CallEmployeeHandler;
import ar.com.almundo.callcenter.call.CallHandler;
import ar.com.almundo.callcenter.call.NoCallHandlerException;
import ar.com.almundo.callcenter.employees.Director;
import ar.com.almundo.callcenter.employees.Operator;
import ar.com.almundo.callcenter.employees.Supervisor;

/**
 * Singleton, responsable de que las llamadas que reciba el método dispatchCall sean manejadas por los empledaos disponibles de acuerdo
 * a las reglas existentes
 * @author rafael
 *
 */
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
				throw e;
			}
		}
	}

	public CallHandler getHoldHandler() {
		return holdHandler;
	}

	/**
	 * Establece un handler para el evento representado por el caso en el que no haya ningún handler ue pueda atender la llamada y se
	 * llegue al final del chain of responsibilities
	 * @param handler
	 */
	public void setHoldHandler(CallHandler holdHandler) {
		this.holdHandler = holdHandler;
	}
}