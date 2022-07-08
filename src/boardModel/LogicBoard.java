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
import utility.PointTuple;
import utility.PositionCalculator;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class LogicBoard {
	
	LogicBoardGUI boardGUI;
	PositionCalculator positionCalculator; // TODO get this from gui, rather than over instructor
	TreeMap<Vector2Int, Gate> gates;
	ArrayList<Gate> outputGates;
	TreeMap<Vector2Int, Boolean> truthValues;
	boolean inEvaluatedState = false;
	
	public LogicBoard(LogicBoardGUI gui_, PositionCalculator positionCalculator_) {
		boardGUI = gui_;
		positionCalculator = positionCalculator_;
		gates = new TreeMap<Vector2Int, Gate>();
		outputGates = new ArrayList<Gate>();
		truthValues = new TreeMap<Vector2Int, Boolean>();
	}
	
	public void addGate(Gate g, Vector2Int v) {
		inEvaluatedState = false;
		
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
		outputGates.add(g);
		updateGUI();
	}

	public void removeGate(Vector2Int v) {
		inEvaluatedState = false;
	}
	
	/**
	 * @param fromOutput
	 * @param toInput
	 * @param inputIndex
	 */
	public void addConnection(Vector2Int fromOutput, Vector2Int toInput, GateIndex inputIndex) {
		inEvaluatedState = false;
		
		Gate givingOutput = gates.get(fromOutput);
		outputGates.remove(givingOutput);
		gates.get(toInput).setInput(givingOutput, inputIndex);
		System.out.println("Connected gate at " + fromOutput + " with gate at " + toInput + " ("
				+ inputIndex + ")");
		for(Gate g : outputGates)
			System.out.println(g);
		updateGUI();
	}
	
	public void removeConnection(Vector2Int fromOutput, Vector2Int toInput, GateIndex inputIndex) {
		inEvaluatedState = false;
		
	}

	public void evaluate() {

		inEvaluatedState = true;
		updateGUI();
	}
	
	// create a list of tiles to paint
	// a list of points for which to draw lines + boolean with one input or two inputs
	// looks like extra work, but the gui should not be allowed to modify the model
	private void updateGUI() {
		TreeMap<Vector2Int, TileType> tiles = new TreeMap<Vector2Int, TileType>();
		ArrayList<PointTuple> connections = new ArrayList<PointTuple>();
		TreeMap<Vector2Int, Boolean> truthValues = new TreeMap<Vector2Int, Boolean>();
		for(Vector2Int key : gates.keySet()) {
			Gate temp = gates.get(key);
			System.out.println(inEvaluatedState);
			tiles.put(key, inEvaluatedState ? Converter.getTypeFromGate(temp, truthValues.get(key)) 
											: Converter.getTypeFromGate(temp));
			
				
				
			
			Gate input = temp.getInput(GateIndex.TOP);
			if(input != null) {
				connections.add(new PointTuple(
						positionCalculator.getLinePoint(Converter.getTypeFromGate(input), getPositionOfGate(input), null), 
						positionCalculator.getLinePoint(Converter.getTypeFromGate(temp), key, GateIndex.TOP)));
				System.out.println(						
						positionCalculator.getLinePoint(Converter.getTypeFromGate(input), getPositionOfGate(input), null) + "  " + 
						positionCalculator.getLinePoint(Converter.getTypeFromGate(temp), key, GateIndex.TOP));
			}
			input = temp.getInput(GateIndex.BOTTOM);
			if(input != null) {
				connections.add(new PointTuple(
						positionCalculator.getLinePoint(Converter.getTypeFromGate(input), getPositionOfGate(input), null), 
						positionCalculator.getLinePoint(Converter.getTypeFromGate(temp), key, GateIndex.BOTTOM)));
				System.out.println(
						positionCalculator.getLinePoint(Converter.getTypeFromGate(input), getPositionOfGate(input), null) + "         " + 
						positionCalculator.getLinePoint(Converter.getTypeFromGate(temp), key, GateIndex.BOTTOM)
				);
			}
		}
		boardGUI.setTilesAndConnections(tiles, connections);
	}
	
	public void test() {
		TRUEgate t = new TRUEgate();
		FALSEgate f = new FALSEgate();
//		ANDgate g1 = new ANDgate();
		ORgate g2 = new ORgate();
		ORgate g3 = new ORgate();
		ORgate g4 = new ORgate();
		
		addGate(t, new Vector2Int(1,2));
		addGate(f, new Vector2Int(1,3));
//		addGate(g1, new Vector2Int(4,3));
		addGate(g2, new Vector2Int(4,2));
		addGate(g3, new Vector2Int(6,1));
		addGate(g4, new Vector2Int(2,4));
		
//		g1.setInput(t, GateIndex.TOP);
//		g1.setInput(f, GateIndex.BOTTOM);
		
//		g2.setInput(t, GateIndex.TOP);
//		g2.setInput(f, GateIndex.BOTTOM);
		g2.setInput(g3, GateIndex.TOP);
		g3.setInput(g2, GateIndex.BOTTOM);		
//		g4.setInput(g2, GateIndex.BOTTOM);		
		hasCycle(new Vector2Int(4,2));
		updateGUI();
//		System.out.println(g1.output());
//		System.out.println(g2.output());
		
//		g1.setInput(t, GateIndex.BOTTOM);
//		g2.setInput(f, GateIndex.TOP);
//
//		System.out.println(g1.output());
//		System.out.println(g2.output());
//		
//		System.out.println(t.getClass().toString());
	}

	/** Check if a connection would form a cycle
	 * @param start Start coordinates of connection
	 * @param v end coordinates of connection
	 * @param index
	 * @param endIndex 
	 * @return
	 */
	public boolean formsCycle(Vector2Int start, Vector2Int end, 
			GateIndex startIndex, GateIndex endIndex) {
		boolean testResult = false; 
		if(endIndex == null) { // connection was drawn FROM an input TO an output
			Gate toSetInput = gates.get(start);
			Gate oldInput = toSetInput.getInput(startIndex); 
			toSetInput.setInput(gates.get(end), startIndex); // set the new connection 
			testResult = hasCycle(start); // make test 
			toSetInput.setInput(oldInput, startIndex); // reset status (i.e. remove the new connection)
		}
		else { // connection was drawn FROM an output TO an input
			Gate toSetInput = gates.get(end);
			Gate oldInput = toSetInput.getInput(endIndex);
			toSetInput.setInput(gates.get(start), endIndex); // set the new connection
			testResult = hasCycle(start); // make test
			toSetInput.setInput(oldInput, endIndex); // reset status (i.e. remove the new connection)
		}
		
		if(!testResult)
			System.out.println("No cycle found!");
		return testResult;
	}

	/**
	 * @param start
	 * @return
	 */
	private boolean hasCycle(Vector2Int start) {
		for(Vector2Int key : gates.keySet()) {
			gates.get(key).getInput(GateIndex.TOP);
			gates.get(key).getInput(GateIndex.BOTTOM);
		}
		
		ArrayList<Gate> frontier = new ArrayList<Gate>();
		ArrayList<Gate> discovered = new ArrayList<Gate>();
		
		Gate current = gates.get(start);
		Gate candidate = current.getInput(GateIndex.TOP);
			if(candidate != null && !discovered.contains(candidate)) {
				discovered.add(candidate);
				frontier.add(candidate);
			}
			
		candidate = current.getInput(GateIndex.BOTTOM);
		if(candidate != null && !discovered.contains(candidate)) {
			frontier.add(candidate);
			discovered.add(candidate);
		}
		
		while(frontier.size() > 0) {
			current = frontier.remove(0);
			candidate = current.getInput(GateIndex.TOP);

			if(candidate == gates.get(start))
				return true;
			if(candidate != null && !discovered.contains(candidate)) {
				frontier.add(candidate);
				discovered.add(candidate);
			}
				
			candidate = current.getInput(GateIndex.BOTTOM);
			if(candidate == gates.get(start))
				return true;
			if(candidate != null && !discovered.contains(candidate)) {
				frontier.add(candidate);
				discovered.add(candidate);
			}
		}
		
		return false;
	}
	
	// HELPERS
	
	private Vector2Int getPositionOfGate(Gate g) {
		for(Vector2Int key : gates.keySet())
			if(g == gates.get(key))
				return key;
		System.out.println("ERROR (LogicBoard): Could not get position of gate: " + g);
		return null;
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
	
	@Override
	public String toString() {
		String s = "Gates:\n";
		for(Vector2Int key : gates.keySet()) {
			s += key + " " + Converter.getTypeFromGate(gates.get(key)) + " " + gates.get(key) + "\n";
		}
		s += "Connections:\n";
		for(Vector2Int key : gates.keySet()) {
			Gate top = gates.get(key).getInput(GateIndex.TOP);
			if(top != null)
				s += gates.get(key) + " at " + getPositionOfGate(gates.get(key)) + " connects to " + getPositionOfGate(top) + " " + Converter.getTypeFromGate(top) + " " + top + " via TOP\n";
			Gate bottom = gates.get(key).getInput(GateIndex.BOTTOM);
			if(bottom != null)
				s += gates.get(key) + " at " + getPositionOfGate(gates.get(key)) + " connects to " + getPositionOfGate(bottom) + " " + Converter.getTypeFromGate(bottom) + " " + bottom + " via BOTTOM\n";
		}
		return s;
	}
}