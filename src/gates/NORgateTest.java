/**
 * 
 */
package gates;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the class NORgate
 * @author philipp grzywaczyk
 * @version 2, September 2022
 * <p>
 * Test class for the NORgate
 * This tests the methods which are coded within the class NORgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.GateTest GateTest}
 *
 */
public class NORgateTest extends TestCase {

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
	 * Test method for {@link gates.NORgate#name()}.
	 * Checks that name() returns the correct string
	 */
	@Test
	public void testName() {
		NORgate nor1 = new NORgate();
		assertEquals("NOR", nor1.name()); 
	}

	/**
	 * Test method for {@link gates.NORgate#computeOutput(boolean, boolean)}.
	 * Checks that our object behaves such as a NOR Gate should.
	 */
	@Test
	public void testComputeOutput() {
		NORgate nor1 = new NORgate();
		assertEquals(false, nor1.computeOutput(true, true));
		assertEquals(false, nor1.computeOutput(false, true));
		assertEquals(false, nor1.computeOutput(true, false));
		assertEquals(true, nor1.computeOutput(false, false));
	}

}