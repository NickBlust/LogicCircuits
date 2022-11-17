/**
 * 
 */
package gates;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the class FALSEgate
 * @author philipp grzywaczyk
 * @version 2, September 2022
 * <p>
 * Test class for the FALSEgate
 * This tests the methods which are coded within the class FALSEgate for their "isolated"
 * functionality; while general gate methods are tested in {@link gates.GateTest GateTest}
 *
 */
public class FALSEgateTest extends TestCase {

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
	 * Test method for {@link gates.FALSEgate#name()}.
	 * Checks that name() returns the correct string
	 */
	@Test
	public void testName() {
		FALSEgate g = new FALSEgate();
		assertEquals("FALSE", g.name());
	}

	/**
	 * Test method for {@link gates.FALSEgate#output()}.
	 * Checks that the output is always 'false'
	 */
	@Test
	public void testOutput() {
		FALSEgate g = new FALSEgate();
		assertEquals(false, g.output());
	}

	/**
	 * Test method for {@link gates.FALSEgate#getInput(gates.GateIndex)}.
	 * Checks that both Input getters return 'null', as this gate is not supposed to get input values.
	 */
	@Test
	public void testGetInput() {
		FALSEgate g = new FALSEgate();
		assertEquals(null, g.getInput(GateIndex.TOP));
		assertEquals(null, g.getInput(GateIndex.BOTTOM));
	}

	/**
	 * Test method for {@link gates.FALSEgate#resetStatus()}.
	 * Checks that upon creation and also after reseting the status is false.
	 */
	@Test
	public void testResetStatus() {
		FALSEgate g = new FALSEgate();
		g.resetStatus();
		assertEquals(Status.FALSE, g.status);
		g.status = Status.TRUE;
		assertEquals(Status.TRUE, g.status);
		g.resetStatus();
		assertEquals(Status.FALSE, g.status);
	}


}
