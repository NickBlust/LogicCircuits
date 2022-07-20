package command;

import boardModel.Converter;
import boardModel.LogicBoard;
import gates.Gate;
import gates.GateIndex;
import gui.TileType;
import utility.Vector2Int;

/** This command wraps the task of adding a gate to 
 * the {@link boardModel.LogicBoard model of a logic circuits board}.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class PlaceGateAt implements Command {

	/** The model to which to add a gate. */
	private LogicBoard model;
	
	/** The position (in grid coordinates) at which to place the gate. */
	private Vector2Int targetPos;
	
	/** If there already was a gate at this position, save it. */
	private Gate previousGateAtPosition;
	
	/** The type of gate / tile to place. */
	private TileType type;
	
	private Vector2Int previousInputTOP;
	private Vector2Int previousInputBOTTOM;
	
	/** Constructs a command to place a gate at a given position.
	 * @param model_ The model to which to add a gate.
	 * @param v_ The position (in grid coordinates) at which to place the gate.
	 * @param type_ The type of tile to place.
	 */
	public PlaceGateAt(LogicBoard model_, Vector2Int v_, TileType type_) {
		model = model_; targetPos = v_; type = type_;
		previousGateAtPosition = model.getGate(targetPos);
		if(previousGateAtPosition != null) {
			Gate temp = previousGateAtPosition.getInput(GateIndex.TOP);
			if(temp != null)
				previousInputTOP = model.getPositionOfGate(temp);
			
			temp = previousGateAtPosition.getInput(GateIndex.BOTTOM);
			if(temp != null)
				previousInputBOTTOM = model.getPositionOfGate(temp);	
		}
	}

	@Override
	public boolean execute() {
		// Check if this position already contains a gate of the type to be placed
		Gate g = model.getGate(targetPos);
		if(g != null && g.getClass().equals(Converter.getGateFromType(type).getClass()))
			return false;
		// position was empty or contained gate of different type
		model.addGate(Converter.getGateFromType(type), targetPos);
		return true;
	}

	@Override
	public void undo() {
		if(previousGateAtPosition != null) {
			model.addGate(previousGateAtPosition, targetPos);	
			model.addConnection(previousInputTOP, targetPos, GateIndex.TOP);
			model.addConnection(previousInputBOTTOM, targetPos, GateIndex.BOTTOM);
		}
	}
}