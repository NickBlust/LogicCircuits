/**
 * 
 */
package command;

import boardModel.LogicBoard;
import gates.Gate;
import gates.GateIndex;
import utility.Vector2Int;
import utility.Vector3Int;

/**
 * @author domin
 *
 */
public class TryToRemoveConnection implements Command {

	LogicBoard model;
	Vector3Int pos;
//	Gate getsInput;
//	Gate providesInput;
	Vector2Int getsInput;
	Vector2Int providesInput;
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
		return model.attemptConnectionRemoval(pos, this);
	}
	
	public void setInfo(Vector2Int receivesInput, Vector2Int providesInput_, GateIndex ind) {
		getsInput = receivesInput;
		providesInput = providesInput_;
		index = ind;
	}

	@Override
	public void undo() {
		model.addConnection(providesInput, getsInput, index);
	}
}