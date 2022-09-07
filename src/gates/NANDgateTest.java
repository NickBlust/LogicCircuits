/**
 * 
 */
package gates;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author philipp Grzywaczyk
 * Test class for the NANDgate
 * This tests the methods which are coded within the class NANDgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.GateTest GateTest}
 *
 */
public class NANDgateTest extends TestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link gates.NANDgate#name()}.
	 */
	@Test
	public void testName() {
		NANDgate nand1 = new NANDgate();
		assertEquals("NAND", nand1.name()); 
	}

	/**
	 * Test method for {@link gates.ANDgate#computeOutput(boolean, boolean)}.
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