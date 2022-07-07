/**
 * 
 */
package app;

import gui.LogicBoardGUI;
import gui.TiledCanvas.TileType;
import utility.BoolGateIndexTuple;
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
		positionCalculator = theGUI.getPositionCalculatorFromGUI();
		theBoard = new LogicBoard(theGUI, positionCalculator);
//		theBoard.test();
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
	 * 
	 */
	public void addConnection(Vector2Int end, Vector2Int start) {
		Command c;
		Vector2Int endCoord = positionCalculator.mousePositionToGridCoordinates(end);
		Vector2Int startCoord = positionCalculator.mousePositionToGridCoordinates(start);
		
		TileType type = theBoard.getGateType(startCoord);
		GateIndex index = positionCalculator.getGateIndexFromPositionOnTile(type, start);
		if(index != null) { // end provides the output value
			c = new ConnectGates(theBoard, endCoord, startCoord, index);
		}
		else { // start provides the output value
			type = theBoard.getGateType(endCoord);
			index = positionCalculator.getGateIndexFromPositionOnTile(type, end);
			c = new ConnectGates(theBoard, startCoord, endCoord, index);
		}

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
//			System.out.println("HELP:" + v + " " + (positionCalculator.validPositionOnTile(t, v, pos, index)));
			// if it is at the right position on the tile, return true
			return positionCalculator.validStartPositionOnTile(t, v, pos).key();
		}
		
		return false;
	}

	/**
	 * @param v Position of mouse release
	 * @param start Start of the current line
	 * @return
	 */
	public boolean isValidEnd(Vector2Int v, Vector2Int start) {
		Vector2Int startCoord = positionCalculator.mousePositionToGridCoordinates(start); // start of line
		Vector2Int endCoord = positionCalculator.mousePositionToGridCoordinates(v); // end of line
		TileType t = theBoard.getGateType(endCoord); // Tile at end of line
		
		if(t != TileType.EMPTY && !startCoord.equals(endCoord)) {
			GateIndex endIndex = null;
			// hit an input or output on another gate?
			BoolGateIndexTuple preliminaryResult = positionCalculator.validEndPositionOnTile(t, v, endCoord);
			if(!preliminaryResult.key())
				return false;
			endIndex = preliminaryResult.value();
			
			// check if we are connecting from an input to an output or vice versa,
			// i.e. do not allow connections from one input to another input etc.
			GateIndex startIndex = positionCalculator
					.getGateIndexFromPositionOnTile(theBoard.getGateType(startCoord), start);
//			System.out.println("Comparing " + theBoard.getGateType(startCoord) + " index " 
//					+ startIndex + " at " + startCoord + "  with  " 
//					+ t + " index " + endIndex + " at " + endCoord);
			if(startIndex == null && !(endIndex == GateIndex.TOP || endIndex == GateIndex.BOTTOM)) {
				System.out.println("1");				
				return false;
			}
			else if(startIndex != null && endIndex != null) {
				System.out.println("2");				
				return false;
			}
			
			
			// check if we would form a cycle
			return !theBoard.formsCycle(startCoord, endCoord, startIndex, endIndex);
			
		}
		return false;
	}
}