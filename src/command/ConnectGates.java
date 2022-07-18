/**
 * 
 */
package command;

import boardModel.LogicBoard;
import gates.GateIndex;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class ConnectGates implements Command {

	LogicBoard model;
	Vector2Int fromOutput; // the Output the connection exits from
	Vector2Int toInput; // the input the connections is leading to
	GateIndex inputIndex; // which of the inputs was connected to?
	
	/**
	 * @param board 
	 * @param outputCoord
	 * @param inputCoord
	 * @param inputIndex
	 */
	public ConnectGates(LogicBoard board, Vector2Int outputCoord, Vector2Int inputCoord, GateIndex inputIndex_) {
		model = board; fromOutput = outputCoord; toInput = inputCoord; inputIndex = inputIndex_;
	}

	@Override
	public boolean execute() {
		model.addConnection(fromOutput, toInput, inputIndex);
		return false;
	}

	@Override
	public void undo() {
		model.removeConnection(toInput, inputIndex);	
	}

}