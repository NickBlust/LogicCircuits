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
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 * <p>
 * TODO The central part of the program, which facilitates communication between
 * the gui and the model, but the gui cannot change the model /view-model-controller model and stuff ... TODO
 */
public class Controller {
	
	/**	The internal representation / model of a {@link boardModel.LogicBoard Logic Circuit Board}. */
	private LogicBoard theBoard;
	
	/**	The view / GUI displaying the {@link app.Controller#theBoard model}
	 * of a {@link boardModel.LogicBoard Logic Circuit Board}. */
	private LogicBoardGUI theGUI;
	
	/**	Provides a method for loading a model from a .txt-file. */
	private BoardSaver boardSaver;
	
	/**	Provides a method for saving the current model as a .txt-file. */
	private BoardLoader boardLoader;
	
	/** Allows verification of positions on the visualization 
	 * of the board and individual tiles / gates.
	 */
	private PositionCalculator positionCalculator;
	
	/** Stores commands that have been executed, such as placing tiles or drawing connections. 
	 * <p> Such commands can be undone (see "Edit"-menu) and TODO redone.
	 * <p> The storage of commands is emptied when
	 * <ol>
	 * <li> the board is evaluated, and
	 * <li> when a board is successfully loaded from a file.
	 * </ol>
	 */
	private Stack<Command> pastCommands;
	private Stack<Command> undoneCommands;
	
	/**	What is the current type of {@link gates.Gate Gate} to be added to the model?
	 * Equals "null" if no tile is to be placed when clicking on the GUI. 
	 */
	private TileType selectedTileToPlace = null;
	
	
	
	/** The constructor sets up the GUI and model, as well as any other required internals. */
	public Controller() { 
		theGUI = new LogicBoardGUI(this);
		positionCalculator = theGUI.getPositionCalculatorFromGUI();
		theBoard = new LogicBoard(theGUI);
		boardLoader = new BoardLoader();
		boardSaver = new BoardSaver();
		resetUndoableCommands();
		resetRedoableCommands();
	}


	
	/************** HANDLE USER INPUT **************/

	/** Having performed a left-click on the GUI
	 * will cause the controller to attempt to remove 
	 * (if the  EMPTY Tile was 
	 * {@link app.Controller#selectedTileToPlace selected in the GUI})
	 * or add (otherwise) gates.
	 * <p>
	 * <b> IMPORTANT NOTE:</b>
	 * <p> The mouse must not be moved (dragged) at all in between clicking and releasing the left
	 * mouse button! (otherwise the mouse event is registered as drag, which leads to drawing
	 * potential connections)
	 * @param v The position of the (left) mouse click which has already been translated to 
	 * coordinates in the grid, i.e. "v" is of the form "v = (row, column)". // TODO row column shenanigans
	 */
	public void handleLeftClick(Vector2Int v) {
		// don't do anything if no tile / gate is selected
		if(selectedTileToPlace == null) { return; }
		
		// otherwise create a command and specify it
		Command c;
		if (selectedTileToPlace != TileType.EMPTY) // place a gate
			c = new PlaceGateAt(theBoard, v, selectedTileToPlace);
		else if(!theBoard.hasGate(v)) // can't remove a gate if there is none
			return; // nothing to do
		else // delete a tile / gate
			c = new RemoveGateAt(theBoard, v);

		executeCommand(c);
	}

	
	
	/** Having performed a right click, the controller attempts
	 * to remove a connection between two gates, if the click was close enough
	 * to a connection.
	 * <p>
	 * Clicking close to an intersection of connections will remove the one
	 * that was placed more recently.
	 * @param v The position of the (right) mouse click, NOT in grid coordinates.
	 */
	public void handleRightClick(Vector2Int v) {
		Command c = new TryToRemoveConnection(theBoard, new Vector3Int(v));
		executeCommand(c);
	}
	
	
	
	/************** VERIFY USER INPUT **************/
	
	/** This function checks if a given position on the board
	 * is a valid start for a connection, i.e. the input or output
	 * of a {@link gates.Gate Gate}.
	 * <p>
	 * It first checks for the existence of a {@link gates.Gate Gate},
	 * and then has the exact position on the gate verified.
	 * @param v (World) position on the GUI / model.
	 * @return "true" iff position was over an input or the output of a {@link gates.Gate Gate}.
	 */
	public boolean isValidStart(Vector2Int v) {
		Vector2Int pos = positionCalculator.mousePositionToGridCoordinates(v);
		TileType t = theBoard.getGateType(pos);
		if(t != TileType.EMPTY) { // model has tile / gate at this coordinate
			return positionCalculator.validStartPositionOnTile(t, v, pos).key();
		}		
		return false;
	}

	
	
	/** This function checks if a given position on the board
	 * is a valid end for a connection, i.e. the input or output 
	 * of a  {@link gates.Gate Gate}.
	 * @param v Position under investigation.
	 * @param start Start of the current (potential) connection.
	 * @return "true" iff position is over an input or the output of a {@link gates.Gate Gate}
	 * AND the connection does not form a cycle.
	 */
	public boolean isValidEnd(Vector2Int v, Vector2Int start) {
		Vector2Int startCoord = positionCalculator.mousePositionToGridCoordinates(start); // start of line
		Vector2Int endCoord = positionCalculator.mousePositionToGridCoordinates(v); // end of line
		TileType t = theBoard.getGateType(endCoord); // Tile at end of line
		
		if(t == TileType.EMPTY || startCoord.equals(endCoord)) 
			return false; // no connection to non-existing tiles or the same tile

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
		
		// check if a cycle would form
		return !theBoard.formsCycle(startCoord, endCoord, startIndex, endIndex);
	}
	

	
	/************** IMPLEMENT COMMANDS FROM MENUS **************/
	
	/** Save the current board to a file. */
	public void saveToFile() {
		boardSaver.save(theBoard, theGUI.getBoardGUIWidth(), theGUI.getBoardGUIHeight());
	}

	
	
	/** Empties the board and tries to load a board model from a .txt-file.
	 * If loading fails, restore the board that was present before loading was attempted.
	 * <P>
	 * Successful loading empties the {@link app.Controller#pastCommands stack of undoable commands}.
	 */
	public void loadFromFile() {
		resetBoard(); // issue command to clear the board / GUI
		if(boardLoader.load(theBoard, theGUI)) {
			resetUndoableCommands();
			resetRedoableCommands();
		}
		else // if loading fails, undo resetting the board 
			undoCommand();
	}

	
	
	/** Removes all gates (and thus also connections) from the current model.
	 * The model notifies the GUI to update afterwards.
	 */
	public void resetBoard() {
		Command c = new ResetBoard(theBoard);
		executeCommand(c);
	}

	
	
	/** Display the value of the outputs of all gates on the board.
	 * <p> Green --> output of this gate is true, Red --> output of this gate is false
	 */
	public void evaluateCircuits() { 
		theBoard.evaluate(); 
		resetUndoableCommands();
		resetRedoableCommands();
	}

	
	
	/** Connect two gates at the given locations (in world coordinates). 
	 * <p>
	 * The GUI does not provide any information on how to connect the gates,
	 * i.e. the controller needs to figure out which gate is providing its output
	 * and which gate is receiving input (and whether it iss 
	 * {@link gates.GateIndex#BOTTOM BOTTOM} or {@link gates.GateIndex#TOP TOP} input).
	 * @param end one of the gates to connect
	 * @param start one of the gates to connect
	 */
	public void addConnection(Vector2Int end, Vector2Int start) {
		Command c;
		// convert to grid coordinates
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


	
	/********************* HELPERS *********************/
	
	/** Executes a given command and places it on the stack if appropriate.
	 * @param c A command to execute.
	 * @see Command
	 */
	private void executeCommand(Command c) {
		if(c.execute()) {
			try { 
				pastCommands.push(c);				
			} catch (StackOverflowError ex) {
				pastCommands = new Stack<Command>();
				pastCommands.push(c);				
			}
			theGUI.updateUndoMenu(pastCommands.size());
			resetRedoableCommands();
		}
	}

	
	
	/** Undo the most recent command on the stack (if there is any).
	 * TODO place undone commands on the redo stack
	 */
	public void undoCommand() {
		if(pastCommands.size() > 0) {
			Command c = pastCommands.pop();
			try { 
				undoneCommands.push(c);				
			} catch (StackOverflowError ex) {
				undoneCommands = new Stack<Command>();
				undoneCommands.push(c);				
			}
			System.out.println("Undoing " + c.getClass());
			c.undo();
			theGUI.updateUndoMenu(pastCommands.size());
			theGUI.updateRedoMenu(undoneCommands.size());
		}
	}
	
	public void redoCommand() {
		if (undoneCommands.size() > 0) {
			Command c = undoneCommands.pop();
			System.out.println("Redoing " + c.getClass());
			try { 
				pastCommands.push(c);				
			} catch (StackOverflowError ex) {
				pastCommands = new Stack<Command>();
				pastCommands.push(c);				
			}
			c.execute();
			theGUI.updateRedoMenu(undoneCommands.size());
			theGUI.updateUndoMenu(pastCommands.size());
		}
	}


	
	/** Empty the stack with undoable commands
	 * and disable the "Undo" button in the "Edit" menu. */
	private void resetUndoableCommands() {
		pastCommands = new Stack<Command>();
		theGUI.updateUndoMenu(pastCommands.size());
	}
	
	/** Empty the stack with redoable commands
	 * and disable the "Redo" button in the "Edit" menu. */
	private void resetRedoableCommands() {
		undoneCommands = new Stack<Command>();
		theGUI.updateRedoMenu(undoneCommands.size());
	}


	
	/** @return The count of undoable commands on the corresponding stack of commands. */
	public int numberOfUndoableCommands() { return pastCommands.size(); }

	
	
	/** Set which type of {@link gates.Gate Gate} is to be placed on the GUI / in the model
	 * when clicking on the GUI.
	 * @param t Encodes the type of {@link gates.Gate Gate} to place.
	 */
	public void setSelectedTileToPlace(TileType t) { selectedTileToPlace = t; }
	
	
	
	/**
	 * @param t Encodes a type of {@link gates.Gate Gate} to compare to the current selection.
	 * @return "true" iff the type of {@link gates.Gate Gate} to be placed equals the argument.
	 */
	public boolean isSelectedTileToPlace(TileType t) { 
		try {
		return selectedTileToPlace.equals(t); 
		} catch(NullPointerException ex) { // selectedTileToPlace was null
			return t == null;
		}
	}	
}