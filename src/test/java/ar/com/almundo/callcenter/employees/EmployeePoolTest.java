package ar.com.almundo.callcenter.employees;

import ar.com.almundo.callcenter.call.Call;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class EmployeePoolTest extends TestCase {
	
	/*
	 * (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		EmployeePool.getInstance().empty();
	}

	public EmployeePoolTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(EmployeePoolTest.class);
	}

	public void testEmployeeCreation() {
		
		EmployeePool.getInstance().addEmployee(new Director(1l, "Vinnie Appice"));
		EmployeePool.getInstance().addEmployee(new Director(2l, "Tim Robbins"));
		EmployeePool.getInstance().addEmployee(new Director(3l, "Walter Sidotti"));
		EmployeePool.getInstance().addEmployee(new Supervisor(4l, "Richard Starkey"));
		EmployeePool.getInstance().addEmployee(new Supervisor(5l, "Bin Valencia"));
		EmployeePool.getInstance().addEmployee(new Supervisor(6l, "Keith Moon"));
		EmployeePool.getInstance().addEmployee(new Operator(7l, "John Dolmayan"));
		EmployeePool.getInstance().addEmployee(new Operator(8l, "Nick Menza"));
		EmployeePool.getInstance().addEmployee(new Operator(9l, "Mike Terrana"));
		EmployeePool.getInstance().addEmployee(new Operator(10l, "Sheila Escobedo"));
		
		assertEquals(3, EmployeePool.getInstance().getDirectorCount());
		assertEquals(3, EmployeePool.getInstance().getSupervisorCount());
		assertEquals(4, EmployeePool.getInstance().getOperatorCount());
	}
	
	public void testEmployeeFetch() {
		
		EmployeePool.getInstance().addEmployee(new Director(1l, "Vinnie Appice"));
		EmployeePool.getInstance().addEmployee(new Supervisor(2l, "Richard Starkey"));
		EmployeePool.getInstance().addEmployee(new Operator(3l, "John Dolmayan"));
		
		Director director = EmployeePool.getInstance().getDirector();
		
		assertEquals(new Long(1l), director.getId());
		assertEquals("Vinnie Appice", director.getName());
		
		Supervisor supervisor = EmployeePool.getInstance().getSupervisor();
		
		assertEquals(new Long(2l), supervisor.getId());
		assertEquals("Richard Starkey", supervisor.getName());
		
		Operator operator = EmployeePool.getInstance().getOperator();
		
		assertEquals(new Long(3l), operator.getId());
		assertEquals("John Dolmayan", operator.getName());
		
		assertEquals(0, EmployeePool.getInstance().getEmployeeCount());
	}
	
	public void testCallQueue() {
		
		final boolean[] callHandled = new boolean[]{false};
		
		Call call = new Call();
		
		Director director = new Director(1l, "Mikkey Dee") {
			
			public void handleCall(Call call) {
				callHandled[0] = true;
				
				super.handleCall(call);
			};
		};
		
		EmployeePool.getInstance().queueCall(call);
		
		assertEquals(1, EmployeePool.getInstance().getQueuedCallsCount());
		assertEquals(0, EmployeePool.getInstance().getEmployeeCount());
		
		EmployeePool.getInstance().addEmployee(director);
		
		assertEquals(0, EmployeePool.getInstance().getQueuedCallsCount());
		assertEquals(0, EmployeePool.getInstance().getEmployeeCount());
		
		assertTrue(callHandled[0]);
		
		call.end();
		
		assertEquals(0, EmployeePool.getInstance().getQueuedCallsCount());
	}
}