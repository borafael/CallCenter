package ar.com.almundo.callcenter.call;

import ar.com.almundo.callcenter.employees.Employee;
import ar.com.almundo.callcenter.employees.EmployeePool;

public class Call {
	
	private Employee employee;
	
	public void handle(Employee employee) {
		this.employee = employee;
	}
	
	public void end() {
		EmployeePool.getInstance().addEmployee(employee);
	}
}
