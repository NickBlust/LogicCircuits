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
		positionCalculator = theGUI.getPositionCalculatorFromGUI();
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
//			System.out.println("HELP:" + v + " " + (positionCalculator.validPositionOnTile(t, v, pos, index)));
			// if it is at the right position on the tile, return true
			return positionCalculator.validStartPositionOnTile(t, v, pos, index);
		}
		
		return false;
	}

	/**
	 * @param v Position of mouse release
	 * @param start Start of the current line
	 * @return
	 */
	public boolean isValidEnd(Vector2Int v, Vector2Int start) {
		Vector2Int pos = positionCalculator.mousePositionToGridCoordinates(v);
		Vector2Int startCoord = positionCalculator.mousePositionToGridCoordinates(start);
		TileType t = theBoard.getGateType(pos);
		
		if(t != TileType.EMPTY && !startCoord.equals(pos)) {
			GateIndex index = null;
			// hit an input or output on another gate?
			boolean preliminaryResult = positionCalculator.validEndPositionOnTile(t, v, pos, index);
			if(!preliminaryResult)
				return false;
			
			// check if we are connecting from an input to an output or vice versa,
			// i.e. do not allow connections from one input to another input etc.
			GateIndex otherIndex = positionCalculator
					.getGateIndexFromPositionOnTile(theBoard.getGateType(pos), v);
			System.out.println("Comparing " + t + " index " + index + " at " + startCoord
					+ "  with  " + theBoard.getGateType(pos) + " index " + otherIndex + " at " + pos);
			
			
			
			// check if we would form a cycle
			return !theBoard.formsCycle(start, v, index);
			
		}
		return false;
	}

}