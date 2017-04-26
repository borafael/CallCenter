package ar.com.almundo.callcenter;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ar.com.almundo.callcenter.call.Call;
import ar.com.almundo.callcenter.employees.Director;
import ar.com.almundo.callcenter.employees.EmployeePool;
import ar.com.almundo.callcenter.employees.Operator;
import ar.com.almundo.callcenter.employees.Supervisor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DispatcherTest extends TestCase {
	
	public static final int MAX_CALL_DURATION = 10000;
	public static final int MIN_CALL_DURATION = 5000;
	

	public DispatcherTest(String testName) {
		super(testName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		
		EmployeePool.getInstance().empty();

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
	}

	public static Test suite() {
		return new TestSuite(DispatcherTest.class);
	}

	public void testSingleCallDispatch() {

		final Call call = new Call();

		new Dispatcher().dispatchCall(call);

		Timer timer = new Timer();

		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				call.end();
			}

		}, 5000);

		try {
			Thread.sleep(5000);
		} 
		catch (InterruptedException ignore) {}
	}

	public void testMultipleCallDispatch() {

		Dispatcher dispatcher = new Dispatcher();
		
		int totalDuration = 0;

		for (int i = 0; i < 10; i++) {

			final Call call = new Call();
			
			int callDuration = getRandomCallDuration();
			
			totalDuration += callDuration;

			dispatcher.dispatchCall(call);

			Timer timer = new Timer();

			timer.schedule(new TimerTask() {

				@Override
				public void run() {

					call.end();
				}

			}, callDuration);
		}

		try {
			Thread.sleep(totalDuration);
		} 
		catch (InterruptedException ignore) {}
	}
	
	private int getRandomCallDuration() {
		return new Random().nextInt(MAX_CALL_DURATION - MIN_CALL_DURATION) + MIN_CALL_DURATION;
	}
}
