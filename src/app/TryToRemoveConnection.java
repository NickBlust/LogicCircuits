/**
 * 
 */
package app;

import boardModel.LogicBoard;
import command.Command;
import gates.Gate;
import gates.GateIndex;
import utility.Vector3Int;

/**
 * @author domin
 *
 */
public class TryToRemoveConnection implements Command {

	LogicBoard model;
	Vector3Int pos;
	Gate getsInput;
	Gate providesInput;
	GateIndex index;
	
	/**
	 * @param theBoard
	 * @param v
	 */
	public TryToRemoveConnection(LogicBoard board, Vector3Int clickPos) {
		model = board;
		pos = clickPos;
	}

	@Override
	public boolean execute() {
		boolean result = model.attemptConnectionRemoval(pos, this);
		return result;
	}
	
	public void setGate(Gate g) { getsInput = g; }
	public void setIndex(GateIndex i) { index = i; }
	public void SetInputGivingGate(Gate g) { providesInput = g; }
	
}