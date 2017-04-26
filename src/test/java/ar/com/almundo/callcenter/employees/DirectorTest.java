package ar.com.almundo.callcenter.employees;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DirectorTest extends TestCase {

	public DirectorTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(DirectorTest.class);
	}

	public void testDirectorCreation() {
		
		Long directorId = 1l;
		String directorName = "Germán Oesterheld";
		
		Director director = new Director(directorId, directorName);
		
		assertEquals(directorId, director.getId());
		assertEquals(directorName, director.getName());		
	}
}
