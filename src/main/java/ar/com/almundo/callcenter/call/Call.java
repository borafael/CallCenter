package ar.com.almundo.callcenter.call;

import ar.com.almundo.callcenter.employees.Employee;

public class Call {
	
	private Employee employee;
	
	private long startMillis;
	private long endMillis;
	
	public void handle(Employee employee) {
		this.employee = employee;
		this.startMillis = System.currentTimeMillis();
	}
	
	public void end() {	
		this.endMillis = System.currentTimeMillis();
		
		if(this.employee != null) {
			this.employee.onCallEnd();
			this.employee = null;
		}
	}
	
	public long getDuration() {
		return this.endMillis - this.startMillis;
	}
	
	public Employee getEmployee() {
		return employee;
	}
}
