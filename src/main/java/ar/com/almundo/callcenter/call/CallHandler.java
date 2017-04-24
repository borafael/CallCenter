package ar.com.almundo.callcenter.call;

import ar.com.almundo.callcenter.employees.Employee;
import ar.com.almundo.callcenter.employees.EmployeePool;

public class CallHandler {
	
	private Class<? extends Employee> employeeClass;
	private CallHandler nextHandler;
	
	public CallHandler(Class<? extends Employee> employeeClass) {
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
	
	public CallHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(CallHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
}
