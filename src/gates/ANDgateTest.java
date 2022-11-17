/**
 * 
 */
package gates;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the class ANDgate
 * @author philipp grzywaczyk
 * @version 2, September 2022
 * <p>
 * Testclass for the ANDgate
 * This tests the methods which are coded within the class ANDgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.GateTest GateTest}
 *
 */
public class ANDgateTest extends TestCase {

	/**
	 * Set Up Method, uses set up of super class
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Tear Down Method, uses tear down of super class
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link gates.ANDgate#name()}.
	 * Checks that name() returns the correct string
	 */
	@Test
	public void testName() {
		ANDgate and1 = new ANDgate();
		assertEquals("AND", and1.name()); 
	}

	/**
	 * Test method for {@link gates.ANDgate#computeOutput(boolean, boolean)}.
	 * Checks that an AND gate behaves correctly as an AND gate should
	 */
	@Test
	public void testComputeOutput() {
		ANDgate and1 = new ANDgate();
		assertEquals(true, and1.computeOutput(true, true));
		assertEquals(false, and1.computeOutput(false, true));
		assertEquals(false, and1.computeOutput(true, false));
		assertEquals(false, and1.computeOutput(false, false));
	}

}
