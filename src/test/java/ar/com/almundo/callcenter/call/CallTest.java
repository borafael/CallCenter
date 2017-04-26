package ar.com.almundo.callcenter.call;

import ar.com.almundo.callcenter.employees.Employee;
import ar.com.almundo.callcenter.employees.Operator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CallTest extends TestCase {

	public CallTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(CallTest.class);
	}

	public void testCallEndHook() {
		
		final boolean[] callHandled = new boolean[]{false};			
		
		Call call = new Call();
		
		Employee employee = new Operator(1l, "John Lennon") {
			
			@Override
			public void onCallEnd() {
				callHandled[0] = true;
			}
		};
		
		call.handle(employee);
		call.end();
		
		assertTrue(callHandled[0]);
	}
	
	public void testCallHandling() {
		
		Call call = new Call();
		
		Employee employee = new Operator(1l, "George Harrison");
		
		call.handle(employee);
		
		assertEquals(employee, call.getEmployee());
	}
}
