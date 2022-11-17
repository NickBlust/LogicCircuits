/**
 * 
 */
package gates;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the class ORgate
 * @author philipp grzywaczyk
 * @version 2, September 2022
 * <p>
 * Test class for the ORgate
 * This tests the methods which are coded within the class ORgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.GateTest GateTest}
 *
 */
public class ORgateTest extends TestCase {

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
	 * Test method for {@link gates.ORgate#name()}.
	 * Checks that name() returns the correct string
	 */
	@Test
	public void testName() {
		ORgate or1 = new ORgate();
		assertEquals("OR", or1.name()); 
	}

	/**
	 * Test method for {@link gates.ORgate#computeOutput(boolean, boolean)}.
	 * Tests that this object behaves correctly and returns the values like an OR Gate should.
	 */
	@Test
	public void testComputeOutput() {
		ORgate or1 = new ORgate();
		assertEquals(true, or1.computeOutput(true, true));
		assertEquals(true, or1.computeOutput(false, true));
		assertEquals(true, or1.computeOutput(true, false));
		assertEquals(false, or1.computeOutput(false, false));
	}

}