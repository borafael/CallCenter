package ar.com.almundo.callcenter.employees;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeePool {
	
	private static EmployeePool instance = new EmployeePool();
	
	private Map<Class<? extends Employee>, List<Employee>> employees;
	
	private EmployeePool() {
		employees = new ConcurrentHashMap<Class<? extends Employee>, List<Employee>>();
	}
	
	public void addEmployee(Employee employee) {
		
		if(!employees.containsKey(employee.getClass())){
			
			employees.put(employee.getClass(), new ArrayList<Employee>());
		}
		
		employees.get(employee.getClass()).add(employee);
	}
	
	public static EmployeePool getInstance() {
		return instance;
	}
	
	public Employee getEmployee(Class<? extends Employee> employeeClass) {
		
		Employee employee = null;
		
		if(employees.containsKey(employeeClass) && !employees.get(employeeClass).isEmpty()){
			
			employee = employees.get(employeeClass).remove(0);
		}
		
		return employee;
	}

	public Operator getOperator() {
		return (Operator)getEmployee(Operator.class);
	}
	
	public Supervisor getSupervisor() {
		return (Supervisor)getEmployee(Supervisor.class);
	}
	
	public Director getDirector() {
		return (Director)getEmployee(Director.class);
	}
}
