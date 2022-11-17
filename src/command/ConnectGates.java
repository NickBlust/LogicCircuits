package command;

import boardModel.LogicBoard;
import gates.GateIndex;
import utility.Vector2Int;

/** This command wraps the task of connecting two gates in
 * the {@link boardModel.LogicBoard model of a logic circuits board}.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class ConnectGates implements Command {

	/** The model in which to connect two gates. */
	private LogicBoard model;
	
	
	/** The output of a gate the connection exits from. */
	private Vector2Int fromOutput;
	
	/** The input of a gate the connection is leads to. */
	private Vector2Int toInput;
	
	/** If there already existed a connection to the input
	 * that is being connected to by this command, then
	 * the position of that old input-gate is stored here.
	 * Default value is (-1, -1), which indicates
	 * no previous input (as the upper left tile on the board
	 * has coordinates (0, 0).
	 */
	private Vector2Int oldInput = new Vector2Int(-1, -1);
	
	
	/** Specify which of the inputs was connected to. */
	private GateIndex inputIndex;
	

	/** Constructs a command to connect two gates in the model.
	 * @param board The model in which to connect two gates.
	 * @param outputCoord Grid coordinates of the gate, whose output is to be connected to.
	 * @param inputCoord Grid coordinates of the gate, whose input is to be connected to.
	 * @param inputIndex_ Specifies which of the inputs to connect to.
	 */
	public ConnectGates(LogicBoard board, Vector2Int outputCoord, Vector2Int inputCoord, GateIndex inputIndex_) {
		model = board; fromOutput = outputCoord; toInput = inputCoord; inputIndex = inputIndex_;
	}

	@Override
	public boolean execute() {
		// check if another connection already existed
		// if so, store it so that you can recreate it
		if(model.getGate(toInput).getInput(inputIndex) != null) {
			oldInput = model.getPositionOfGate(model.getGate(toInput).getInput(inputIndex));
		}
		
		model.addConnection(fromOutput, toInput, inputIndex);
		return true;
	}

	@Override
	public void undo() {
		model.removeConnection(toInput, inputIndex);
		// recreate connection if applicable
		if(!oldInput.equals(new Vector2Int(-1, -1)))
			model.addConnection(oldInput, toInput, inputIndex);
	}
	
	@Override
	public boolean redo() {
		if(!oldInput.equals(new Vector2Int(-1, -1)))
			model.removeConnection(toInput, inputIndex);
		model.addConnection(fromOutput, toInput, inputIndex);
		return true;
	}
}