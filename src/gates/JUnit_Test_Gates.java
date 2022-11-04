<<<<<<< HEAD
/**
 * 
 */
=======
>>>>>>> total_refactor_philipp_additions
package gates;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
<<<<<<< HEAD
 * @author Dominik Baumann
 * This class tests the functionality of all gates.
=======
 * This class tests the functionality of all gates.
 * @author Dominik Baumann
 * @version 2, July 2022
>>>>>>> total_refactor_philipp_additions
 * @see Gate
 */
public class JUnit_Test_Gates extends TestCase {


<<<<<<< HEAD
	/** @throws java.lang.Exception */
=======
	/** 
	 * Set Up Method, uses set up of super class
	 * @throws java.lang.Exception */
>>>>>>> total_refactor_philipp_additions
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

<<<<<<< HEAD
	/** @throws java.lang.Exception */
=======
	/** 
	 * Tear Down Method, uses tear down of super class
	 * @throws java.lang.Exception */
>>>>>>> total_refactor_philipp_additions
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
	
<<<<<<< HEAD
	/** Test method for the {@link gates.ANDgate ANDgate}. */
	@Test
	public void test_ANDGate() {
		Gate g = new ANDgate();
		g.setInput(new TRUEgate(), 0);
		g.setInput(new TRUEgate(), 1);
		assertTrue(g.output());
		
		g.setInput(new FALSEgate(), 0);
		g.setInput(new TRUEgate(), 1);
		assertFalse(g.output());
		
		g.setInput(new TRUEgate(), 0);
		g.setInput(new FALSEgate(), 1);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), 0);
		g.setInput(new FALSEgate(), 1);
=======
	/** Test method for the {@link gates.ANDgate ANDgate}. 
	 * Only general output and other inherited methods tested here; concrete methods of gates tested in single classes*/
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
>>>>>>> total_refactor_philipp_additions
		assertFalse(g.output());
	}
	
	/** Test method for the {@link gates.NANDgate NANDgate}. */
	@Test
	public void test_NANDGate() {
		Gate g = new NANDgate();
<<<<<<< HEAD
		g.setInput(new TRUEgate(), 0);
		g.setInput(new TRUEgate(), 1);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), 0);
		g.setInput(new TRUEgate(), 1);
		assertTrue(g.output());
		
		g.setInput(new TRUEgate(), 0);
		g.setInput(new FALSEgate(), 1);
		assertTrue(g.output());
		
		g.setInput(new FALSEgate(), 0);
		g.setInput(new FALSEgate(), 1);
=======
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
>>>>>>> total_refactor_philipp_additions
		assertTrue(g.output());
	}
	
	/** Test method for the {@link gates.NORgate NORgate}. */
	@Test
	public void test_NORGate() {
		Gate g = new NORgate();
<<<<<<< HEAD
		g.setInput(new TRUEgate(), 0);
		g.setInput(new TRUEgate(), 1);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), 0);
		g.setInput(new TRUEgate(), 1);
		assertFalse(g.output());
		
		g.setInput(new TRUEgate(), 0);
		g.setInput(new FALSEgate(), 1);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), 0);
		g.setInput(new FALSEgate(), 1);
=======
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
>>>>>>> total_refactor_philipp_additions
		assertTrue(g.output());
	}
	
	/** Test method for the {@link gates.NOTgate NOTgate}. */
	@Test
	public void test_NOTGate() {
		Gate g = new NOTgate();
<<<<<<< HEAD
		g.setInput(new TRUEgate(), 0);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), 0);
=======
		g.setInput(new TRUEgate(), GateIndex.TOP);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), GateIndex.TOP);
>>>>>>> total_refactor_philipp_additions
		assertTrue(g.output());	
	}
	
	/** Test method for the {@link gates.ORgate ORgate}. */
	@Test
	public void test_ORGate() {
		Gate g = new ORgate();
<<<<<<< HEAD
		g.setInput(new TRUEgate(), 0);
		g.setInput(new TRUEgate(), 1);
		assertTrue(g.output());
		
		g.setInput(new FALSEgate(), 0);
		g.setInput(new TRUEgate(), 1);
		assertTrue(g.output());
		
		g.setInput(new TRUEgate(), 0);
		g.setInput(new FALSEgate(), 1);
		assertTrue(g.output());
		
		g.setInput(new FALSEgate(), 0);
		g.setInput(new FALSEgate(), 1);
=======
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
>>>>>>> total_refactor_philipp_additions
		assertFalse(g.output());
	}
	
	/** Test method for the {@link gates.ORgate ORgate}. */
	@Test
	public void test_XORGate() {
		Gate g = new XORgate();
<<<<<<< HEAD
		g.setInput(new TRUEgate(), 0);
		g.setInput(new TRUEgate(), 1);
		assertFalse(g.output());
		
		g.setInput(new FALSEgate(), 0);
		g.setInput(new TRUEgate(), 1);
		assertTrue(g.output());
		
		g.setInput(new TRUEgate(), 0);
		g.setInput(new FALSEgate(), 1);
		assertTrue(g.output());
		
		g.setInput(new FALSEgate(), 0);
		g.setInput(new FALSEgate(), 1);
=======
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
>>>>>>> total_refactor_philipp_additions
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
<<<<<<< HEAD
		g1.setInput(s1, 0);
		g1.setInput(s2, 1);
		Gate g2 = new ORgate();
		g2.setInput(s3, 0);
		g2.setInput(s4, 1);
		Gate g3 = new XORgate();
		g3.setInput(g1, 0);
		g3.setInput(g2, 1);
=======
		g1.setInput(s1, GateIndex.TOP);
		g1.setInput(s2, GateIndex.BOTTOM);
		Gate g2 = new ORgate();
		g2.setInput(s3, GateIndex.TOP);
		g2.setInput(s4, GateIndex.BOTTOM);
		Gate g3 = new XORgate();
		g3.setInput(g1, GateIndex.TOP);
		g3.setInput(g2, GateIndex.BOTTOM);
>>>>>>> total_refactor_philipp_additions
		// get value of final gate
		assertTrue(g3.output());
	}
}