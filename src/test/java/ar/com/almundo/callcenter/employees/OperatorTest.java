package ar.com.almundo.callcenter.employees;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class OperatorTest extends TestCase {

	public OperatorTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(OperatorTest.class);
	}

	public void testOperatorCreation() {
		
		Long operatorId = 1l;
		String operatorName = "Solano Lopez";
		
		Operator operator = new Operator(operatorId, operatorName);
		
		assertEquals(operatorId, operator.getId());
		assertEquals(operatorName, operator.getName());		
	}
}
