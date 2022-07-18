/**
 * 
 */
package command;

import java.util.HashMap;
import java.util.TreeMap;

import boardModel.LogicBoard;
import gates.Gate;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class ResetBoard implements Command {

	LogicBoard board;
	TreeMap<Vector2Int, Gate> gates_;
	HashMap<Gate, Integer> outputGates_;
	
	public ResetBoard(LogicBoard someBoard) {
		board = someBoard;
		gates_ = someBoard.getGates();
		outputGates_ = someBoard.getOutputGates();
	}
	
	@Override
	public boolean execute() {
		board.reset();
		return true;
	}

	@Override
	public void undo() {
		board.setGates(gates_, outputGates_);
	}
}