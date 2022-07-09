/**
 * 
 */
package boardModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import app.TryToRemoveConnection;
import gates.*;
import gui.LogicBoardGUI;
import gui.TileType;
import utility.PointTuple;
import utility.PositionCalculator;
import utility.Vector2Int;
import utility.Vector3Int;

/**
 * @author domin
 *
 */
public class LogicBoard {
	
	LogicBoardGUI boardGUI;
	PositionCalculator positionCalculator; // TODO get this from gui, rather than over instructor
	TreeMap<Vector2Int, Gate> gates;
	HashMap<Gate, Integer> outputGates;
	boolean inEvaluatedState = false;
	
	public LogicBoard(LogicBoardGUI gui_) {
		boardGUI = gui_;
		positionCalculator = boardGUI.getPositionCalculatorFromGUI();
		gates = new TreeMap<Vector2Int, Gate>();
		outputGates = new HashMap<Gate, Integer>();
	}
	
	// TODO: check if connections from the output are maintained
	// NOTE null check is performed before issuing the command
	public void addGate(Gate g, Vector2Int v) {
		if(inEvaluatedState) { resetStatusOnBoards(); }
		
		if(gates.containsKey(v)) { // if there is a gate at this position
			Gate temp = gates.get(v); // get the gate
			//	keep the connections
			/* Try setting the BOTTOM input before also trying to set the TOP input.
			 * If we set the BOTTOM input before the TOP input,
			 * the TOP input will be kept for NOTgates (rather than the BOTTOM input),
			 * unless the TOP input does not exist => then the NOTgate keeps the BOTTOM input. */
			g.setInput(temp.getInput(GateIndex.BOTTOM), GateIndex.BOTTOM);
			Gate tempInput = temp.getInput(GateIndex.TOP);
			if(tempInput != null)
				g.setInput(tempInput, GateIndex.TOP);
			updateGateInputs(temp, g);
		}
		gates.put(v, g);
		addOutputGate(g);
		updateGUI();
	}

	public void removeGate(Vector2Int v) {
		if(inEvaluatedState) { resetStatusOnBoards(); }
		Gate g = gates.get(v);
		removeOutputGate(g);
		gates.remove(v, g);
		updateGateInputs(g, null);
		updateGUI();
	}
	
	/** 
	 * Checks all gates on the board if they have "from" as an input,
	 * and if so, replace that input with "to".
	 * @param from
	 * @param to
	 */
	private void updateGateInputs(Gate from, Gate to) {
		for(Vector2Int key : gates.keySet()) {
			Gate target = gates.get(key);
			for(GateIndex ind : GateIndex.values()) {
				if(target.getInput(ind) == from)
					target.setInput(to, ind);
			}
		}
	}
	
	/**
	 * @param fromOutput
	 * @param toInput
	 * @param inputIndex
	 */
	public void addConnection(Vector2Int fromOutput, Vector2Int toInput, GateIndex inputIndex) {
		if(inEvaluatedState) { resetStatusOnBoards(); }
		
		Gate givingOutput = gates.get(fromOutput);
		addOutputGate(givingOutput);
		gates.get(toInput).setInput(givingOutput, inputIndex);
		updateGUI();
	}
	
	/**
	 * @param g
	 */
	private void removeOutputGate(Gate g) {
		if(outputGates.get(g) > 1)
			outputGates.put(g, outputGates.get(g) - 1);		
		else if(outputGates.get(g) == 1)
			outputGates.remove(g);
		else {
			System.out.println("ERROR (LogicBoard): something went wrong with the outputgates: " 
		+ "g serves as output for " + outputGates.get(g) + " gate(s)");
		}
	}
	

	/**
	 * @param g
	 */
	private void addOutputGate(Gate g) {
		if(!outputGates.containsKey(g))
			outputGates.put(g, 1);
		else
			outputGates.put(g, outputGates.get(g) + 1);
	}

	public boolean attemptConnectionRemoval(Vector3Int clickPos, TryToRemoveConnection command) {
		for(Vector2Int key : gates.keySet()) {
			Gate temp = gates.get(key);

			// check both inputs
			for(GateIndex ind : GateIndex.values()) {
				if(ClickedNearConnection(clickPos, temp, key, ind)) {
					command.setInfo(temp, ind,  temp.getInput(ind));
					removeOutputGate(temp.getInput(ind));
					temp.setInput(null, ind); // actually remove connection
					if(inEvaluatedState) { resetStatusOnBoards(); } // TODO: only if this action was successful
					updateGUI();
					return true;					
				}
			}
		}
		return false; // right click was not near any connection
	}

	/**
	 * @param clickPos
	 * @param g
	 * @param key
	 * @param ind 
	 * @return
	 */
	private boolean ClickedNearConnection(Vector3Int clickPos, Gate g, Vector2Int key, GateIndex ind) {
		Gate input = g.getInput(ind);
		if(input != null) {
			Vector3Int a = new Vector3Int(positionCalculator.getLinePoint(Converter.getTypeFromGate(input), getPositionOfGate(input), null)); 
			Vector3Int b = new Vector3Int(positionCalculator.getLinePoint(Converter.getTypeFromGate(g), key, ind));
			return(clickPos.nearToLineSegment(a, b, 10));
		}
		return false;
	}

	public void evaluate() {
		for(Gate g : outputGates.keySet())
			g.output();
		
		inEvaluatedState = true;
		updateGUI();
	}
	
	// create a list of tiles to paint
	// a list of points for which to draw lines + boolean with one input or two inputs
	// looks like extra work, but the gui should not be allowed to modify the model
	private void updateGUI() {
		TreeMap<Vector2Int, TileType> tiles = new TreeMap<Vector2Int, TileType>();
		ArrayList<PointTuple> connections = new ArrayList<PointTuple>();
		for(Vector2Int key : gates.keySet()) {
			Gate temp = gates.get(key);
			tiles.put(key, Converter.getTypeFromGate(temp));
				
			for(GateIndex ind : GateIndex.values())  {
				Gate input = temp.getInput(ind);
				if(input != null) {
					connections.add(new PointTuple(
							positionCalculator.getLinePoint(Converter.getTypeFromGate(input), getPositionOfGate(input), null), 
							positionCalculator.getLinePoint(Converter.getTypeFromGate(temp), key, ind)));
				}
			}
		} // end for
		boardGUI.setTilesAndConnections(tiles, connections);
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
		Gate candidate;
		for(GateIndex ind : GateIndex.values()) {
			candidate = current.getInput(ind);
			if(candidate != null && !discovered.contains(candidate)) {
				discovered.add(candidate);
				frontier.add(candidate);
			}
		}
		
		while(frontier.size() > 0) {
			current = frontier.remove(0);
			for(GateIndex ind : GateIndex.values()) {
				candidate = current.getInput(ind);
				if(candidate == gates.get(start))
					return true;			
				if(candidate != null && !discovered.contains(candidate)) {
					discovered.add(candidate);
					frontier.add(candidate);
				}
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

	/**
	 * 
	 */
	private void resetStatusOnBoards() {
		inEvaluatedState = false;
		for(Vector2Int key : gates.keySet())
			gates.get(key).resetStatus();
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

	/**
	 * 
	 */
	public void reset() {
		gates = new TreeMap<Vector2Int, Gate>();
		outputGates = new HashMap<Gate, Integer>();
		updateGUI();
	}
	
	public void setGates(TreeMap<Vector2Int, Gate> gates_, HashMap<Gate, Integer> outputGates_) {
		gates = gates_; outputGates = outputGates_;
	}

	/**
	 * @return
	 */
	public TreeMap<Vector2Int, Gate> getGates() {
		TreeMap<Vector2Int, Gate> gatesCopy = new TreeMap<Vector2Int, Gate>();
		for(Vector2Int key : gates.keySet())
			gatesCopy.put(key, gates.get(key));
		return gatesCopy;
	}

	/**
	 * @return
	 */
	public ArrayList<Gate> getOutputGates() {
		ArrayList<Gate> outputGatesCopy = new ArrayList<Gate>();
		for(Gate g : outputGates.keySet())
			outputGatesCopy.add(g);
		return outputGatesCopy;
	}

	/**
	 * @return The number of gates in the model
	 */
	public int numberOfGates() { return gates.size(); }

	/**
	 * @return
	 */
	public String toStorageString() {
		String s = "";
		for(Vector2Int key : gates.keySet()) {
			if(key == gates.lastKey())
				break;
			s += gates.get(key).name() + " " + key.x + " " + key.y + "\n"; 
		}
		s += gates.lastEntry().getValue().name() + " " + gates.lastKey().x + " " + gates.lastKey().y + "\n"; 
		
		for(Vector2Int key : gates.keySet()) {
			for(GateIndex ind : GateIndex.values()) {
				Gate g = gates.get(key).getInput(ind);
				if(g != null) {
					Vector2Int inPos = getPositionOfGate(g);
					s += inPos.x + " " + inPos.y + " " + key.x + " " + key.y + " " + ind + "\n";
				} 
			}
//			Gate top = gates.get(key).getInput(GateIndex.TOP);
//			if(top != null) {
//				Vector2Int inPos = getPositionOfGate(top);
//				s += inPos.x + " " + inPos.y + " " + key.x + " " + key.y + " " + "TOP";
//			}
//			Gate bottom = gates.get(key).getInput(GateIndex.BOTTOM);
//			if(bottom != null) {
//				Vector2Int inPos = getPositionOfGate(bottom);
//				s += inPos.x + " " + inPos.y + " " + key.x + " " + key.y + " " + "BOTTOM";
//			}
		}
		return s;
	}
}