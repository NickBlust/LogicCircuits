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
 * @author philipp
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
		fail("Not yet implemented");
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
