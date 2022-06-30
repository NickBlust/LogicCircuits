/**
 * 
 */
package logicCircuits;
import gates.*;

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
	 * Test method for {@link logicCircuits.LogicCircuit#LogicCircuit(int, int)}.
	 */
	@Test
	public void testLogicCircuit() {
		int n = 50;
		int m = 100;
		LogicCircuit circuit = new LogicCircuit(n,m);
		assertEquals(50, circuit.board.size());
		assertEquals(100, circuit.board.get(13).size());
	}

	/**
	 * Test method for {@link logicCircuits.LogicCircuit#addGate(gates.Gate, int, int)}.
	 */
	@Test
	public void testAddGate() {
		LogicCircuit circuit = new LogicCircuit(50,50);
		ORgate or1 = new ORgate();
		circuit.addGate(or1, 4, 3);
		assertEquals(or1, circuit.board.get(4).get(3));
		assertEquals(true, circuit.output_gates.contains(or1));
		assertEquals(50, circuit.board.size());
		assertEquals(50, circuit.board.get(13).size());
		ORgate or2 = new ORgate();
		circuit.addGate(or2, 56, 76);
		assertEquals(or2, circuit.board.get(56).get(76));
		assertEquals(true, circuit.output_gates.contains(or2));
		assertEquals(57, circuit.board.size());
		assertEquals(77, circuit.board.get(13).size());
	}

	/**
	 * Test method for {@link logicCircuits.LogicCircuit#removeGate(int, int)}.
	 */
	@Test
	public void testRemoveGate() {
		LogicCircuit circuit = new LogicCircuit(50,50);
		ANDgate and1 = new ANDgate();
		circuit.addGate(and1, 7, 13);
		assertEquals(and1, circuit.board.get(7).get(13));
		assertEquals(true, circuit.output_gates.contains(and1));
		circuit.removeGate(7, 13);
		assertEquals(null, circuit.board.get(7).get(13));
		assertEquals(false, circuit.output_gates.contains(and1));
	}

	/**
	 * Test method for {@link logicCircuits.LogicCircuit#connectGates(int, int, int, int, int)}.
	 */
	@Test
	public void testConnectGates() {
		LogicCircuit circuit = new LogicCircuit(50,50);
		ORgate or1 = new ORgate();
		circuit.addGate(or1, 4, 3);
		ANDgate and1 = new ANDgate();
		circuit.addGate(and1, 7, 13);
		circuit.connectGates(4, 3, 7, 13, 0);
		assertEquals(true, circuit.output_gates.contains(or1));
		assertEquals(false, circuit.output_gates.contains(and1));
		assertEquals(and1, circuit.board.get(4).get(3).getInput(0));		
	}

	/**
	 * Test method for {@link logicCircuits.LogicCircuit#disconnectGate(int, int, int)}.
	 */
	@Test
	public void testUnconnectGate() {
		LogicCircuit circuit = new LogicCircuit(50,50);
		NORgate nor1 = new NORgate();
		circuit.addGate(nor1, 34, 15);
		ANDgate and1 = new ANDgate();
		circuit.addGate(and1, 22, 32);
		circuit.connectGates(34, 15, 22, 32, 1);
		assertEquals(true, circuit.output_gates.contains(nor1));
		assertEquals(false, circuit.output_gates.contains(and1));
		assertEquals(and1, circuit.board.get(34).get(15).getInput(1));
		circuit.disconnectGate(34, 15, 1);
		assertEquals(null, circuit.board.get(34).get(15).getInput(1));
		assertEquals(true, circuit.output_gates.contains(and1));
	}

	/**
	 * Test method for {@link logicCircuits.LogicCircuit#get_circuit_output()}.
	 */
	@Test
	public void testGet_circuit_output() {
		LogicCircuit circuit = new LogicCircuit(50,50);
		ANDgate and1 = new ANDgate();
		ORgate or1 = new ORgate();
		NOTgate not1 = new NOTgate();
		NOTgate not2 = new NOTgate();
		NOTgate not3 = new NOTgate();
		TRUEgate input_t = new TRUEgate();
		FALSEgate input_f = new FALSEgate();
		
		circuit.addGate(input_t, 0, 0);
		circuit.addGate(input_f, 1, 0);
		circuit.addGate(and1, 2, 2);
		circuit.addGate(not1, 2, 4);
		circuit.addGate(not2, 4, 2);
		circuit.addGate(not3, 5, 2);
		circuit.addGate(or1, 4, 4);
		
		circuit.connectGates(2, 4, 2, 2, 0);
		circuit.connectGates(2, 2, 0, 0, 0);
		circuit.connectGates(2, 2, 1, 0, 1);
		circuit.connectGates(4, 2, 0, 0, 0);
		circuit.connectGates(5, 2, 1, 0, 0);
		circuit.connectGates(4, 4, 4, 2, 0);
		circuit.connectGates(4, 4, 5, 2, 1);
		
		boolean[] out = circuit.get_circuit_output();
		assertEquals(2, circuit.get_circuit_output().length);
		assertEquals(out[0], out[1]);		
	}
	

}
