/**
 * 
 */
package gates;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the class NOTgate
 * @author philipp grzywaczyk
 * @version 2, September 2022
 * <p>
 * Test class for the NOTgate
 * This tests the methods which are coded within the class NOTgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.GateTest GateTest}
 *
 */
public class NOTgateTest extends TestCase {

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
	 * Test method for {@link gates.NOTgate#name()}.
	 * Checks that name() returns the correct string
	 */
	@Test
	public void testName() {
		NOTgate not1 = new NOTgate();
		assertEquals("NOT", not1.name()); 
	}

	/**
	 * Test method for {@link gates.NOTgate#setInput(gates.Gate, gates.GateIndex)}.
	 * checks that setting the input at position TOP always works and returns the correct (negated) value,
	 * and that setting something at the BOTTOM Index does not do anything, as it is not a valid
	 * input for this type of Gate.
	 */
	@Test
	public void testSetInput() {
		NOTgate not1 = new NOTgate();
		TRUEgate t = new TRUEgate();
		assertEquals(not1.inputs.get(GateIndex.BOTTOM), null);
		assertEquals(not1.inputs.get(GateIndex.TOP), null);
		assertEquals(not1.output(), true);
		
		not1.setInput(t, null);
		assertEquals(not1.inputs.get(GateIndex.BOTTOM), null);
		assertEquals(not1.inputs.get(GateIndex.TOP), null);
		assertEquals(not1.output(), true);
		
		not1.setInput(t, GateIndex.BOTTOM);
		assertEquals(not1.inputs.get(GateIndex.BOTTOM), t);
		assertEquals(not1.inputs.get(GateIndex.TOP), null);
		assertEquals(not1.output(), true);
		
		not1.setInput(t, GateIndex.TOP);
		assertEquals(not1.inputs.get(GateIndex.TOP), t);
		assertEquals(not1.inputs.get(GateIndex.BOTTOM), null);
		assertEquals(not1.output(), false);
	}

	/**
	 * Test method for {@link gates.NOTgate#computeOutput(boolean, boolean)}.
	 * Tests that the Output of the NOT Gate is correct, always negation of Input in TOP
	 */
	@Test
	public void testComputeOutput() {
		NOTgate not1 = new NOTgate();
		assertEquals(true, not1.computeOutput(false, false)); 
		assertEquals(true, not1.computeOutput(false, true)); 
		assertEquals(false, not1.computeOutput(true, false)); 
		assertEquals(false, not1.computeOutput(true, true)); 
	}

}
