package gui;

import app.BoardLoader;
import app.BoardSaver;
import gates.Gate;
import gates.TileConverter;

import java.util.ArrayList;

import logicCircuits.LogicCircuit;
import utility.ConnectionInfo;
import utility.EvaluationInfo;
import utility.Vector2Int;

/**
 * The BoardEditor class is responsible for managing information about the board.
 * @author cmcgregor, Dominik Baumann (latter is responsible for the horrible parts)
 */
public class BoardEditor 
{
    /**
     * An enumeration type to represent different types of tiles that make up
     * the board. Each type has a corresponding image file that is used
     * to draw the right tile to the screen.
     */
    public enum TileType 
    {
        EMPTYTILE, FALSE, TRUE,
        AND, NAND, NOR, NOT, OR, XOR,
        AND_TRUE, NAND_TRUE, NOR_TRUE, NOT_TRUE, OR_TRUE, XOR_TRUE,
        AND_FALSE, NAND_FALSE, NOR_FALSE, NOT_FALSE, OR_FALSE, XOR_FALSE;
    }

    /**
     * The width of the board, measured in tiles. Changing this may
     * cause the display to draw incorrectly, and as a minimum the size of the
     * GUI would need to be adjusted.
     */
    public int BOARD_WIDTH = 10;
    
    /**
     * The height of the board, measured in tiles. Changing this may
     * cause the display to draw incorrectly, and as a minimum the size of the
     * GUI would need to be adjusted.
     */
    public int BOARD_HEIGHT = 8;

    /**
     * The GUI associated with a BoardEditor object. This link allows the editor
     * to pass information to the GUI to be drawn.
     */
    private BoardGUI gui;

    
    /**
     * The underlying model for the logic board.
     */
    private LogicCircuit logicCircuit;
    
    /**
     * The 2 dimensional array of tiles the represent the current board.
     * The size of this array should use the BOARD_HEIGHT and BOARD_WIDTH
     * attributes when it is created.
     */
//    private TileType[][] tiles;
    
    /**
     * The tile to place on the board. 
     * Equals null, if connections between gates are drawn.
     */
    public TileType tileToPlace;

    /**
     * Constructor that creates a BoardEditor object and connects it with a BoardGUI
     * object.
     * @param gui The BoardGUI object that this editor will pass information to in
     * order to draw the board.
     */
    public BoardEditor(BoardGUI gui) 
    {
        this.gui = gui;
        this.logicCircuit = new LogicCircuit(BOARD_WIDTH, BOARD_HEIGHT);
    }

    /**
     * Generates a new board. The method builds a 2D array of TileType values
     * that will be used to draw tiles to the screen. Tiles can be empty, AND gates,
     * Or gates etc.
     * @return A 2D array of TileTypes representing the tiles in the current
     * board. The size of this array should use the width and height of the 
     * board.
     */
    private TileType[][] generateBoard() 
    {
        TileType[][] tiles = new TileType[BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = 0; i < BOARD_WIDTH; i++)
        { for (int i2 = 0; i2 < BOARD_HEIGHT; i2++) {
            tiles[i][i2] = TileType.EMPTYTILE; }
        }
        return tiles;
    }

    /**
     * This method initiates the board.
     */
    public void startBoard() 
    {
        gui.updateDisplay(generateBoard());
    }
    
    /**
     * When clicking a button with a Gate in the GUI
     * call this function to select which Type of tile is 
     * to be drawn upon a click on the board.
     * If the same Gate-button is clicked again, the tile is set to null.
     * @param t The tile to draw onto the board.
     */
    public void selectTileToPlace(TileType t) { 
    	if(t == tileToPlace)
    		tileToPlace = null;
    	else
    		tileToPlace = t;
    	System.out.println("BoardEditor: Selected " + tileToPlace);
    	}
    
    /**
     * Add the type of gate currently selected in the {@link gui.BoardEditor BoardEditor}
     * to the underlying model at the given position.
     * @param v Position of the new gate on the board.
     */
    public void placeTile(Vector2Int v) {
//    	tiles[v.x][v.y] = tileToPlace;
    	logicCircuit.addGate(tileToPlace, v.x, v.y);
    }
    
    /**
     * Remove a gate from the board and the model.
     * @param v Position of the gate.
     */
    public void removeGate(Vector2Int v) {
//    	tiles[v.x][v.y] = TileType.EMPTYTILE;
    	logicCircuit.removeGate(v.x, v.y);
    }
    
    /** Remove all gates from the model */
    public void removeAllGates() { logicCircuit.removeAllGates(); }
    
    /** 
     * Create a connection between two gates in the underlying model.
     * @param inputPos Position of the gate whose output is used as input.
     * @param targetPos Position of the gate whose input is to be set.
     * @param id Determines which input of the gate is set - is either 1 or 2;
     */
    public void addConnection(Vector2Int inputPos, Vector2Int targetPos, int id) {
    	// Gates work with identifier 0 or 1 => subtract 1 from the id before passing it on!
    	logicCircuit.connectGates(inputPos.x, inputPos.y, targetPos.x, targetPos.y, id - 1);
    }
    
    /** Remove a connection between to gates in the model.
     * @param c Contains the information which connection to remove.
     */
    public void removeConnection(ConnectionInfo c) {
    	logicCircuit.disconnectGate(c);
    }
    
    /**
     * @return A list with information for each gate on the board about
     * 			the type of gate
     * 			its position
     * 			and its truth value under evaluation.
     */
    public ArrayList<EvaluationInfo> evaluateAndVisualizeModel() {
    	return logicCircuit.evaluateAndVisualize();
    }
    
    /** Save the board to a .txt-file. */
    public void saveBoard() { BoardSaver.save(logicCircuit); }
    
    /**
     * Loads a board from a .txt file and updates the GUI.
     * @param someGUI The current GUI.
     */
    public void loadBoard(BoardGUI someGUI) {
    	LogicCircuit loadedBoard = BoardLoader.load();
    	if(loadedBoard != null) {
    		logicCircuit = loadedBoard;
    		ArrayList<ArrayList<Gate> > newBoard = logicCircuit.getBoard();
    		someGUI.canvas.currentTiles = new TileType[logicCircuit.getModelDimensions().x][logicCircuit.getModelDimensions().y];
    		for(int row = 0; row < logicCircuit.getModelDimensions().x; row++) {
    			for(int col = 0; col < logicCircuit.getModelDimensions().y; col++) {
    				someGUI.canvas.currentTiles[row][col] = TileConverter.getTileTypeFromGate(newBoard.get(row).get(col));
    			}
    		}
    		ArrayList<ConnectionInfo> connections = logicCircuit.getConnections();
    		someGUI.clearConnections();
    		for(ConnectionInfo c : connections) {
    			someGUI.addConnectionToGUI(c);
    		}
    	}
    }

	/** Check if a gate already receives input.
	 * @param end Position of the target (the gate receiving input).
	 * @param inputIndex Which gate on the target is under investigation?
	 * @return True if the gate already has a connection with the given index, false otherwise.
	 */
	public boolean alreadyHasConnection(Vector2Int end, int inputIndex) {
		return logicCircuit.hasConnection(end, inputIndex);
	}
}