/**
 * 
 */
package command;

import boardModel.LogicBoard;
import gates.Gate;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class RemoveGateAt implements Command {

	Vector2Int pos;
	Gate removedGate;
	LogicBoard model;
	public RemoveGateAt(LogicBoard model_, Vector2Int v) {
		model = model_; pos = v; removedGate = model.getGate(v);
	}
	
	@Override
	public boolean execute() {
		model.removeGate(pos);
		return true;
	}

	@Override
	public void undo() {
		model.addGate(removedGate, pos);
	}

}