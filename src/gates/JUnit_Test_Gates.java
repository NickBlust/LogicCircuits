package gates;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * This class tests the functionality of all gates.
 * @author Dominik Baumann
 * @version 2, July 2022
 * @see Gate
 */
public class JUnit_Test_Gates extends TestCase {


	/** @throws java.lang.Exception */
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/** @throws java.lang.Exception */
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for the constant Gates: 
	 * {@link gates.TRUEgate TRUEgate} and {@link gates.FALSEgate FALSEgate}.
	 */
	@Test
	public void test_ConstGates() {
		Gate t = new TRUEgate();
		assertTrue(t.output());
		
		Gate f = new FALSEgate();
		assertFalse(f.output());
	}
	
	/** Test method for the {@link gates.ANDgate ANDgate}. */
	@Test
	public void test_ANDGate() {
		Gate g = new ANDgate();
		g.setInput(new TRUEgate(), GateIndex.TOP);
		g.setInput(new TRUEgate(), GateIndex.BOTTOM);
		assertTrue(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		g.setInput(new TRUEgate(), GateIndex.BOTTOM);
		assertFalse(g.output());
		
		g.setInput(new TRUEgate(), GateIndex.TOP);
		g.setInput(new FALSEgate(), GateIndex.BOTTOM);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		g.setInput(new FALSEgate(), GateIndex.BOTTOM);
		assertFalse(g.output());
	}
	
	/** Test method for the {@link gates.NANDgate NANDgate}. */
	@Test
	public void test_NANDGate() {
		Gate g = new NANDgate();
		g.setInput(new TRUEgate(), GateIndex.TOP);
		g.setInput(new TRUEgate(), GateIndex.BOTTOM);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		g.setInput(new TRUEgate(), GateIndex.BOTTOM);
		assertTrue(g.output());
		
		g.setInput(new TRUEgate(), GateIndex.TOP);
		g.setInput(new FALSEgate(), GateIndex.BOTTOM);
		assertTrue(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		g.setInput(new FALSEgate(), GateIndex.BOTTOM);
		assertTrue(g.output());
	}
	
	/** Test method for the {@link gates.NORgate NORgate}. */
	@Test
	public void test_NORGate() {
		Gate g = new NORgate();
		g.setInput(new TRUEgate(), GateIndex.TOP);
		g.setInput(new TRUEgate(), GateIndex.BOTTOM);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		g.setInput(new TRUEgate(), GateIndex.BOTTOM);
		assertFalse(g.output());
		
		g.setInput(new TRUEgate(), GateIndex.TOP);
		g.setInput(new FALSEgate(), GateIndex.BOTTOM);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		g.setInput(new FALSEgate(), GateIndex.BOTTOM);
		assertTrue(g.output());
	}
	
	/** Test method for the {@link gates.NOTgate NOTgate}. */
	@Test
	public void test_NOTGate() {
		Gate g = new NOTgate();
		g.setInput(new TRUEgate(), GateIndex.TOP);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		assertTrue(g.output());	
	}
	
	/** Test method for the {@link gates.ORgate ORgate}. */
	@Test
	public void test_ORGate() {
		Gate g = new ORgate();
		g.setInput(new TRUEgate(), GateIndex.TOP);
		g.setInput(new TRUEgate(), GateIndex.BOTTOM);
		assertTrue(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		g.setInput(new TRUEgate(), GateIndex.BOTTOM);
		assertTrue(g.output());
		
		g.setInput(new TRUEgate(), GateIndex.TOP);
		g.setInput(new FALSEgate(), GateIndex.BOTTOM);
		assertTrue(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		g.setInput(new FALSEgate(), GateIndex.BOTTOM);
		assertFalse(g.output());
	}
	
	/** Test method for the {@link gates.ORgate ORgate}. */
	@Test
	public void test_XORGate() {
		Gate g = new XORgate();
		g.setInput(new TRUEgate(), GateIndex.TOP);
		g.setInput(new TRUEgate(), GateIndex.BOTTOM);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		g.setInput(new TRUEgate(), GateIndex.BOTTOM);
		assertTrue(g.output());
		
		g.setInput(new TRUEgate(), GateIndex.TOP);
		g.setInput(new FALSEgate(), GateIndex.BOTTOM);
		assertTrue(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
		g.setInput(new FALSEgate(), GateIndex.BOTTOM);
		assertFalse(g.output());
	}
	
	/**
	 * Test method to check that the input works with gates that are not
	 *  {@link gates.TRUEgate TRUEgate}s or {@link gates.FALSEgate FALSEgate}s.
	 */
	@Test
	public void test_Gate_Assignment() {
		Gate s1 = new TRUEgate();
		Gate s2 = new FALSEgate();
		Gate s3 = new TRUEgate();
		Gate s4 = new FALSEgate();
		
		// assign gates
		Gate g1 = new ANDgate();
		g1.setInput(s1, GateIndex.TOP);
		g1.setInput(s2, GateIndex.BOTTOM);
		Gate g2 = new ORgate();
		g2.setInput(s3, GateIndex.TOP);
		g2.setInput(s4, GateIndex.BOTTOM);
		Gate g3 = new XORgate();
		g3.setInput(g1, GateIndex.TOP);
		g3.setInput(g2, GateIndex.BOTTOM);
		// get value of final gate
		assertTrue(g3.output());
	}
}