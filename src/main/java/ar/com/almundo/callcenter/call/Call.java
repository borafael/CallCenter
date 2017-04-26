package ar.com.almundo.callcenter.call;

import ar.com.almundo.callcenter.employees.Employee;

public class Call {
	
	private Employee employee;
	
	public void handle(Employee employee) {
		this.employee = employee;
	}
	
	public void end() {	
		this.employee.onCallEnd();
		this.employee = null;
	}
	
	public Employee getEmployee() {
		return employee;
	}
}
