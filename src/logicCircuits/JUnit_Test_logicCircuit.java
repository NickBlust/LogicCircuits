/**
 * 
 */
package logicCircuits;
import gates.*;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author philb
 *
 */
public class JUnit_Test_logicCircuit extends TestCase {

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
	 * Test method for {@link logicCircuits.logicCircuit#logicCircuit(int, int)}.
	 */
	@Test
	public void testLogicCircuit() {
		int n = 50;
		int m = 100;
		logicCircuit circuit = new logicCircuit(n,m);
		assertEquals(50, circuit.board.length);
		assertEquals(100, circuit.board[13].length);
		assertEquals(100, circuit.board[27].length);
		assertEquals(100, circuit.board[2].length);
	}

	/**
	 * Test method for {@link logicCircuits.logicCircuit#addGate(gates.Gate, int, int)}.
	 */
	@Test
	public void testAddGate() {
		logicCircuit circuit = new logicCircuit(50,50);
		ORgate or1 = new ORgate();
		circuit.addGate(or1, 4, 3);
		assertEquals(or1, circuit.board[4][3]);
		assertEquals(true, circuit.output_gates.contains(or1));
	}

	/**
	 * Test method for {@link logicCircuits.logicCircuit#removeGate(int, int)}.
	 */
	@Test
	public void testRemoveGate() {
		logicCircuit circuit = new logicCircuit(50,50);
		ANDgate and1 = new ANDgate();
		circuit.addGate(and1, 7, 13);
		assertEquals(and1, circuit.board[7][13]);
		assertEquals(true, circuit.output_gates.contains(and1));
		circuit.removeGate(7, 13);
		assertEquals(null, circuit.board[7][13]);
		assertEquals(false, circuit.output_gates.contains(and1));
	}

	/**
	 * Test method for {@link logicCircuits.logicCircuit#connectGates(int, int, int, int, int)}.
	 */
	@Test
	public void testConnectGates() {
		logicCircuit circuit = new logicCircuit(50,50);
		ORgate or1 = new ORgate();
		circuit.addGate(or1, 4, 3);
		ANDgate and1 = new ANDgate();
		circuit.addGate(and1, 7, 13);
		circuit.connectGates(4, 3, 7, 13, 0);
		assertEquals(true, circuit.output_gates.contains(or1));
		assertEquals(false, circuit.output_gates.contains(and1));
		assertEquals(and1, circuit.board[4][3].getInput(0));		
	}

	/**
	 * Test method for {@link logicCircuits.logicCircuit#unconnectGate(int, int, int)}.
	 */
	@Test
	public void testUnconnectGate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link logicCircuits.logicCircuit#get_circuit_output()}.
	 */
	@Test
	public void testGet_circuit_output() {
		fail("Not yet implemented");
	}

}
