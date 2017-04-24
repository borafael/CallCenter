package ar.com.almundo.callcenter.employees;

import ar.com.almundo.callcenter.Call;

public abstract class Employee {
	
	private Long id;
	private String name;
	
	public Employee(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public abstract void atender(Call llamada);
	
	@Override
	public boolean equals(Object object) {
		
		if(!(object instanceof Employee))
			return false;
		
		Employee employee = (Employee)object;
		
		return id != null && id.equals(employee.getId());
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}
}