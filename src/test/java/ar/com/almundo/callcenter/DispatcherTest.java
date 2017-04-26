package ar.com.almundo.callcenter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ar.com.almundo.callcenter.call.Call;
import ar.com.almundo.callcenter.call.CallHandler;
import ar.com.almundo.callcenter.call.CallHandlingException;
import ar.com.almundo.callcenter.employees.Director;
import ar.com.almundo.callcenter.employees.Employee;
import ar.com.almundo.callcenter.employees.EmployeePool;
import ar.com.almundo.callcenter.employees.Operator;
import ar.com.almundo.callcenter.employees.Supervisor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DispatcherTest extends TestCase {
	
	public static final int MAX_CALL_DURATION = 10000;
	public static final int MIN_CALL_DURATION = 5000;
	
	private Random random;

	public DispatcherTest(String testName) {
		super(testName);
		
		this.random = new Random();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		
		EmployeePool.getInstance().empty();

	}

	public static Test suite() {
		return new TestSuite(DispatcherTest.class);
	}

	public void testSingleCallDispatch() throws InterruptedException {
		
		EmployeePool.getInstance().addEmployee(new Director(1l, "Vinnie Appice"));
		
		Thread thread = new TestCallThread(getRandomCallDuration());
		thread.start();
		thread.join();
	}

	public void testMultipleCallDispatch() throws InterruptedException {
		
		List<Employee> employees = new LinkedList<Employee>();
		
		employees.add(new Director(1l, "Vinnie Appice"));
		employees.add(new Director(2l, "Tim Robbins"));
		employees.add(new Director(3l, "Walter Sidotti"));
		employees.add(new Supervisor(4l, "Richard Starkey"));
		employees.add(new Supervisor(5l, "Bin Valencia"));
		employees.add(new Supervisor(6l, "Keith Moon"));
		employees.add(new Operator(7l, "John Dolmayan"));
		employees.add(new Operator(8l, "Nick Menza"));
		employees.add(new Operator(9l, "Mike Terrana"));
		employees.add(new Operator(10l, "Sheila Escobedo"));
		
		for(Employee employee: employees)
			EmployeePool.getInstance().addEmployee(employee);
		
		Thread[] threads = new Thread[employees.size()];
		
		for(int threadIndex = 0; threadIndex < 10; threadIndex++) {
			threads[threadIndex] = new TestCallThread(getRandomCallDuration());
			threads[threadIndex].start();
		}
		
		for(int threadIndex = 0; threadIndex < 10; threadIndex++) {
			threads[threadIndex].join();
		}
	}
	
	public void testNoInactiveEmployees() {

		try {
			new Dispatcher().dispatchCall(new Call());
			fail();
		}
		catch(CallHandlingException e) {}
	}
	
	public void testHoldHandler() {
		
		final boolean[] holdHandlerCalled = new boolean[]{false};
		
		Dispatcher dispatcher = new Dispatcher();
		
		dispatcher.setHoldHandler(new CallHandler() {

			/*
			 * (non-Javadoc)
			 * @see ar.com.almundo.callcenter.call.CallHandler#handleCall(ar.com.almundo.callcenter.call.Call)
			 */
			@Override
			public void handleCall(Call call) {

				holdHandlerCalled[0] = true;
			}
		});
		
		dispatcher.dispatchCall(new Call());
		
		assertTrue(holdHandlerCalled[0]);
	}
	
	private int getRandomCallDuration() {
		return random.nextInt(MAX_CALL_DURATION - MIN_CALL_DURATION) + MIN_CALL_DURATION;
	}
	
	private class TestCallThread extends Thread {
		
		private int callDuration;
		
		public TestCallThread(int callDuration) {
			this.callDuration = callDuration;
		}
		
		@Override
		public void run() {

			final Call call = new Call();
			
			new Dispatcher().dispatchCall(call);
			
			try {
				Thread.sleep(callDuration);
			} 
			catch (InterruptedException ignore) {}
			
			call.end();
		}
	}
}
