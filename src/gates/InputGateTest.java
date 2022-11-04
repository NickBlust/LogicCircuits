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
 * Test class for the class InputGate
 * @author philipp grzywaczyk
 * @version 2, September 2022
 * <p>
 * Test class for the InputGate
 * This tests the methods which are coded within the class InputGate for their "isolated"
  * functionality; while general gate methods are tested in {@link gates.GateTest GateTest}
* and methods which are left abstract are again tested where tey're implemented.
 *
 */
public class InputGateTest extends TestCase {

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
	 * Test method for {@link gates.InputGate#output()}.
	 * This tests all the methods which are coded in InputGate, as they are so connected
	 * that we test all in one procedure
	 */
	@Test
	public void testOutput_and_Inputs() {
		InputGate g = new NANDgate();
		Gate t = new TRUEgate();
		Gate f = new FALSEgate();
		
		/**
		 * This tests if a new gate has status uneval., and if calculating
		 * an output without inputs treats them as false
		 */
		assertEquals(g.status, Status.UNEVALUATED);
		assertEquals(g.output(), true);
		assertEquals(g.status, Status.TRUE);
		
		/**
		 * This tests setting the inputs and getting the inputs 
		 */
		g.setInput(f, GateIndex.BOTTOM);
		g.setInput(t, GateIndex.TOP);
		assertEquals(g.inputs.get(GateIndex.BOTTOM), f);
		assertEquals(g.getInput(GateIndex.TOP), t);
		assertEquals(g.bottomInput(), false);
		
		/**
		 * Tests topInput
		 */
		g.setInput(t, GateIndex.BOTTOM);
		assertEquals(g.topInput(), true);
		
		/**
		 * Tests a new calculation of the output with correct inputs
		 */
		assertEquals(g.output(), false);
		assertEquals(g.status, Status.FALSE);
		
		/**
		 * tests resetStatus. after reseting, status should be uneval.
		 */
		g.resetStatus();
		assertEquals(g.status, Status.UNEVALUATED);
		
		/**
		 * Tests once again the output of new correct inputs
		 */
		g.setInput(f, GateIndex.TOP);
		assertEquals(g.getInput(GateIndex.TOP), f);
		assertEquals(g.output(), true);
		assertEquals(g.status, Status.TRUE);		
	}

}
