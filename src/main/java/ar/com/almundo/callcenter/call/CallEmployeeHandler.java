package ar.com.almundo.callcenter.call;

import ar.com.almundo.callcenter.employees.Employee;
import ar.com.almundo.callcenter.employees.EmployeePool;

public class CallEmployeeHandler {
	
	private Class<? extends Employee> employeeClass;
	private CallEmployeeHandler nextHandler;
	
	public CallEmployeeHandler(Class<? extends Employee> employeeClass) {
		this.nextHandler = null;
		this.employeeClass = employeeClass;
	}
	
	public void handleCall(Call call) {
		
		Employee employee = EmployeePool.getInstance().getEmployee(employeeClass);
		
		if(employee != null)
			employee.handleCall(call);
		else
			delegateCallHandling(call);
	}
	
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
