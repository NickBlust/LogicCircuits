/**
 * 
 */
package boardModel;

import java.util.ArrayList;
import java.util.TreeMap;

import gates.*;
import gates.Gate.GateIndex;
import gui.LogicBoardGUI;
import gui.TiledCanvas.TileType;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class LogicBoard {
	
	LogicBoardGUI boardGUI;
	TreeMap<Vector2Int, Gate> gates;
	
	public LogicBoard(LogicBoardGUI gui_) {
		boardGUI = gui_;
		gates = new TreeMap<Vector2Int, Gate>();
	}
	
	public boolean hasGate(Vector2Int v) { return gates.containsKey(v); }
	
	public Gate getGate(Vector2Int v) {
		if(hasGate(v))
			return gates.get(v);
		return null;
	}
	
	public TileType getGateType(Vector2Int v) {
		if(hasGate(v))
			return Converter.getTypeFromGate(gates.get(v));
		return TileType.EMPTY;
	}
	
	
	public void addGate(Gate g, Vector2Int v) { 
		System.out.println("Adding " + g + " at " + v);
		for(Vector2Int points : gates.keySet())
			System.out.println(points);
		if(gates.containsKey(v)) { // keep connections when replacing gates
			Gate temp = gates.get(v);
			if(temp != null) {
				/* Try setting the BOTTOM input before also trying to set the TOP input.
				 * If we set the BOTTOM input before the TOP input,
				 * the TOP input will be kept for NOTgates (rather than the BOTTOM input),
				 * unless the TOP input does not exist => then the NOTgate keeps the BOTTOM input. */
				g.setInput(temp.getInput(GateIndex.BOTTOM), GateIndex.BOTTOM);
				Gate tempInput = temp.getInput(GateIndex.TOP);
				if(tempInput != null)
					g.setInput(tempInput, GateIndex.TOP);
			}
		}
		gates.put(v, g); 
		// TODO: this is precarious, as the GUI should not be allowed to modify the gates!
		updateGUI();
	}
	
	public void validateConnection(Vector2Int v1, Vector2Int v2) {
		
	}
	
	// create a list of tiles to paint
	// a list of points for which to draw lines + boolean with one input or two inputs
	private void updateGUI() {
		TreeMap<Vector2Int, TileType> tiles = new TreeMap<Vector2Int, TileType>();
		TreeMap<Vector2Int, Vector2Int> connections = new TreeMap<Vector2Int, Vector2Int>();
		for(Vector2Int key : gates.keySet()) {
			Gate temp = gates.get(key);
			tiles.put(key, Converter.getTypeFromGate(temp));
			
			Gate input = temp.getInput(GateIndex.TOP);
			if(input != null) 
				connections.put(getPositionOfGate(input), key);
			input = temp.getInput(GateIndex.BOTTOM);
			if(input != null) 
				connections.put(getPositionOfGate(input), key);
		}
			// translate to map of 
		// draw empty board
		// for each tile in the map draw the tile
			// and each connection
		boardGUI.setTilesAndConnections(tiles, connections);
	}
	
	private Vector2Int getPositionOfGate(Gate g) {
		for(Vector2Int key : gates.keySet())
			if(g == gates.get(key))
				return key;
		System.out.println("ERROR (LogicBoard): Could not get position of gate: " + g);
		return null;
	}
	
	public void test() {
		TRUEgate t = new TRUEgate();
		FALSEgate f = new FALSEgate();
		ANDgate g1 = new ANDgate();
		ORgate g2 = new ORgate();
		
		g1.setInput(t, GateIndex.TOP);
		g1.setInput(f, GateIndex.BOTTOM);
		
		g2.setInput(t, GateIndex.TOP);
		g2.setInput(f, GateIndex.BOTTOM);
		
		System.out.println(g1.output());
		System.out.println(g2.output());
		
		g1.setInput(t, GateIndex.BOTTOM);
		g2.setInput(f, GateIndex.TOP);

		System.out.println(g1.output());
		System.out.println(g2.output());
		
		System.out.println(t.getClass().toString());
	}

	/** Check if a connection would form a cycle
	 * @param start
	 * @param v
	 * @param index
	 * @return
	 */
	public boolean formsCycle(Vector2Int start, Vector2Int end, GateIndex index) {
		boolean testResult = false;
//		if(index == null) { // connection was drawn FROM an input TO an output
//			Gate toSetInput = gates.get(start);
//			Gate oldInput = toSetInput.getInput(index);
//			
//			toSetInput.setInput(gates.get(end), index);
//			// make test and reset status to before the test
//			toSetInput.setInput(oldInput, index);
//		}
//		else { // connection was drawn FROM an output TO an input
//			Gate toSetInput = gates.get(end);
//			Gate oldInput = toSetInput.getInput(index);
//			
//			toSetInput.setInput(gates.get(end), index);
//			// make test and reset status to before the test
//			toSetInput.setInput(oldInput, index);
//		}

		return testResult;
	}
}