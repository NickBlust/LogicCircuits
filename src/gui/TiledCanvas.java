package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JPanel;

import gates.Status;
import utility.PointTupleWithStatus;
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
	 * {@link boardModel.LogicBoard model}.
	 */
	public TreeMap<Vector2Int, TileType> tilesToDraw;
	
	
	/** Store the endpoints of each connection that needs
	 * to be drawn. That information is provided by the
	 * {@link boardModel.LogicBoard model}.
	 */
	public ArrayList<PointTupleWithStatus> connectionsToDraw;
	
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

	/** Color used for connections, where the output at the start of the connection is TRUE. */
	private Color customGreen = new Color(95, 208, 84);

	/** Color used for connections, where the output at the start of the connection is FALSE. */
	private Color customRed = new Color(208, 84, 84);
	
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
		connectionsToDraw = new ArrayList<PointTupleWithStatus>();
		repaint();
	}

	
	/************** DRAWING THIS COMPONENT **************/
	
	
	@Override
	public void paintComponent(Graphics g) {
		setPreferredSize(new Dimension(boardGUI.getBoardGUIWidth() * boardGUI.getTileWidth(),
				boardGUI.getBoardGUIHeight() * boardGUI.getTileHeight()));
		super.paintComponent(g);
		drawBoard(g); // draw an empty board 
		drawTiles(g); // draw the gates onto the board
		drawConnections(g); // draw the connections between the gates
		/* when the user clicked somewhere without releasing and is dragging the mouse
		 * draw a line between the original click position and the current mouse position. */
		if(drawTentativeLine) { 
			g.setColor(Color.BLACK);
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
		for(PointTupleWithStatus c : connectionsToDraw) {
			// fit the color according to the gate's output's status
			if(c.status.equals(Status.UNEVALUATED))
				g.setColor(Color.BLACK);
			else if(c.status.equals(Status.TRUE))
					g.setColor(customGreen);
			else 
				g.setColor(customRed);
			g.drawLine(c.a.x, c.a.y, c.b.x, c.b.y);
		}		
	}
	
	
	
	/** This is just here because Eclipse complained about it. */
	private static final long serialVersionUID = 1L;
}