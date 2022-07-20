package command;

import java.util.ArrayList;

import boardModel.LogicBoard;
import gates.Gate;
import utility.GateWithIndexTuple;
import utility.Vector2Int;


/** This command wraps the task of removing a gate from 
 * the {@link boardModel.LogicBoard model of a logic circuits board}.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class RemoveGateAt implements Command {

	/** The model from which to remove a gate. */
	LogicBoard model;

	/** The position at which to remove a gate. */
	Vector2Int targetPos;
	
	/** Store the gate that was removed
	 * for undoing the command.
	 */
	Gate removedGate;
	
	/** Store a list with gates that received input 
	 * from the removed gate for undoing the command */
	ArrayList<GateWithIndexTuple> oldConnections;
	
	/** Constructs a command to remove a gate at a given position.
	 * @param model_ The model from which to remove a gate.
	 * @param v_ The position at which to remove a gate.
	 */
	public RemoveGateAt(LogicBoard model_, Vector2Int v_) {
		model = model_; targetPos = v_; removedGate = model.getGate(v_);
		//oldConnections = new ArrayList<GateWithIndexTuple>();
	}
	
	@Override
	public boolean execute() {
		oldConnections = model.removeGate(targetPos);
		return true;
	}

	@Override
	public void undo() {
		model.addGate(removedGate, targetPos);
		for(int i = 0; i < oldConnections.size(); i++) {
			model.addConnection(targetPos, model.getPositionOfGate(oldConnections.get(i).gate),
					oldConnections.get(i).ind);
		}
	}
}