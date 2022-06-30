/**
 * 
 */
package gui;

import utility.Vector2Int;
import java.lang.Math;

/**
 * Class for computing the position of a tile in the grid.
 * @author Dominik Baumann
 */
public class BoardPositionCalculator {
	/** The associated boardEditor. */
	BoardEditor boardEditor;

	
	/** Constructor
	 * @param be the target {@link gui.BoardEditor BoardEditor}
	 */
	public BoardPositionCalculator(BoardEditor be) {
		boardEditor = be;
	}
	
	
	/** 
	 * Get the position of a tile in the grid based on the gui position.
	 * @param v GUI position
	 * @return grid position
	 */
	public Vector2Int GetTileIndices(Vector2Int v) {
		if (v.x > BoardGUI.TILE_WIDTH * boardEditor.BOARD_WIDTH
				|| v.y > BoardGUI.TILE_HEIGHT * boardEditor.BOARD_HEIGHT) {
			return null;			
		}
		return new Vector2Int((int) Math.ceil(v.x / BoardGUI.TILE_WIDTH), (int) Math.ceil(v.y / BoardGUI.TILE_HEIGHT));
	}
	
	/**
	 * Get the position of a tile in the grid based on the gui position.
	 * @param x (GUI position).x
	 * @param y (GUI position).y
	 * @return grid position
	 */
	public Vector2Int GetTileIndices(int x, int y) {
		if (x > BoardGUI.TILE_WIDTH * boardEditor.BOARD_WIDTH
				|| y > BoardGUI.TILE_HEIGHT * boardEditor.BOARD_HEIGHT) {
			return null;			
		}
		return new Vector2Int((int) Math.ceil(x / BoardGUI.TILE_WIDTH), (int) Math.ceil(y / BoardGUI.TILE_HEIGHT));
	}
}