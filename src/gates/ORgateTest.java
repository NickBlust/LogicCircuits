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
 * Test class for the ORgate
 * This tests the methods which are coded within the class ORgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.JUnit_Test_Gates.java JUnit_Test_Gates}
 *
 */
public class ORgateTest extends TestCase {

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
	 * Test method for {@link gates.ORgate#name()}.
	 */
	@Test
	public void testName() {
		ORgate or1 = new ORgate();
		assertEquals("OR", or1.name()); 
	}

	/**
	 * Test method for {@link gates.ORgate#computeOutput(boolean, boolean)}.
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