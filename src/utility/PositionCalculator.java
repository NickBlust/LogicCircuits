/**
 * 
 */
package utility;

import gates.Gate.GateIndex;
import gui.TiledCanvas.TileType;

/**
 * @author domin
 *
 */
public class PositionCalculator {

	private int maxDistance = 10;
	
	private int tileWidth;
	private int tileHeight;
	private int boardWidth;
	private int boardHeight;
	
	public PositionCalculator(int tileWidth_, int tileHeight_, int boardWidth_, int boardHeight_) {
		tileWidth = tileWidth_;
		tileHeight = tileHeight_;
		boardWidth = boardWidth_;
		boardHeight = boardHeight_;
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
	public boolean validStartPositionOnTile(TileType t, Vector2Int pos, Vector2Int coord, GateIndex index) {
		if(t == TileType.TRUE || t == TileType.FALSE) {
			return overOutput(pos, coord);
		}
		else if(t == TileType.NOT || t == TileType.NOT_TRUE || t == TileType.NOT_FALSE) {
			return overOutput(pos, coord) || overSoloInput(pos, coord, index);
		}
		else {
			return overOutput(pos, coord) || overDoubleInput(pos, coord, index);
		}
	}
	
	public boolean overOutput(Vector2Int pos, Vector2Int coord) {
		Vector2Int target = new Vector2Int(tileWidth * (coord.x + 1) - 4,
				tileHeight * coord.y + (tileHeight / 2));
		double dist = target.squaredDistance(pos);
		return dist <= maxDistance * maxDistance;
	}
	
	
	public boolean overSoloInput(Vector2Int pos, Vector2Int coord, GateIndex index) {
		Vector2Int target = new Vector2Int(tileWidth * coord.x + 5,
				tileHeight * coord.y + (tileHeight / 2));
		double dist = target.squaredDistance(pos);
		if (dist <= maxDistance * maxDistance) {
			index = GateIndex.TOP;
			return true;
		}
		return false;
	}
	
	
	public boolean overDoubleInput(Vector2Int pos, Vector2Int coord, GateIndex index) {
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
	
	
	
	public boolean validateMousePosition(Vector2Int v) {
		if(boardWidth * tileWidth < v.x || boardHeight * tileHeight < v.y)
			return false;
		return true;
	}

	/**
	 * @param t
	 * @param v
	 * @param pos
	 * @param index
	 * @return
	 */
	public boolean validEndPositionOnTile(TileType t, Vector2Int v, Vector2Int pos, GateIndex index) {
		return validStartPositionOnTile(t, v, pos, index);
	}
	
	public GateIndex getGateIndexFromPositionOnTile(TileType t, Vector2Int v) {
		if(t == TileType.FALSE || t == TileType.TRUE)
			return null;
		else if (t == TileType.NOT || t == TileType.NOT_TRUE || t == TileType.FALSE) {
			if(overOutput(v, mousePositionToGridCoordinates(v)))
				return null;
			return GateIndex.TOP;
		}
		else if (t != TileType.EMPTY) {
			if(overOutput(v, mousePositionToGridCoordinates(v)))
				return null;
			
			GateIndex result = null;
			System.out.println("Checking " + v + " " + mousePositionToGridCoordinates(v));
			System.out.println(overDoubleInput(v, mousePositionToGridCoordinates(v), result));
			System.out.println(result);
			return result;
				
		}
		System.out.println("ERROR (PositionCalculator): Could not determine GateIndex " + t + " at " + v);
		return null;
	}
}