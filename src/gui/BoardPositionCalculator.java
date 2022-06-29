/**
 * 
 */
package gui;

import utility.Vector2Int;
import java.lang.Math;

/**
 * @author Dominik Baumann
 *
 */
public class BoardPositionCalculator {
	BoardEditor boardEditor;
//	BoardGUI boardGUI;
	
	public BoardPositionCalculator(BoardEditor be/*, BoardGUI bg*/) {
		boardEditor = be;
//		boardGUI = bg;
	}
	
	
	public Vector2Int GetTileIndices(Vector2Int v) {
		if (v.x > BoardGUI.TILE_WIDTH * boardEditor.BOARD_WIDTH
				|| v.y > BoardGUI.TILE_HEIGHT * boardEditor.BOARD_HEIGHT) {
			return null;			
		}
		return new Vector2Int((int) Math.ceil(v.x / BoardGUI.TILE_WIDTH), (int) Math.ceil(v.y / BoardGUI.TILE_HEIGHT));
	}
	
	public Vector2Int GetTileIndices(int x, int y) {
		if (x > BoardGUI.TILE_WIDTH * boardEditor.BOARD_WIDTH
				|| y > BoardGUI.TILE_HEIGHT * boardEditor.BOARD_HEIGHT) {
			return null;			
		}
		return new Vector2Int((int) Math.ceil(x / BoardGUI.TILE_WIDTH), (int) Math.ceil(y / BoardGUI.TILE_HEIGHT));
	}
}