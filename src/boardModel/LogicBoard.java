package boardModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import command.TryToRemoveConnection;
import gates.*;
import gui.LogicBoardGUI;
import gui.TileType;
import utility.GateWithIndexTuple;
import utility.PointTupleWithStatus;
import utility.PositionCalculator;
import utility.Vector2Int;
import utility.Vector3Int;

/**
 * The underlying model of a logic board.
 * @author Dominik Baumann
 * @version 2, July 2022
 * <p>
 * The underlying model of a logic board.
 * <p>
 * {@link gates.Gate Gates} can be placed on the board
 * by storing a mapping which maps positions on the board
 * to a {@link gates.Gate Gate} object.
 * <p>
 * Connections between {@link gates.Gate Gates} are stored
 * implicitly <b>in</b> the {@link gates.Gate Gates}.
 * <p>
 * If necessary, the model tells the GUI to update the visualization 
 * and gives the GUI the required information to do that,
 *  but the GUI <b>cannot</b> change anything in the model.
 */
public class LogicBoard {
	
	/** The GUI for visualizing this model. */
	LogicBoardGUI boardGUI;
	
	/** Helper, which facilitates verification of mouse positions etc. */
	PositionCalculator positionCalculator;
	
	/** Stores all gates in the model with their position. */
	TreeMap<Vector2Int, Gate> gates;
	
	/** TODO this is not at all doing anything, or serving a reasonable purpose */
	HashMap<Gate, Integer> outputGates;
	
	/** Keep track of whether the board has been evaluated => set it to true
	 * <p> Set to false after executing some command (e.g. placing a gate) on an evaluated board. */
	boolean inEvaluatedState = false;
	
	
	
	/** The constructor needs to know which GUI to address.
	 * @param gui_ The GUI that is visualizing this board. */
	public LogicBoard(LogicBoardGUI gui_) {
		boardGUI = gui_;
		positionCalculator = boardGUI.getPositionCalculatorFromGUI();
		gates = new TreeMap<Vector2Int, Gate>();
		outputGates = new HashMap<Gate, Integer>();
	}
	

	
	/******* ADDITION AND REMOVAL OF GATES AND CONNECTIONS *******/
	
	/** Add a gate at a given position. If another
	 * gate was previously present at this position,
	 * maintain existing connections. If the new gate is a
	 * {@link gates.NOTgate NOT-gate}, only one input will
	 * be maintained (with {@link gates.GateIndex#TOP TOP}
	 * being prioritized over {@link gates.GateIndex#BOTTOM BOTTOM}).
	 * @param g Gate to add to the board.
	 * @param v Position at which to add the gate.
	 */
	public void addGate(Gate g, Vector2Int v) {
		resetStatusOfGatesOnBoard();
		if(gates.containsKey(v)) { // if there is a gate at this position
			Gate temp = gates.get(v); // get the gate
			g.setInput(temp.getInput(GateIndex.BOTTOM), GateIndex.BOTTOM);
			g.setInput(temp.getInput(GateIndex.TOP), GateIndex.TOP);
			updateGateInputs(temp, g);
			setOutputCount(g, getOutputCount(temp));
		}
		else
			setOutputCount(g, 0);
		gates.put(v, g);
//		addOutputGate(g);
		updateGUI();
	}

	
	
	/** Remove a gate at a given position.
	 * @param v The position at which to remove a gate.
	 * @return A list with gates that received input from the removed gate.
	 * This information is required for undoing the removal of the gate.
	 * @see command.RemoveGateAt
	 */
	public ArrayList<GateWithIndexTuple> removeGate(Vector2Int v) {
		resetStatusOfGatesOnBoard();
		Gate g = gates.get(v);
//		removeOutputGate(g);
		outputGates.remove(g);
		gates.remove(v, g);
		ArrayList<GateWithIndexTuple> receivers = updateGateInputs(g, null);
		updateGUI();
		return receivers;
	}
	
	
	
	/** Adds a connection between two gates (which are 
	 * specified by their position on the board).
	 * @param fromOutput The {@link gates.Gate Gate} which provides its output value as input.
	 * @param toInput The {@link gates.Gate Gate} which receives input from another {@link gates.Gate Gate}.
	 * @param inputIndex Decides which of the input slots to connect to.
	 */
	public void addConnection(Vector2Int fromOutput, Vector2Int toInput, GateIndex inputIndex) {
		resetStatusOfGatesOnBoard();
		
		Gate givingOutput = gates.get(fromOutput);
//		addOutputGate(givingOutput);
		setOutputCount(givingOutput, getOutputCount(givingOutput) + 1);
		gates.get(toInput).setInput(givingOutput, inputIndex);
		updateGUI();
	}
	
	
	
	/** Remove the connection going <b>to</b> this gate at the specified input.
	 * <p>
	 * This function is used primarily for UNDOING the creation of connections,
	 * which is why it doesn't need to perform any checks with 
	 * {@link boardModel.LogicBoard#clickedNearConnection clickedNearConnection}.
	 * @param pos The position of the gate to which the connection leads that is to be deleted.
	 * @param ind The input identifier for the connection on this gate.
	 */
	public void removeConnection(Vector2Int pos, GateIndex ind) {
		gates.get(pos).setInput(null, ind);
		updateGUI();
	}
	
	
	
	/** Given a position (of a right mouse click), check if this position
	 * is close to any connection on the board. If so, remove that connection.
	 * <p>
	 * If there are multiple connections nearby, only one gets removed.
	 * @param clickPos Position of (right) mouse click.
	 * @param command The command for attempting to remove the connection.
	 * @return True iff the click was near an existing connection
	 */
	public boolean attemptConnectionRemoval(Vector3Int clickPos, TryToRemoveConnection command) {
		for(Vector2Int key : gates.keySet()) {
			Gate temp = gates.get(key);

			// check both inputs
			for(GateIndex ind : GateIndex.values()) {
				if(clickedNearConnection(clickPos, temp, key, ind)) {
					command.setInfo(key, getPositionOfGate(temp.getInput(ind)), ind);
//					removeOutputGate(temp.getInput(ind));
					setOutputCount(temp.getInput(ind), getOutputCount(temp.getInput(ind)) - 1);
					resetStatusOfGatesOnBoard(); // TODO: only if this action was successful
					removeConnection(key, ind);
					return true;					
				}
			}
		}
		return false; // right click was not near any connection
	}
	

	
	/******* COMMUNICATION WITH GUI *******/
	
	// create a list of tiles to paint
	// a list of points for which to draw lines + boolean with one input or two inputs
	// looks like extra work, but the gui should not be allowed to modify the model.
	private void updateGUI() {
		TreeMap<Vector2Int, TileType> tiles = new TreeMap<Vector2Int, TileType>();
		ArrayList<PointTupleWithStatus> connections = new ArrayList<PointTupleWithStatus>();
		Vector2Int dim = new Vector2Int(0, 0);
		for(Vector2Int key : gates.keySet()) {
			Gate temp = gates.get(key);
			tiles.put(key, Converter.getTypeFromGate(temp));
			
			dim.componentwiseMaximum(key); // check if gate lies at border of current board
			
			for(GateIndex ind : GateIndex.values())  {
				Gate input = temp.getInput(ind);
				if(input != null) {
					connections.add(new PointTupleWithStatus(
							positionCalculator.getLinePoint(Converter.getTypeFromGate(input), getPositionOfGate(input), null), 
							positionCalculator.getLinePoint(Converter.getTypeFromGate(temp), key, ind),
							input.status()));
				}
			}
		} // end for
		boardGUI.updateDimensions(dim);
		boardGUI.setTilesAndConnections(tiles, connections);
	}
	
	
	
	/**
	 * Call the function for computing a gate's output on
	 * all gates which do not serve as input to another gate
	 * (i.e. when their outputCount is 0).
	 */
	public void evaluate() {
		for(Gate g : outputGates.keySet()) {
			if(outputGates.get(g) == 0) 
				g.output();
		}
		inEvaluatedState = true;
		updateGUI();
	}
	
	
	
	/** 
	 * Checks all gates on the board if they have "from" as an input,
	 * and if so, replace that input with "to".
	 * @param from A gate which is (potentially) used as input.
	 * @param to A gate which should replace all ocurrences of "from" as input.
	 * @return A list with gates that received input from the removed gate.
	 * This information is required for undoing the removal of the gate.
	 * @see command.RemoveGateAt 
	 */
	private ArrayList<GateWithIndexTuple> updateGateInputs(Gate from, Gate to) {
		// store gates which are having "from" as input
		ArrayList<GateWithIndexTuple> receivers = new ArrayList<GateWithIndexTuple>();
		for(Vector2Int key : gates.keySet()) {
			Gate target = gates.get(key);
			for(GateIndex ind : GateIndex.values()) {
				if(target.getInput(ind) == from) {
					target.setInput(to, ind);
					receivers.add(new GateWithIndexTuple(target, ind));
				}
			}
		}
		return receivers;
	}



	/** Given a gate, keep track of how often its output is used as input on other gates. 
	 * Note that one gate may provide its output for both inputs of another gate.
	 * @param g A gate which has the count of inputs it provides for increased or reduced.
	 * @param count The number of gates this gate is serving as input for.
	 */
	private void setOutputCount(Gate g, int count) {
		if(count >= 0)
			outputGates.put(g, count);
		else {
			System.out.println("ERROR: Tried to set number of gates receiving output from gate "
		+ g + " at " + getPositionOfGate(g) + " to a negative value: " + count);
		}
	}
	
	/** Find out for how many inputs this gate's output is used.
	 * @param g A gate on the board.
	 * @return The number of inputs for which this gate's output is used.
	 */
	private int getOutputCount(Gate g) {
		try { return outputGates.get(g); }
		catch(java.lang.NullPointerException ex) { return 0; }
	}



	/********************* CYCLE DETECTION *********************/
	
	/** Given two gates (specified by their position on the grid),
	 * add a new connection to the model and 
	 * check if the model then contains a cycle.
	 * <p>
	 * After the test, remove that connection again.
	 * @param start Start coordinates of connection
	 * @param end end coordinates of connection
	 * @param startIndex Identifier for the  input / output used at the start of the connection.
	 * @param endIndex Identifier for the  input / output used at the end of the connection.
	 * @return "true" iff the new connection forms a cycle
	 */
	public boolean formsCycle(Vector2Int start, Vector2Int end, 
			GateIndex startIndex, GateIndex endIndex) {
		boolean testResult = false; 
		if(endIndex == null) // connection was drawn FROM an input TO an output
			testResult = simulateNewConnection(start, end, startIndex, endIndex);
		else // connection was drawn FROM an output TO an input
			testResult = simulateNewConnection(end, start, endIndex, startIndex);
		return testResult;
	}
	
	
	
	/** Add a new connection to the model,
	 * check if the model then contains a cycle,
	 * then remove the connection again
	 * @param start Start coordinates of connection
	 * @param end end coordinates of connection
	 * @param startIndex Identifier for the  input / output used at the start of the connection.
	 * @param endIndex Identifier for the  input / output used at the end of the connection.
	 * @return "true" iff the new connection forms a cycle
	 */
	private boolean simulateNewConnection(Vector2Int start, Vector2Int end, 
			GateIndex startIndex, GateIndex endIndex) {
		boolean testResult;
		Gate toSetInput = gates.get(start);
		Gate oldInput = toSetInput.getInput(startIndex); 
		toSetInput.setInput(gates.get(end), startIndex); // set the new connection 
		testResult = hasCycle(start); // make test 
		toSetInput.setInput(oldInput, startIndex); // reset status (i.e. remove the new connection)
		return testResult;
	}

	
	
	/** This function tests if the connections in the 
	 * current model form a cycle - essentially by
	 * performing a depth first search.
	 * <p> <b>IDEA:</b>
	 * Pick a gate to start with, and get the gates
	 * that are providing an input for it. For each
	 * gate providing an input, check their input ... and so on
	 * <p>
	 * If the starting gate is reached again, we know
	 * that the connections on the board form a cycle.
	 * 
	 * @param start Grid position of a tile
	 * @return "true" iff the connections in the model form a cycle
	 */
	private boolean hasCycle(Vector2Int start) {
		ArrayList<Gate> frontier = new ArrayList<Gate>();
		ArrayList<Gate> discovered = new ArrayList<Gate>();

		// get inputs of start gate
		Gate current = gates.get(start);
		Gate candidate;
		for(GateIndex ind : GateIndex.values()) {
			candidate = current.getInput(ind);
			if(candidate != null && !discovered.contains(candidate)) {
				discovered.add(candidate);
				frontier.add(candidate);
			}
		}
		// core loop
		while(frontier.size() > 0) {
			current = frontier.remove(0);
			for(GateIndex ind : GateIndex.values()) {
				candidate = current.getInput(ind);
				if(candidate == gates.get(start))
					return true; // found cyccle
				if(candidate != null && !discovered.contains(candidate)) {
					discovered.add(candidate);
					frontier.add(candidate);
				}
			}
		}
		return false; // no cycle was found
	}

	
	
	/************** RESETTING MODEL **************/
	
	/** Remove all gates from the board
	 * and tell the GUI to update accordingly.
	 */
	public void reset() {
		// reset model
		gates = new TreeMap<Vector2Int, Gate>();
		outputGates = new HashMap<Gate, Integer>();

		// reset gui
		boardGUI.setDimensions(-1, -1);
		boardGUI.setTilesAndConnections(new TreeMap<Vector2Int, TileType>(), new ArrayList<PointTupleWithStatus>());
	}
	

	/** Reset the status of all gates on the board
	 * to {@link gates.Status#UNEVALUATED UNEVALUATED}
	 * if they are in a different state.
	 * <p>
	 * This has purely cosmetic effects on the visualization.
	 */
	private void resetStatusOfGatesOnBoard() {
		if(inEvaluatedState) {
			inEvaluatedState = false;
			for(Vector2Int key : gates.keySet())
				gates.get(key).resetStatus();
		}
	}


	/********************* HELPERS *********************/
	

	/**
	 * @return The number of gates in the model
	 * @see app.BoardSaver
	 */
	public int numberOfGates() { return gates.size(); }
	
	
	
	/** Used for bringing back a board via "Undo"
	 * after issuing the command {@link command.ResetBoard ResetBoard}.
	 * @param gates_ A record of all gates on the board.
	 * @param outputGates_ A record of all gates that are not providing input to another gate.
	 */
	public void setGates(TreeMap<Vector2Int, Gate> gates_, HashMap<Gate, Integer> outputGates_) {
		gates = gates_; outputGates = outputGates_;
		updateGUI();
	}

	
	
	/**
	 * @return A copy of the record of gates on the board.
	 * @see command.ResetBoard
	 */
	public TreeMap<Vector2Int, Gate> getGates() {
		TreeMap<Vector2Int, Gate> gatesCopy = new TreeMap<Vector2Int, Gate>();
		for(Vector2Int key : gates.keySet())
			gatesCopy.put(key, gates.get(key));
		return gatesCopy;
	}

	
	
	/**
	 * @return A copy of the record of gates providing no input to other gates.
	 * @see command.ResetBoard
	 */
	public HashMap<Gate, Integer> getOutputGates() {
		HashMap<Gate, Integer> outputGatesCopy = new HashMap<Gate, Integer>();
		for(Gate g : outputGates.keySet())
			outputGatesCopy.put(g, outputGates.get(g));
		return outputGatesCopy;
	}
	

	
	/** Allow the controller to check if the model
	 * has a gate at a certain position
	 * @param v A position to check for gates.
	 * @return "true" iff the model has a gate at this position.
	 */
	public boolean hasGate(Vector2Int v) { return gates.containsKey(v); }
	
	
	
	/** Get a gate on the board by its position.
	 * @param v The (potential) position of a gate on the board
	 * @return The gate at that position, OR null if no gate is there
	 */
	public Gate getGate(Vector2Int v) {
		if(hasGate(v))
			return gates.get(v);
		return null;
	}
	
	
	
	/**
	 * @param v The (potential) position of a gate on the board.
	 * @return The type of gate at that position as 
	 * {@link gui.TileType TileType}.
	 */
	public TileType getGateType(Vector2Int v) {
		if(hasGate(v))
			return Converter.getTypeFromGate(gates.get(v));
		return TileType.EMPTY;
	}


	
	/** This function finds the position of a gate on the board.
	 * @param g A gate on the board
	 * @return the grid position of the given gate on the board 
	 * (OR null if the gate was not on the board)
	 */
	public Vector2Int getPositionOfGate(Gate g) {
		for(Vector2Int key : gates.keySet())
			if(g == gates.get(key))
				return key;
		System.out.println("ERROR (LogicBoard): Could not get position of gate: " + g);
		return null;
	}

	
	
	/** Check if a position (of a right mouse click)
	 * lies close to a connection on the board.
	 * @param clickPos The position of a right mouse click.
	 * @param gate A gate for which to investigate incoming connections.
	 * @param gatePos The position of that gate.
	 * @param ind The input identifier which is considered.
	 * @return True iff the click was near to an existing connection.
	 */
	private boolean clickedNearConnection(Vector3Int clickPos, Gate gate, Vector2Int gatePos, GateIndex ind) {
		Gate input = gate.getInput(ind);
		if(input != null) { // no need to check if there was no input in the first place
			Vector3Int a = new Vector3Int(positionCalculator.getLinePoint(Converter.getTypeFromGate(input), getPositionOfGate(input), null)); 
			Vector3Int b = new Vector3Int(positionCalculator.getLinePoint(Converter.getTypeFromGate(gate), gatePos, ind));
			return(clickPos.nearToLineSegment(a, b, 10));
		}
		return false;
	}
	
	
	
	/**
	 * @return A string representation of the model, fit to be saved to a .txt-file
	 * @see app.BoardSaver
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
		}
		return s;
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