/**
 * 
 */
package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JPanel;

import utility.PointTuple;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class TiledCanvas extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TreeMap<Vector2Int, TileType> tilesToDraw;
	public ArrayList<PointTuple> connectionsToDraw;
	
	/** The {@link gui.LogicBoardGUI gui parent} this canvas sits on. */
	LogicBoardGUI boardGUI;
	ImageStorage images;
	BufferedImage emptyTileImage;
	private int tileWidth;
	private int tileHeight;
	
	// Stuff required for drawing a line for previewing a connection.
	public boolean startedDragging = false;
	public boolean drawTentativeLine = false;
	public Vector2Int lineStart;
	public Vector2Int lineEnd;

	
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
		drawBoard(g); // split this into: drawEmptyBoard and drawGatesOnBoard
		drawTiles(g);
		drawConnections(g);
		/* when the user clicked somewhere without releasing and is dragging the mouse
		 * draw a line between the original click position and the current mouse position. */
        if(drawTentativeLine) { 
//        	System.out.println("Drawing line between " + lineStart + " and " + lineEnd);
        	g.drawLine(lineStart.x, lineStart.y,  lineEnd.x, lineEnd.y);
        }
	}

	/**
     * Draws graphical elements to the screen.
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
    
    public void drawTiles(Graphics g) {
    	for(Vector2Int key : tilesToDraw.keySet()) {
            Graphics2D g2 = (Graphics2D) g;
    		g2.drawImage(images.getImage(tilesToDraw.get(key)),
    				key.x * tileWidth, 
            		key.y * tileHeight, null);
    	}
    }
    
    /**
	 * @param g
	 */
	private void drawConnections(Graphics g) {
		for(PointTuple c : connectionsToDraw) {
//			Vector2Int end = connectionsToDraw.get(key);
			g.drawLine(c.a.x, c.a.y, c.b.x, c.b.y);
			// figure out where to draw from
//			System.out.println("Should draw line from " + key + " to " + connectionsToDraw.get(key));
		}
		
	}
}