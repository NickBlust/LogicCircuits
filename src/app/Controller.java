/**
 * 
 */
package app;

import gui.LogicBoardGUI;
import gui.TileType;
import utility.BoolGateIndexTuple;
import utility.PositionCalculator;
import utility.Vector2Int;
import utility.Vector3Int;
import command.*;
import gates.GateIndex;

import java.util.Stack;

import boardModel.LogicBoard;

/**
 * @author domin
 *
 */
public class Controller {
	LogicBoardGUI theGUI;
	LogicBoard theBoard;
	public TileType selectedTileToPlace = null;//TileType.EMPTY;
	
	Stack<Command> pastCommands;
	PositionCalculator positionCalculator;
	
	public Controller() { }
	
	private void executeCommand(Command c) {
		if(c.execute())
			pastCommands.add(c);
	}
	
	public void start() {
		pastCommands = new Stack<Command>();
		theGUI = new LogicBoardGUI(this);
		positionCalculator = theGUI.getPositionCalculatorFromGUI();
		theBoard = new LogicBoard(theGUI);
	}

	/**
	 * 
	 */
	public void clickedSave() {
		BoardSaver.save(theBoard, theGUI.getBoardGUIWidth(), theGUI.getBoardGUIHeight());
	}

	/**
	 * 
	 */
	public void clickedLoad() {
		if(BoardLoader.load(theBoard, theGUI))
			pastCommands = new Stack<Command>();		
	}

	/**
	 * 
	 */
	public void resetBoard() {
		Command c = new ResetBoard(theBoard);
		executeCommand(c);
	}

	/**
	 * 
	 */
	public void evaluateCircuits() { 
		theBoard.evaluate(); 
		pastCommands = new Stack<Command>();
	}

	/** add / remove gates & add connections with left click
	 * @param point 
	 * 
	 */
	public void handleLeftClick(Vector2Int v) {
		if(selectedTileToPlace == null) { return; }
		Command c;
		
		if (selectedTileToPlace != TileType.EMPTY)
			c = new PlaceGateAt(theBoard, v, selectedTileToPlace);
		else if(!theBoard.hasGate(v))
			return; // nothing to do
		else // delete a tile
			c = new RemoveGateAt(theBoard, v);

		executeCommand(c);
	}

	/** remove connections with right click, if they are close to a connection
	 * @param v
	 */
	public void handleRightClick(Vector2Int v) {
		Command c = new TryToRemoveConnection(theBoard, new Vector3Int(v));
		executeCommand(c);
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

		executeCommand(c);
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
		
		if(t == TileType.EMPTY || startCoord.equals(endCoord)) 
			return false;

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
		if(startIndex == null && !(endIndex == GateIndex.TOP || endIndex == GateIndex.BOTTOM)) 
			return false; 
		else if(startIndex != null && endIndex != null) 
			return false;
		
		
		// check if we would form a cycle
		return !theBoard.formsCycle(startCoord, endCoord, startIndex, endIndex);
	}

}