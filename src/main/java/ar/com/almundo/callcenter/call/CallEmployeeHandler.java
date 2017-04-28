package ar.com.almundo.callcenter.call;

import ar.com.almundo.callcenter.employees.Employee;
import ar.com.almundo.callcenter.employees.EmployeePool;

/**
 * Implementación de la interfaz CallHanlder que intenta obtener un empleado del tipo correspondiente del pool y 
 * hacer que este atienda la llamada, de no ser posible pasa la llamada al siguiente handler y de no haber más
 * handlers arroja una excepción.
 * 
 * La implementación responde a un patrón de chain of responsiblity
 * @author rafael
 *
 */
public class CallEmployeeHandler implements CallHandler {
	
	private Class<? extends Employee> employeeClass;
	private CallEmployeeHandler nextHandler;
	
	public CallEmployeeHandler(Class<? extends Employee> employeeClass) {
		this.nextHandler = null;
		this.employeeClass = employeeClass;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ar.com.almundo.callcenter.call.CallHandler#handleCall(ar.com.almundo.callcenter.call.Call)
	 */
	public void handleCall(Call call) {
		
		Employee employee = EmployeePool.getInstance().getEmployee(employeeClass);
		
		if(employee != null)
			employee.handleCall(call);
		else
			delegateCallHandling(call);
	}
	
	/**
	 * Delega atender la llamada al siguiente handler, arroja una excepción de no haber ninguno
	 * @param llamada a atender
	 */
	private void delegateCallHandling(Call call) {
		
		if(nextHandler != null)
			nextHandler.handleCall(call);
		else
			throw new NoCallHandlerException();
	}
	
	public CallEmployeeHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(CallEmployeeHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
}
