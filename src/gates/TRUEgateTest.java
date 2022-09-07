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
 * Test class for the TRUEgate
 * This tests the methods which are coded within the class TRUEgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.JUnit_Test_Gates.java JUnit_Test_Gates}
 *
 */
public class TRUEgateTest extends TestCase {

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
	 * Test method for {@link gates.TRUEgate#name()}.
	 */
	@Test
	public void testName() {
		TRUEgate g = new TRUEgate();
		assertEquals("TRUE", g.name());
	}

	/**
	 * Test method for {@link gates.TRUEgate#output()}.
	 */
	@Test
	public void testOutput() {
		TRUEgate g = new TRUEgate();
		assertEquals(true, g.output());
	}

	/**
	 * Test method for {@link gates.TRUEgate#getInput(gates.GateIndex)}.
	 */
	@Test
	public void testGetInput() {
		TRUEgate g = new TRUEgate();
		assertEquals(null, g.getInput(GateIndex.TOP));
		assertEquals(null, g.getInput(GateIndex.BOTTOM));
	}

	/**
	 * Test method for {@link gates.TRUEgate#resetStatus()}.
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
