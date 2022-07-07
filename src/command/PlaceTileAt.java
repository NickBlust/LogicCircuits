/**
 * 
 */
package command;

import boardModel.Converter;
import boardModel.LogicBoard;
import gui.TiledCanvas.TileType;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class PlaceTileAt implements Command {

	LogicBoard model;
	Vector2Int v;
	TileType type;
	/**
	 * @param theBoard
	 * @param point
	 * @param selectedTileToPlace
	 */
	public PlaceTileAt(LogicBoard model_, Vector2Int v_, TileType type_) {
		model = model_; v = v_; type = type_;
	}

	@Override
	public boolean execute() {
		model.addGate(Converter.getGateFromType(type), v);
		return true;
	}

}
