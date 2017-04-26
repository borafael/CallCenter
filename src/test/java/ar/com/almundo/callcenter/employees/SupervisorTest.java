package ar.com.almundo.callcenter.employees;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SupervisorTest extends TestCase {

	public SupervisorTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(SupervisorTest.class);
	}

	public void testSupervisorCreation() {
		
		Long supervisorId = 1l;
		String supervisorName = "Robin Wood";
		
		Supervisor supervisor = new Supervisor(supervisorId, supervisorName);
		
		assertEquals(supervisorId, supervisor.getId());
		assertEquals(supervisorName, supervisor.getName());		
	}
}
