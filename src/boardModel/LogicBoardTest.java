/**
 * 
 */
package boardModel;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import utility.Vector2Int;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import app.Controller;
import gates.ANDgate;
import gates.FALSEgate;
import gates.Gate;
import gates.GateIndex;
import gates.NOTgate;
import gates.ORgate;
import gates.TRUEgate;
import gui.LogicBoardGUI;
import gui.TileType;

/**
 * @author philb
 *
 */
public class LogicBoardTest extends TestCase {

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
	 * Test method for the class LogicBoard.
	 * Methods under test are:
	 * {@link boardModel.LogicBoard#addGate(gates.Gate, utility.Vector2Int)}.
	 * {@link boardModel.LogicBoard#removeGate(Vector2Int v)}.
	 * {@link boardModel.LogicBoard#addConnection(Vector2Int fromOutput, Vector2Int toInput, GateIndex inputIndex)}.
	 * {@link boardModel.LogicBoard#removeConnection(Vector2Int pos, GateIndex ind)}.
	 * {@link boardModel.LogicBoard#formsCycle(Vector2Int start, Vector2Int end, 
			GateIndex startIndex, GateIndex endIndex)}.
	 * {@link boardModel.LogicBoard#reset()}.
	 * {@link boardModel.LogicBoard#numberOfGates()}.
	 * {@link boardModel.LogicBoard#getGates()}.
	 * {@link boardModel.LogicBoard#getOutputGates()}.
	 * {@link boardModel.LogicBoard#hasGate(Vector2Int v)}.
	 * {@link boardModel.LogicBoard#getGate(Vector2Int v)}.
	 * {@link boardModel.LogicBoard#getGateType(Vector2Int v)}.
	 * {@link boardModel.LogicBoard#getPositionOfGate(Gate g)}.
	 */
	@Test
	public void test_LogicBoard() {
		Controller ctrl = new Controller();
		LogicBoardGUI gui = new LogicBoardGUI(ctrl);
		LogicBoard board = new LogicBoard(gui);
		
		// Make a new gate and assert it is not yet contained anywhere
		ANDgate and = new ANDgate();
		Vector2Int v = new Vector2Int(3,4);
		assertEquals(board.gates.containsValue(and), false);
		assertEquals(board.outputGates.containsKey(and), false);
		assertEquals(board.getGates().isEmpty(), true);
		assertEquals(board.numberOfGates(), 0);
		
		// Add the Gate and check it is added to the corresponding maps
		board.addGate(and, v);
		assertEquals(board.gates.containsValue(and), true);
		assertEquals(board.getGates().containsValue(and), true);
		assertEquals(board.gates.get(v), and);
		assertEquals(board.outputGates.containsKey(and), true);
		assertEquals(board.outputGates.get(and), (Integer)0);
		assertEquals(board.getOutputGates(), board.outputGates);
		
		// Add a second gate and check the same
		TRUEgate t = new TRUEgate();
		Vector2Int w = new Vector2Int(1,1);
		board.addGate(t,w);
		assertEquals(board.gates.containsValue(t), true);
		assertEquals(board.gates.get(w), t);
		assertEquals(board.outputGates.containsKey(t), true);
		assertEquals(board.outputGates.get(t), (Integer)0);
		assertEquals(board.getOutputGates().size(), 2);
		assertEquals(board.getGates(), board.gates);
		
		// Connect these gates and check the connection
		board.addConnection(w, v, GateIndex.TOP);
		assertEquals(board.gates.get(v).getInput(GateIndex.TOP), t);
		//assertEquals(board.getOutputCount(t), 1);
		
		// Replace the AND-Gate by an OR-Gate and check the connection is still in place
		ORgate or = new ORgate();
		assertEquals(board.getGateType(v), TileType.AND);
		board.addGate(or, v);
		assertEquals(board.getGateType(v), TileType.OR);
		assertEquals(board.gates.containsValue(or), true);
		assertEquals(board.gates.get(v), or);
		assertEquals(board.getGate(v), or);
		assertEquals(board.hasGate(v), true);
		assertEquals(board.gates.containsValue(and), false);
		assertEquals(board.gates.get(v).getInput(GateIndex.TOP), t);
		
		// Remove Gate and check input of the gate to which it was connected is now null
		Vector2Int w2 = board.getPositionOfGate(t);
		assertEquals(w2,w);
		board.removeGate(w2);
		assertEquals(board.hasGate(w), false);
		assertEquals(board.gates.containsValue(t), false);
		assertEquals(board.gates.get(v).getInput(GateIndex.TOP), null);
	
		// Check the Reset
		assertEquals(board.gates.isEmpty(), false);
		board.reset();
		assertEquals(board.gates.isEmpty(), true);
		assertEquals(board.numberOfGates(), 0);
		
		// Build a Cycle, along the way check numberOfGates and TileType-getter as well as remove-connection
		Vector2Int u = new Vector2Int(6,7);
		Vector2Int x1 = new Vector2Int(3,1);
		FALSEgate f = new FALSEgate();
		board.addGate(f, x1);
		board.addGate(t, w);
		board.addGate(and, v);
		board.addGate(or, u);
		assertEquals(board.numberOfGates(), 4);		
		assertEquals(board.getGateType(u), TileType.OR);
		board.addConnection(w, v, GateIndex.TOP);
		board.addConnection(v, u, GateIndex.TOP);
		assertEquals(board.formsCycle(x1, u, GateIndex.TOP, GateIndex.BOTTOM), false);
		board.addConnection(x1, u, GateIndex.BOTTOM);
		assertEquals(board.formsCycle(u, v, GateIndex.TOP, GateIndex.BOTTOM), true);
		board.removeConnection(u, GateIndex.TOP);
		assertEquals(board.formsCycle(u, v, GateIndex.TOP, GateIndex.BOTTOM), false);
		
		// Complete the Circuit and evaluate output gates
		// The circuit is such that all output-gates should be true
		NOTgate not = new NOTgate();
		ANDgate and2 = new ANDgate();
		Vector2Int a = new Vector2Int(8,8);
		Vector2Int b = new Vector2Int(9,8);
		board.addConnection(u, v, GateIndex.BOTTOM);
		board.addConnection(w, u, GateIndex.TOP);
		board.addGate(and2, a);
		board.addGate(not, b);
		board.addConnection(u, a, GateIndex.TOP);
		board.addConnection(x1, a, GateIndex.BOTTOM);
		board.addConnection(a, b, GateIndex.TOP);
		
		for(Gate g : board.outputGates.keySet()) {
			if(board.outputGates.get(g) == 0) 
				assertEquals(g.output(), true);
		}
		
		
	}
}
