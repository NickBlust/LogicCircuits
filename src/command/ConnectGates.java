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
		model.addConnection(fromOutput, toInput, inputIndex);
		return true;
	}

	@Override
	public void undo() {
		model.removeConnection(toInput, inputIndex);	
	}
}