/**
 * 
 */
package utility;

import gates.GateIndex;
import gui.TileType;

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
	public BoolGateIndexTuple validStartPositionOnTile(TileType t, Vector2Int pos, Vector2Int coord) {
		if(t == TileType.TRUE || t == TileType.FALSE) {
			return overOutput(pos, coord);
		}
		else if(t == TileType.NOT || t == TileType.NOT_TRUE || t == TileType.NOT_FALSE) {
			BoolGateIndexTuple temp = overOutput(pos, coord);
			if(temp.key) { return temp; }
			temp = overSoloInput(pos, coord);
			if(temp.key) { return temp; }
		}
		else {
			BoolGateIndexTuple temp = overOutput(pos, coord);
			if(temp.key) { return temp; }
			temp = overDoubleInput(pos, coord);
			if(temp.key) { return temp; }
		}
		return new BoolGateIndexTuple(false, null);
	}
	
	public BoolGateIndexTuple overOutput(Vector2Int pos, Vector2Int coord) {
		Vector2Int target = new Vector2Int(tileWidth * (coord.x + 1) - 4,
				tileHeight * coord.y + (tileHeight / 2));
		double dist = target.squaredDistance(pos);
		return new BoolGateIndexTuple(dist <= maxDistance * maxDistance, null);
	}
	
	
	public BoolGateIndexTuple overSoloInput(Vector2Int pos, Vector2Int coord) {
		Vector2Int target = new Vector2Int(tileWidth * coord.x + 5,
				tileHeight * coord.y + (tileHeight / 2));
		double dist = target.squaredDistance(pos);
		if (dist <= maxDistance * maxDistance) {
			return new BoolGateIndexTuple(true, GateIndex.TOP);
		}
		return new BoolGateIndexTuple(false, null);
	}
	
	
	public BoolGateIndexTuple overDoubleInput(Vector2Int pos, Vector2Int coord) {
		// check TOP input first
		Vector2Int target = new Vector2Int(tileWidth * coord.x + 5, tileHeight * coord.y + 5);
		double dist = target.squaredDistance(pos);
		if (dist <= maxDistance * maxDistance) {
			return new BoolGateIndexTuple(true, GateIndex.TOP);	
		}
		
		// check BOTTOM input second
		target = new Vector2Int(tileWidth * coord.x + 5, tileHeight * (coord.y + 1) - 4);
		dist = target.squaredDistance(pos);
		if (dist <= maxDistance * maxDistance) {
			return new BoolGateIndexTuple(true, GateIndex.BOTTOM);
		}
		
		return new BoolGateIndexTuple(false, null);
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
	public BoolGateIndexTuple validEndPositionOnTile(TileType t, Vector2Int v, Vector2Int pos) {
		return validStartPositionOnTile(t, v, pos);
	}
	
	public GateIndex getGateIndexFromPositionOnTile(TileType t, Vector2Int v) {
		if(t == TileType.FALSE || t == TileType.TRUE)
			return null;
		else if (t == TileType.NOT || t == TileType.NOT_TRUE || t == TileType.FALSE) {
			if(overOutput(v, mousePositionToGridCoordinates(v)).key())
				return null;
			return GateIndex.TOP;
		}
		else if (t != TileType.EMPTY) {
			if(overOutput(v, mousePositionToGridCoordinates(v)).key())
				return null;
			
			BoolGateIndexTuple temp = overDoubleInput(v, mousePositionToGridCoordinates(v));
//			System.out.println("Checking " + v + " " + mousePositionToGridCoordinates(v));
//			System.out.println(temp.key());
			return temp.value();
				
		}
		System.out.println("ERROR (PositionCalculator): Could not determine GateIndex " + t + " at " + v);
		return null;
	}
	
	/**
	 * Compute the position at the center of an input / output on a tile.
	 * @param t
	 * @param coord
	 * @param index
	 * @return
	 */
	public Vector2Int getLinePoint(TileType t, Vector2Int coord, GateIndex index) {
	
		// if not and not null => not input
		if((t == TileType.NOT || t == TileType.NOT_TRUE || t == TileType.NOT_FALSE) && index != null) {
			System.out.println("Drawing line at NOT gate input");
			return new Vector2Int(tileWidth * coord.x + 5, tileHeight * coord.y + (tileHeight / 2));
		}
		// else if not TRUE and not FALSE and not null => Bottom or Top input
		else if (t != TileType.TRUE && t != TileType.FALSE && index != null) {
			if(index == GateIndex.TOP) {
				return new Vector2Int(tileWidth * coord.x + 5, tileHeight * coord.y + 5);
			}
			else { // index is GateIndex.BOTTOM
				return new Vector2Int(tileWidth * coord.x + 5, tileHeight * (coord.y + 1) - 4);
			}			
		}
		// else its an output => universal formula for that
		else {
			return new Vector2Int(tileWidth * (coord.x + 1) - 4, tileHeight * coord.y + (tileHeight / 2));
		}
	}
}