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
 * Test class for the class Gate
 * @author philipp grzywaczyk
 * @version 2, September 2022
 * <p>
 * Test class for the Gate class.
 * All "general" gate methoda are tested here, while abstract methods are tested where they
 * are implemented.
 *
 */
public class GateTest extends TestCase {

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
	 * Test method for {@link gates.Gate#status()}.
	 */
	@Test
	public void testStatus() {
		Gate g = new ANDgate();
		assertEquals(g.status(), Status.UNEVALUATED);
		g.status = Status.FALSE;
		assertEquals(g.status(), Status.FALSE);
	}

	/**
	 * Test method for {@link gates.Gate#setStatus(boolean)}.
	 */
	@Test
	public void testSetStatus() {
		Gate g = new ANDgate();
		assertEquals(g.status(), Status.UNEVALUATED);
		g.setStatus(true);
		assertEquals(g.status(), Status.TRUE);
	}

}
