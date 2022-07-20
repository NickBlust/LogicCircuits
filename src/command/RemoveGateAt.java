/**
 * 
 */
package command;

import java.util.ArrayList;

import boardModel.LogicBoard;
import gates.Gate;
import utility.GateWithIndexTuple;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class RemoveGateAt implements Command {

	Vector2Int pos;
	Gate removedGate;
	LogicBoard model;
	ArrayList<GateWithIndexTuple> oldConnections;
	public RemoveGateAt(LogicBoard model_, Vector2Int v) {
		model = model_; pos = v; removedGate = model.getGate(v);
		oldConnections = new ArrayList<GateWithIndexTuple>();
	}
	
	@Override
	public boolean execute() {
		oldConnections = model.removeGate(pos);
		return true;
	}

	@Override
	public void undo() {
		model.addGate(removedGate, pos);
		for(int i = 0; i < oldConnections.size(); i++) {
			model.addConnection(pos, model.getPositionOfGate(oldConnections.get(i).gate),
					oldConnections.get(i).ind);
		}
	}
}