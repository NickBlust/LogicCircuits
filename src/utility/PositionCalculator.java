/**
 * 
 */
package utility;

import gates.Gate.GateIndex;
import gui.LogicBoardGUI;
import gui.TiledCanvas.TileType;

/**
 * @author domin
 *
 */
public class PositionCalculator {

	private int maxDistance = 10;
	
	private int tileWidth;
	private int tileHeight;
	
	public PositionCalculator(int tileWidth_, int tileHeight_) {
		tileWidth = tileWidth_;
		tileHeight = tileHeight_;
	}
	
	public Vector2Int mousePositionToGridCoordinates(Vector2Int pos) {
		return new Vector2Int((int) Math.ceil(pos.x / tileWidth),
				(int) Math.ceil(pos.y / tileHeight));
	}
	
	/**
	 * @param t Type of tile / gate
	 * @param pos Position (of a mouse click) relative to the board's upper left corner. 
	 * @param coord Coordinate of the tile on the Board in (row, column)-format
	 * @return true iff the click was on an input or the output of the tile.
	 */
	public boolean validPositionOnTile(TileType t, Vector2Int pos, Vector2Int coord, GateIndex index) {
		return overOutput(t, pos, coord) 
				|| overSoloInput(t, pos, coord, index)
				|| overDoubleInput(t, pos, coord, index);
	}
	
	public boolean overOutput(TileType t, Vector2Int pos, Vector2Int coord) {
		Vector2Int target = new Vector2Int(tileWidth * (coord.x + 1) - 4,
				tileHeight * coord.y + (tileHeight / 2));
		double dist = target.squaredDistance(pos);
		return dist <= maxDistance * maxDistance;
	}
	
	
	public boolean overSoloInput(TileType t, Vector2Int pos, Vector2Int coord, GateIndex index) {
		Vector2Int target = new Vector2Int(tileWidth * coord.x + 5,
				tileHeight * coord.y + (tileHeight / 2));
		double dist = target.squaredDistance(pos);
		if (dist <= maxDistance * maxDistance) {
			index = GateIndex.TOP;
			return true;
		}
		return false;
	}
	
	
	public boolean overDoubleInput(TileType t, Vector2Int pos, Vector2Int coord, GateIndex index) {
		// check TOP input first
		Vector2Int target = new Vector2Int(tileWidth * coord.x + 5, tileHeight * coord.y + 5);
		double dist = target.squaredDistance(pos);
		if (dist <= maxDistance * maxDistance) {
			index = GateIndex.TOP;
			return true;
		}
		
		// check BOTTOM input second
		target = new Vector2Int(tileWidth * coord.x + 5, tileHeight * (coord.y + 1) - 4);
		dist = target.squaredDistance(pos);
		if (dist <= maxDistance * maxDistance) {
			index = GateIndex.BOTTOM;
			return true;
		}
		
		return false;
	}
}