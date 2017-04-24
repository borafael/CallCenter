package ar.com.almundo.callcenter;

import ar.com.almundo.callcenter.call.Call;
import ar.com.almundo.callcenter.employees.Director;
import ar.com.almundo.callcenter.employees.EmployeePool;
import ar.com.almundo.callcenter.employees.Operator;
import ar.com.almundo.callcenter.employees.Supervisor;

public class CallCenter {

	public static void main(String[] args) {
		EmployeePool.getInstance().addEmployee(new Director(1l, "Roberto"));
		EmployeePool.getInstance().addEmployee(new Supervisor(1l, "Alberto"));
		EmployeePool.getInstance().addEmployee(new Operator(1l, "Juan"));
		
		Dispatcher dispatcher = new Dispatcher();
		
		Call call = new Call();
		dispatcher.dispatchCall(call);
		dispatcher.dispatchCall(new Call());
		dispatcher.dispatchCall(new Call());
		call.end();
		dispatcher.dispatchCall(new Call());
		dispatcher.dispatchCall(new Call());
		dispatcher.dispatchCall(new Call());
	}
}
