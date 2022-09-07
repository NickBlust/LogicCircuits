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
 * Test class for the class XORgate
 * @author philipp grzywaczyk
 * @version 2, September 2022
 * <p>
 * Test class for the XORgate
 * This tests the methods which are coded within the class XORgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.GateTest GateTest}
 *
 */
public class XORgateTest extends TestCase {

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
	 * Test method for {@link gates.XORgate#name()}.
	 */
	@Test
	public void testName() {
		XORgate xor1 = new XORgate();
		assertEquals("XOR", xor1.name()); 
	}

	/**
	 * Test method for {@link gates.XORgate#computeOutput(boolean, boolean)}.
	 */
	@Test
	public void testComputeOutput() {
		XORgate xor1 = new XORgate();
		assertEquals(false, xor1.computeOutput(true, true));
		assertEquals(true, xor1.computeOutput(false, true));
		assertEquals(true, xor1.computeOutput(true, false));
		assertEquals(false, xor1.computeOutput(false, false));
	}

}