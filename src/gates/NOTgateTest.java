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
 * @author philipp grzywaczyk
 * Test class for the NOTgate
 * This tests the methods which are coded within the class NOTgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.JUnit_Test_Gates.java JUnit_Test_Gates}
 *
 */
public class NOTgateTest extends TestCase {

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
	 * Test method for {@link gates.NOTgate#name()}.
	 */
	@Test
	public void testName() {
		NOTgate not1 = new NOTgate();
		assertEquals("NOT", not1.name()); 
	}

	/**
	 * Test method for {@link gates.NOTgate#setInput(gates.Gate, gates.GateIndex)}.
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
