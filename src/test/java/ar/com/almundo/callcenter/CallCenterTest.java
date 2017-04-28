package ar.com.almundo.callcenter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ar.com.almundo.callcenter.call.Call;
import ar.com.almundo.callcenter.call.CallHandler;
import ar.com.almundo.callcenter.employees.Director;
import ar.com.almundo.callcenter.employees.Employee;
import ar.com.almundo.callcenter.employees.EmployeePool;
import ar.com.almundo.callcenter.employees.Supervisor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CallCenterTest extends TestCase {
	
	public static final int RAMP_UP_TIME = 10000;
	
	public static final int MAX_CALL_DURATION = 10000;
	public static final int MIN_CALL_DURATION = 5000;
	
	public static final int MAX_TIME_BETWEEN_CALLS = 1000;
	public static final int MIN_TIME_BETWEEN_CALLS = 500;
	
	private Random random;

	public CallCenterTest(String testName) {
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
		
		EmployeePool.getInstance().reset();
		
		List<Employee> employees = new LinkedList<Employee>();
		
		employees.add(new Director(1l, "Vinnie Appice"));
		employees.add(new Director(2l, "Tim Robbins"));
		employees.add(new Director(3l, "Walter Sidotti"));
		employees.add(new Supervisor(4l, "Richard Starkey"));
		employees.add(new Supervisor(5l, "Bin Valencia"));
		
		for(Employee employee: employees)
			EmployeePool.getInstance().addEmployee(employee);

		Dispatcher.getInstance().setHoldHandler(new CallHandler() {
			
			/*
			 * (non-Javadoc)
			 * @see ar.com.almundo.callcenter.call.CallHandler#handleCall(ar.com.almundo.callcenter.call.Call)
			 */
			@Override
			public void handleCall(Call call) {

				call.handle(null);
				EmployeePool.getInstance().queueCall(call);
			}
		});

	}

	public static Test suite() {
		return new TestSuite(CallCenterTest.class);
	}
	
	public void testRampUp() throws InterruptedException {
		
		long startMillis = System.currentTimeMillis();
		
		long endMillis = System.currentTimeMillis();
		
		List<Thread> threads = new LinkedList<Thread>();
		
		while(endMillis - startMillis < RAMP_UP_TIME) {
			
			Thread thread = new TestCallThread(getRandomCallDuration()); 
			
			threads.add(thread);
			thread.start();
			
//			threads.add(thread);
			
			try {
				Thread.sleep(getRandomTimeBetweenCalls());
			}
			catch(InterruptedException ignore){}
			
			endMillis = System.currentTimeMillis();
		}
		
		for(Thread thread: threads)
			thread.join();		
	}

	private int getRandomCallDuration() {
		return random.nextInt(MAX_CALL_DURATION - MIN_CALL_DURATION) + MIN_CALL_DURATION;
	}
	
	private int getRandomTimeBetweenCalls() {
		return random.nextInt(MAX_TIME_BETWEEN_CALLS - MIN_TIME_BETWEEN_CALLS) + MIN_TIME_BETWEEN_CALLS;
	}
}
