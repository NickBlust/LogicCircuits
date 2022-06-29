package gui;

import java.util.ArrayList;

import logicCircuits.LogicCircuit;
import utility.ConnectionInfo;
import utility.EvaluationInfo;
import utility.Vector2Int;

/**
 * The BoardEditor class is responsible for managing information about the board.
 * @author cmcgregor
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
    private TileType[][] tiles;
    
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
        tiles = new TileType[BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = 0; i < BOARD_WIDTH; i++)
        {
            for (int i2 = 0; i2 < BOARD_HEIGHT; i2++)
            {
                tiles[i][i2] = TileType.EMPTYTILE;
            }
        }
        return tiles;
    }

    /**
     * This method initiates the board.
     */
    public void startBoard() 
    {
        tiles = generateBoard();
        gui.updateDisplay(tiles);
    }
    
    public void selectTileToPlace(TileType t) { 
    	if(t == tileToPlace)
    		tileToPlace = null;
    	else
    		tileToPlace = t;
    	System.out.println("Selected " + tileToPlace);
    	}
    
    /**
     * Add the {@link BoardEditor.tileToPlace currently selected type of gate}
     * to the underlying model at the given position.
     * @param v Position of the new gate on the board.
     */
    public void placeTile(Vector2Int v) {
    	tiles[v.x][v.y] = tileToPlace;
    	logicCircuit.addGate(tileToPlace, v.x, v.y);
    }
    
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
    
    public void removeConnection(ConnectionInfo c) {
    	logicCircuit.unconnectGate(c);
    }
    
    public ArrayList<EvaluationInfo> evaluateAndVisualizeModel() {
    	return logicCircuit.evaluateAndVisualize();
    }
}