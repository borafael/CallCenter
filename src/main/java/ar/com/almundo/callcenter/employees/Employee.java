package ar.com.almundo.callcenter.employees;

import ar.com.almundo.callcenter.call.Call;

public abstract class Employee {
	
	private Long id;
	private String name;
	
	public Employee(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public void handleCall(Call call) {

		System.out.println(String.format("%s %s speaking...", getClass().getSimpleName().toLowerCase(), name));
		call.handle(this);
	}
	
	public void onCallEnd() {
		System.out.println(String.format("%s %s ending call...", getClass().getSimpleName().toLowerCase(), name));
		EmployeePool.getInstance().addEmployee(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		
		if(!(object instanceof Employee))
			return false;
		
		Employee employee = (Employee)object;
		
		return id != null && id.equals(employee.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}
}