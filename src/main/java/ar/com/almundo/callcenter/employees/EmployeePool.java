package ar.com.almundo.callcenter.employees;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import ar.com.almundo.callcenter.call.Call;
import ar.com.almundo.callcenter.call.CallQueueFullException;

/**
 * Implementación de de singleton con el objetivo de administrar empleados disponibles y llamadas en espera
 * @author rafael
 *
 */
public class EmployeePool {
	
	public static final int MAX_QUEUED_CALLS = 10;
	
	private static EmployeePool instance = new EmployeePool();
	
	private Map<Class<? extends Employee>, List<Employee>> employees;
	private Queue<Call> callsOnHold;
	
	private EmployeePool() {
		employees = new ConcurrentHashMap<Class<? extends Employee>, List<Employee>>();
		callsOnHold = new ConcurrentLinkedQueue<Call>();
	}
	
	public void empty() {
		employees.clear();
	}
	
	public void flushQueuedCalls() {
		callsOnHold.clear();
	}
	
	public void reset() {
		empty();
		flushQueuedCalls();
	}
	
	/**
	 * Agrega un empleado al pool de empleados disponibles para atender una llamada (a menos que haya una llamada en espera, en cuyo
	 * caso el empleado agregado directamente es convocado para atender la llamada)
	 * @param employee
	 */
	public void addEmployee(Employee employee) {
		
		if(!callsOnHold.isEmpty()) {
			employee.handleCall(callsOnHold.poll());
		}
		else {
			if(!employees.containsKey(employee.getClass())){
				
				employees.put(employee.getClass(), new ArrayList<Employee>());
			}
			
			employees.get(employee.getClass()).add(employee);
		}
	}
	
	/**
	 * Encola una llamada en espera para que sea atendida por el próximo empleado en ser agregado al pool
	 * @param call
	 */
	public void queueCall(Call call) {
		
		if(callsOnHold.size() == MAX_QUEUED_CALLS) {
			System.out.println("Call queue is full, can't handle the call...");
			
			throw new CallQueueFullException();
		}
		
		System.out.println("Putting call on hold...");
		this.callsOnHold.add(call);
	}
	
	public int getQueuedCallsCount() {
		return this.callsOnHold.size();
	}
	
	public static EmployeePool getInstance() {
		return instance;
	}
	
	/**
	 * Devuelve un empleado libre de la clase correspondiente
	 * @param clase del empleado
	 * @return empleado
	 */
	public Employee getEmployee(Class<? extends Employee> employeeClass) {
		
		Employee employee = null;
		
		if(employees.containsKey(employeeClass) && !employees.get(employeeClass).isEmpty()){
			
			employee = employees.get(employeeClass).remove(0);
		}
		
		return employee;
	}
	
	/**
	 * Devuelve todos los empleados de la clase correspondiente
	 * @param clase de los empleados
	 * @return empleados
	 */
	public List<Employee> getEmployees(Class<? extends Employee> employeeClass) {
		
		List<Employee> employees = new ArrayList<Employee>();
		
		if(this.employees.containsKey(employeeClass)){
			
			employees.addAll(this.employees.get(employeeClass));
		}
		
		return employees;
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
	
	public int getOperatorCount() {
		return getEmployees(Operator.class).size();
	}
	
	public int getSupervisorCount() {
		return getEmployees(Supervisor.class).size();
	}
	
	public int getDirectorCount() {
		return getEmployees(Director.class).size();
	}
	
	public int getEmployeeCount() {
		return getOperatorCount() + getSupervisorCount() + getDirectorCount();
	}
}
