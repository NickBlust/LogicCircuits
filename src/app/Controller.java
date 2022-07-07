/**
 * 
 */
package app;

import gui.LogicBoardGUI;
import gui.TiledCanvas.TileType;
import utility.PositionCalculator;
import utility.Vector2Int;

import command.*;
import gates.Gate.GateIndex;

import java.util.Stack;

import boardModel.LogicBoard;

/**
 * @author domin
 *
 */
public class Controller {
	LogicBoardGUI theGUI;
	LogicBoard theBoard;
	public TileType selectedTileToPlace = TileType.OR;
	
	Stack<Command> pastCommands;
	PositionCalculator positionCalculator;
	
	public Controller() { }
	
	public void start() {
		pastCommands = new Stack<Command>();
		theGUI = new LogicBoardGUI(this);
		theBoard = new LogicBoard(theGUI);
		theBoard.test();
		positionCalculator = new PositionCalculator(theGUI.getTileWidth(), theGUI.getTileHeight());
	}

	/**
	 * 
	 */
	public void clickedSave() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public void clickedLoad() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public void resetBoard() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public void evaluateCircuits() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param point 
	 * 
	 */
	public void handleMouseClick(Vector2Int v) {
		Command c = new PlaceTileAt(theBoard, v, selectedTileToPlace);
		if(c.execute())
			pastCommands.add(c);	
	}

	/**
	 * @param v
	 * @return
	 */
	public boolean isValidStart(Vector2Int v) {

		// check if model has tile at said coordinate
		Vector2Int pos = positionCalculator.mousePositionToGridCoordinates(v);
		TileType t = theBoard.getGateType(pos);
		if(t != TileType.EMPTY) {
			GateIndex index = null;
			System.out.println("HELP:" + v + " " + (positionCalculator.validPositionOnTile(t, v, pos, index)));
			// if it is at the right position on the tile, return true
		}
		
		return false;
	}

}