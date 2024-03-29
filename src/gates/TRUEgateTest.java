/**
 * 
 */
package gates;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the class TRUEgate
 * @author philipp grzywaczyk
 * @version 2, September 2022
 * <p>
 * Test class for the TRUEgate
 * This tests the methods which are coded within the class TRUEgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.GateTest GateTest}
 *
 */
public class TRUEgateTest extends TestCase {

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
	 * Test method for {@link gates.TRUEgate#name()}.
	 * Checks that name() returns the correct string
	 */
	@Test
	public void testName() {
		TRUEgate g = new TRUEgate();
		assertEquals("TRUE", g.name());
	}

	/**
	 * Test method for {@link gates.TRUEgate#output()}.
	 * Checks that output is always 'true'
	 */
	@Test
	public void testOutput() {
		TRUEgate g = new TRUEgate();
		assertEquals(true, g.output());
	}

	/**
	 * Test method for {@link gates.TRUEgate#getInput(gates.GateIndex)}.
	 * Tests that the get input works correctly, and that a new gate has no input
	 */
	@Test
	public void testGetInput() {
		TRUEgate g = new TRUEgate();
		assertEquals(null, g.getInput(GateIndex.TOP));
		assertEquals(null, g.getInput(GateIndex.BOTTOM));
	}

	/**
	 * Test method for {@link gates.TRUEgate#resetStatus()}.
	 * Tests that reseting of the status works correctly and that at creation and after
	 * reseting it is 'true'
	 */
	@Test
	public void testResetStatus() {
		TRUEgate g = new TRUEgate();
		g.resetStatus();
		assertEquals(Status.TRUE, g.status);
		g.status = Status.FALSE;
		assertEquals(Status.FALSE, g.status);
		g.resetStatus();
		assertEquals(Status.TRUE, g.status);
	}


}
