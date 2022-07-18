/**
 * 
 */
package command;

import java.util.ArrayList;

import boardModel.Converter;
import boardModel.LogicBoard;
import gates.Gate;
import gui.TileType;
import utility.GateWithIndexTuple;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class PlaceGateAt implements Command {

	LogicBoard model;
	Vector2Int v;
	Gate previousGateAtPosition;
	ArrayList<GateWithIndexTuple> oldConnections;
	TileType type;
	/**
	 * @param theBoard
	 * @param point
	 * @param selectedTileToPlace
	 */
	public PlaceGateAt(LogicBoard model_, Vector2Int v_, TileType type_) {
		model = model_; v = v_; type = type_;
		previousGateAtPosition = model.getGate(v);
		oldConnections = new ArrayList<GateWithIndexTuple>();
	}

	@Override
	public boolean execute() {
		// Check if this position already contains a gate of the type to be placed
		Gate g = model.getGate(v);
		if(g != null && g.getClass().equals(Converter.getGateFromType(type).getClass()))
			return false;
		// position was empty or contained gate of different type
		model.addGate(Converter.getGateFromType(type), v);
		return true;
	}

	@Override
	public void undo() {
		if(previousGateAtPosition != null)
			model.addGate(previousGateAtPosition, v);
		else
			oldConnections = model.removeGate(v);		
	}
}