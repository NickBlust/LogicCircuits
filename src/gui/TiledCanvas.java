package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JPanel;

import utility.PointTuple;
import utility.Vector2Int;

/** This class implements a board with grids ("tiles")
 * on it. It is the visual core element of the GUI, where
 * the user can actually construct their model of a
 * {@link boardModel.LogicBoard Logic Circuits Board}
 * by placing {@link gates.Gate gates} and drawing connections.
 * @author Dominik Baumann, Philipp Grzywaczyk, Cameron McGregor
 * @version 2, July 2022
 * @see boardModel.LogicBoard
 * @see gates.Gate
 */
public class TiledCanvas extends JPanel {

	
	/** Store the positions and the types of gates
	 * to draw. That information is provided by the 
	 * {@link boardModel.logicBoard model}.
	 */
	public TreeMap<Vector2Int, TileType> tilesToDraw;
	
	
	/** Store the endpoints of each connection that needs
	 * to be drawn. That information is provided by the
	 * {@link boardModel.logicBoard model}.
	 */
	public ArrayList<PointTuple> connectionsToDraw;
	
	/** The {@link gui.LogicBoardGUI GUI parent} this GUI element sits on. */
	LogicBoardGUI boardGUI;
	
	/** Basically a look-up table for the images of
	 * the tiles that can be placed on the board.
	 */
	ImageStorage images;
	
	/** The image of an empty tile (i.e. a cell without a gate)
	 * will be needed very often, so store it.
	 */
	private BufferedImage emptyTileImage;
	
	/** The width of an individual tile / cell / gate image (in pixels). */
	private int tileWidth;

	/** The height of an individual tile / cell / gate image (in pixels). */
	private int tileHeight;
	
	// Some variables are required for drawing a line for previewing a connection.
	
	/** True iff the user clicked any mouse button over 
	 * the output or an input of a gate on the board
	 * and is still pressing the button. 
	 */
	public boolean startedDragging = false;
	
	/** True iff a line to preview a connection should be drawn. */
	public boolean drawTentativeLine = false;
	
	/** The start of the line for previewing a connection. */
	public Vector2Int lineStart;
	
	/** The end of the line for previewing a connection
	 * (i.e. the current mouse cursor position). */
	public Vector2Int lineEnd;

	
	/** Constructor for the visualization of a grid,
	 * on which the user can place gates and draw connection.
	 * @param boardGUI_ The {@link gui.LogicBoardGUI GUI parent} this GUI element sits on.
	 * @param imageStorage A look-up table for the images of
	 * the tiles that can be placed on the board
	 */
	public TiledCanvas(LogicBoardGUI boardGUI_, ImageStorage imageStorage) {
		images = imageStorage;
		boardGUI = boardGUI_;
		tileWidth = boardGUI.getTileWidth();
		tileHeight = boardGUI.getTileHeight();
		emptyTileImage = images.getImage(TileType.EMPTY);
		tilesToDraw = new TreeMap<Vector2Int, TileType>();
		connectionsToDraw = new ArrayList<PointTuple>();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoard(g); // draw an empty board 
		drawTiles(g); // draw the gates onto the board
		drawConnections(g); // draw the connections between the gates
		/* when the user clicked somewhere without releasing and is dragging the mouse
		 * draw a line between the original click position and the current mouse position. */
        if(drawTentativeLine) { 
        	g.drawLine(lineStart.x, lineStart.y,  lineEnd.x, lineEnd.y);
        }
	}

	/** Draws the empty board.
     * @param g ()
     */
    private void drawBoard(Graphics g) 
    {
    	int columns = boardGUI.getBoardGUIWidth();
    	int rows = boardGUI.getBoardGUIHeight();
    	Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < columns; i++) {
            for (int k = 0; k < rows; k++) {
            	g2.drawImage(emptyTileImage, 
            			i * tileWidth, 
            			k * tileHeight, null);            			
            }
        }
	}
    
    /** Draws all gates (i.e. the tiles representing them) on the board.
     * @param g ()
     */
    public void drawTiles(Graphics g) {
    	for(Vector2Int key : tilesToDraw.keySet()) {
            Graphics2D g2 = (Graphics2D) g;
    		g2.drawImage(images.getImage(tilesToDraw.get(key)),
    				key.x * tileWidth, 
            		key.y * tileHeight, null);
    	}
    }
    
    /** Draw all connections between gates on the board.
	 * @param g ()
	 */
	private void drawConnections(Graphics g) {
		for(PointTuple c : connectionsToDraw) {
			g.drawLine(c.a.x, c.a.y, c.b.x, c.b.y);
		}		
	}
	
	/** This is just here because Eclipse complained about it. */
	private static final long serialVersionUID = 1L;
}