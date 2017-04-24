package ar.com.almundo.callcenter;

import java.util.Timer;
import java.util.TimerTask;

import ar.com.almundo.callcenter.call.Call;
import ar.com.almundo.callcenter.employees.EmployeePool;
import ar.com.almundo.callcenter.employees.Operator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DispatcherTest extends TestCase {
	
	public DispatcherTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(DispatcherTest.class);
	}

	public void testSingleCallDispatch() {

		final Call call = new Call();
		
		EmployeePool.getInstance().addEmployee(new Operator(1l, "Ricardo Rojas"));
		
		new Dispatcher().dispatchCall(call);
		
		assertEquals(0, EmployeePool.getInstance().getEmployeeCount());
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				call.end();
				assertEquals(1, EmployeePool.getInstance().getEmployeeCount());
			}
			
		}, 5000);
	
	}
}
