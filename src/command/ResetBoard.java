package command;

import java.util.HashMap;
import java.util.TreeMap;

import boardModel.LogicBoard;
import gates.Gate;
import utility.Vector2Int;

/** This command wraps the task of removing all
 * gates from the 
 * {@link boardModel.LogicBoard model of a logic circuits board},
 * which will also result in the model telling the
 * {@link gui.LogicBoardGUI GUI} to reset.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class ResetBoard implements Command {

	/** The model to reset. */
	LogicBoard model;
	
	/** For the purpose of undoing this command,
	 * keep track of all that were on the board
	 * before it was reset.
	  */
	TreeMap<Vector2Int, Gate> gates_;
	
	/** TODO: not sure if we will keep this?
	 * */
	HashMap<Gate, Integer> outputGates_;
	
	/** Constructs a command to remove all
	 * gates from the board / model.
	 * @param someBoard The board / model to reset.
	 */
	public ResetBoard(LogicBoard someBoard) {
		model = someBoard;
		gates_ = someBoard.getGates();
		outputGates_ = someBoard.getOutputGates();
	}
	
	@Override
	public boolean execute() {
		model.reset();
		return true;
	}

	@Override
	public void undo() {
		model.setGates(gates_, outputGates_);
	}

	@Override
	public boolean redo() {
		return execute();
	}
}