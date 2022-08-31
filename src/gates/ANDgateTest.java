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
public class ANDgateTest extends TestCase {

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
	 * Test method for {@link gates.ANDgate#name()}.
	 */
	@Test
	public void testName() {
		ANDgate and1 = new ANDgate();
		assertEquals("AND", and1.name()); 
	}

	/**
	 * Test method for {@link gates.ANDgate#computeOutput(boolean, boolean)}.
	 */
	@Test
	public void testComputeOutput() {
		ANDgate and1 = new ANDgate();
		assertEquals(true, and1.computeOutput(true, true));
		assertEquals(false, and1.computeOutput(false, true));
		assertEquals(false, and1.computeOutput(true, false));
		assertEquals(false, and1.computeOutput(false, false));
	}

}
