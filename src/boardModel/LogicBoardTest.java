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
import gates.GateIndex;
import gates.TRUEgate;
import gui.LogicBoardGUI;

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
	 * Test method for {@link boardModel.LogicBoard#addGate(gates.Gate, utility.Vector2Int)}.
	 */
	@Test
	public void testAddGate_getOutputGates() {
		Controller ctrl = new Controller();
		LogicBoardGUI gui = new LogicBoardGUI(ctrl);
		LogicBoard board = new LogicBoard(gui);
		
		// Make a new gate and assert it is not yet contained anywhere
		ANDgate and = new ANDgate();
		Vector2Int v = new Vector2Int(3,4);
		assertEquals(board.gates.containsValue(and), false);
		assertEquals(board.outputGates.containsKey(and), false);
		
		// Add the Gate and check it is added to the corresponding maps
		board.addGate(and, v);
		assertEquals(board.gates.containsValue(and), true);
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
		
		// Connect these gates and check the connection
		board.addConnection(w, v, GateIndex.TOP);
		assertEquals(board.gates.get(v).getInput(GateIndex.TOP), t);
		
		
		
		
		
		
		
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#removeGate(utility.Vector2Int)}.
	 */
	@Test
	public void testRemoveGate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#addConnection(utility.Vector2Int, utility.Vector2Int, gates.GateIndex)}.
	 */
	@Test
	public void testAddConnection() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#removeConnection(utility.Vector2Int, gates.GateIndex)}.
	 */
	@Test
	public void testRemoveConnection() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#attemptConnectionRemoval(utility.Vector3Int, command.TryToRemoveConnection)}.
	 */
	@Test
	public void testAttemptConnectionRemoval() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#evaluate()}.
	 */
	@Test
	public void testEvaluate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#formsCycle(utility.Vector2Int, utility.Vector2Int, gates.GateIndex, gates.GateIndex)}.
	 */
	@Test
	public void testFormsCycle() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#reset()}.
	 */
	@Test
	public void testReset() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#numberOfGates()}.
	 */
	@Test
	public void testNumberOfGates() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#setGates(java.util.TreeMap, java.util.HashMap)}.
	 */
	@Test
	public void testSetGates() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#getGates()}.
	 */
	@Test
	public void testGetGates() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#hasGate(utility.Vector2Int)}.
	 */
	@Test
	public void testHasGate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#getGate(utility.Vector2Int)}.
	 */
	@Test
	public void testGetGate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#getGateType(utility.Vector2Int)}.
	 */
	@Test
	public void testGetGateType() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#getPositionOfGate(gates.Gate)}.
	 */
	@Test
	public void testGetPositionOfGate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#toStorageString()}.
	 */
	@Test
	public void testToStorageString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link boardModel.LogicBoard#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
