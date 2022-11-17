/**
 * 
 */
package gates;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the class NANDgate
 * @author philipp grzywaczyk
 * @version 2, September 2022
 * <p>
 * Test class for the NANDgate
 * This tests the methods which are coded within the class NANDgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.GateTest GateTest}
 *
 */
public class NANDgateTest extends TestCase {

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
	 * Test method for {@link gates.NANDgate#name()}.
	 * Checks that name() returns the correct string
	 */
	@Test
	public void testName() {
		NANDgate nand1 = new NANDgate();
		assertEquals("NAND", nand1.name()); 
	}

	/**
	 * Test method for {@link gates.ANDgate#computeOutput(boolean, boolean)}.
	 * checks that our object NAND behaves such as an NAND Gate should.
	 */
	@Test
	public void testComputeOutput() {
		NANDgate nand1 = new NANDgate();
		assertEquals(false, nand1.computeOutput(true, true));
		assertEquals(true, nand1.computeOutput(false, true));
		assertEquals(true, nand1.computeOutput(true, false));
		assertEquals(true, nand1.computeOutput(false, false));
	}

}