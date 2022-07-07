/**
 * 
 */
package utility;

import gui.LogicBoardGUI;
import gui.TiledCanvas.TileType;

/**
 * @author domin
 *
 */
public class PositionCalculator {

	private static int maxDistance = 10;
	
	public static Vector2Int mousePositionToGridCoordinates(Vector2Int pos) {
		return new Vector2Int((int) Math.ceil(pos.x / LogicBoardGUI.TILE_WIDTH),
				(int) Math.ceil(pos.y / LogicBoardGUI.TILE_HEIGHT));
	}
	
	/**
	 * @param t Type of tile / gate
	 * @param pos Position (of a mouse click) relative to the board's upper left corner. 
	 * @param coord Coordinate of the tile on the Board in (row, column)-format
	 * @return true iff the click was on an input or the output of the tile.
	 */
	public static boolean validPositionOnTile(TileType t, Vector2Int pos, Vector2Int coord) {
		return overOutput(t, pos, coord) 
				|| overSoloInput(t, pos, coord)
				|| overDoubleInput(t, pos, coord);
	}
	
	public static boolean overOutput(TileType t, Vector2Int pos, Vector2Int coord) {
		Vector2Int target = new Vector2Int(LogicBoardGUI.TILE_WIDTH * (coord.x + 1) - 4,
				LogicBoardGUI.TILE_HEIGHT * coord.y + (LogicBoardGUI.TILE_HEIGHT / 2));
		double dist = target.squaredDistance(pos);
		return dist <= maxDistance * maxDistance;
	}
	
	
	public static boolean overSoloInput(TileType t, Vector2Int pos, Vector2Int coord) {
		Vector2Int target = new Vector2Int(LogicBoardGUI.TILE_WIDTH * (coord.x + 1) - 4,
				LogicBoardGUI.TILE_HEIGHT * coord.y + (LogicBoardGUI.TILE_HEIGHT / 2));
		double dist = target.squaredDistance(pos);
		return dist <= maxDistance * maxDistance;
	}
	
	
	public static boolean overDoubleInput(TileType t, Vector2Int pos, Vector2Int coord) {
		Vector2Int target = new Vector2Int(LogicBoardGUI.TILE_WIDTH * (coord.x + 1) - 4,
				LogicBoardGUI.TILE_HEIGHT * coord.y + (LogicBoardGUI.TILE_HEIGHT / 2));
		double dist = target.squaredDistance(pos);
		return dist <= maxDistance * maxDistance;
	}
}