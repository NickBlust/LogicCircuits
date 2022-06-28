package gui;

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
        EMPTYTILE, OR, AND //PLUS ANY ADDITIONAL ONES NEEDED
    }

    /**
     * The width of the board, measured in tiles. Changing this may
     * cause the display to draw incorrectly, and as a minimum the size of the
     * GUI would need to be adjusted.
     */
    public static final int BOARD_WIDTH = 10;
    
    /**
     * The height of the board, measured in tiles. Changing this may
     * cause the display to draw incorrectly, and as a minimum the size of the
     * GUI would need to be adjusted.
     */
    public static final int BOARD_HEIGHT = 8;

    /**
     * The GUI associated with a BoardEditor object. This link allows the editor
     * to pass information to the GUI to be drawn.
     */
    private BoardGUI gui;

    /**
     * The 2 dimensional array of tiles the represent the current board.
     * The size of this array should use the BOARD_HEIGHT and BOARD_WIDTH
     * attributes when it is created.
     */
    private TileType[][] tiles;

    /**
     * Constructor that creates a BoardEditor object and connects it with a BoardGUI
     * object.
     * @param gui The BoardGUI object that this editor will pass information to in
     * order to draw the board.
     */
    public BoardEditor(BoardGUI gui) 
    {
        this.gui = gui;
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
}
