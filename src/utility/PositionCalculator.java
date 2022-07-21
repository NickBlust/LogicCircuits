package utility;

import gates.GateIndex;
import gui.LogicBoardGUI;
import gui.TileType;

/** This is a helper class for converting mouse positions
 * on the GUI to grid position / coordinates in the model,
 * and for checking where the mouse is on an 
 * individual tile / gate on the board.
 * <p>
 * The mouse position is given w.r.t. the upper left
 * corner of the grid.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class PositionCalculator {

	/** Determines how far from an input / output the user
	 * may click in order to try to create a connection.
	 */
	private int maxDistance = 10;
	
	/** The width of a tile in the GUI. */
	private int tileWidth;
	
	/** The height of a tile in the GUI. */
	private int tileHeight;
	
	/** The GUI visualizing the model. */
	private LogicBoardGUI gui;
	

	/** Create a calculator with the given parameters. 
	 * @param tileWidth_ The width of a tile in the GUI.
	 * @param tileHeight_ The height of a tile in the GUI.
	 * @param gui_ The GUI visualizing the model.
	 */
	public PositionCalculator(int tileWidth_, int tileHeight_, LogicBoardGUI gui_) {
		tileWidth = tileWidth_;
		tileHeight = tileHeight_;
		gui = gui_;
	}
	
	/** Convert the position of the mouse on the GUI
	 * to grid coordinates.
	 * @param pos Position of a mouse click.
	 * @return The coordinates of the tile / gate the mouse was over.
	 */
	public Vector2Int mousePositionToGridCoordinates(Vector2Int pos) {
		return new Vector2Int((int) Math.ceil(pos.x / tileWidth),
				(int) Math.ceil(pos.y / tileHeight));
	}
	
	/** Check if a given position is over the output or an input
	 * of a gate.
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
	
	/** Check if the mouse is over an output.
	 * @param pos Position of the mouse click.
	 * @param coord Grid position of a gate / tile.
	 * @return True iff the mouse was over the input of the gate.
	 */
	public BoolGateIndexTuple overOutput(Vector2Int pos, Vector2Int coord) {
		Vector2Int target = new Vector2Int(tileWidth * (coord.x + 1) - 4,
				tileHeight * coord.y + (tileHeight / 2));
		double dist = target.squaredDistance(pos);
		return new BoolGateIndexTuple(dist <= maxDistance * maxDistance, null);
	}
	
	
	/** Check if the mouse is over the input of a 
	 * gate / tile with only one input.
 	 * @param pos Position of the mouse click.
	 * @param coord Grid position of a gate / tile.
	 * @return True and GateIndex TOP iff the mouse was over the output of the gate.
	 */
	public BoolGateIndexTuple overSoloInput(Vector2Int pos, Vector2Int coord) {
		Vector2Int target = new Vector2Int(tileWidth * coord.x + 5,
				tileHeight * coord.y + (tileHeight / 2));
		double dist = target.squaredDistance(pos);
		if (dist <= maxDistance * maxDistance) {
			return new BoolGateIndexTuple(true, GateIndex.TOP);
		}
		return new BoolGateIndexTuple(false, null);
	}
	
	/** Check if the mouse is over one of the inputs of a 
	 * gate / tile with two inputs.
 	 * @param pos Position of the mouse click.
	 * @param coord Grid position of a gate / tile.
	 * @return True and the corresponding GateIndex iff the mouse was over an input of the gate.
	 */
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
	


	/** Check if the position of the mouse is a valid end position
	 * for a connection. Note that this is a purely cosmetic function,
	 * as any start position is also a valid end position. Whether
	 * these positions actually form a proper connection is handled
	 * elsewhere.
	 * @param t The type of tile / gate.
	 * @param v The position of the mouse.
	 * @param pos Coordinate of the tile / gate on the board as (row, column).
	 * @return
	 */
	public BoolGateIndexTuple validEndPositionOnTile(TileType t, Vector2Int v, Vector2Int pos) {
		return validStartPositionOnTile(t, v, pos);
	}
	
	/** Find out which input / output the mouse is at.
	 * @param t The type of tile / gate.
	 * @param v THe position of the mouse.
	 * @return null if the mouse was over the output,
	 * or the corresponding GateIndex if the mouse was over an input.
	 */
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
			return temp.value();
				
		}
		System.out.println("ERROR (PositionCalculator): Could not determine GateIndex " + t + " at " + v);
		return null;
	}
	
	/** Compute the position at the center of an input / output on a tile.
	 * @param t The type of tile / gate.
	 * @param coord The grid coordinates of the tile / gate.
	 * @param index Specifies the TOP, BOTTOM input or the output.
	 * @return The position at the center of the indicated input / output.
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
	
	/** Check if the mouse position actually is on the grid.
	 * @param v Position of the mouse.
	 * @return True iff the mouse is over the grid visualizing the model.
	 */
	public boolean validateMousePosition(Vector2Int v) {
		if(gui.getBoardGUIWidth()* tileWidth < v.x || gui.getBoardGUIHeight() * tileHeight < v.y)
			return false;
		return true;
	}
}