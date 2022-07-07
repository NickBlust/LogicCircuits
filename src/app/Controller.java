/**
 * 
 */
package app;

import gui.LogicBoardGUI;
import gui.TiledCanvas.TileType;
import utility.Vector2Int;

import command.*;

import java.util.Stack;

import boardModel.LogicBoard;

/**
 * @author domin
 *
 */
public class Controller {
	LogicBoardGUI theGUI;
	LogicBoard theBoard;
	TileType selectedTileToPlace = TileType.OR;
	
	Stack<Command> pastCommands;
	
	public Controller() { }
	
	public void start() {
		pastCommands = new Stack<Command>();
		theGUI = new LogicBoardGUI(this);
		theBoard = new LogicBoard(theGUI);
		theBoard.test();
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

}