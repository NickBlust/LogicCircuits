/**
 * 
 */
package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.TreeMap;

import javax.swing.JPanel;

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
	
	public enum TileType {
		EMPTY, FALSE, TRUE,
		AND, NAND, NOR, NOT, OR, XOR,
		AND_TRUE, NAND_TRUE, NOR_TRUE, NOT_TRUE, OR_TRUE, XOR_TRUE,
		AND_FALSE, NAND_FALSE, NOR_FALSE, NOT_FALSE, OR_FALSE, XOR_FALSE
	}
	
	public TreeMap<Vector2Int, TileType> tilesToDraw;
	
	/** The {@link gui.LogicBoardGUI gui parent} this canvas sits on. */
	LogicBoardGUI boardGUI;
	ImageStorage images;
	BufferedImage emptyTileImage;
	
	public TiledCanvas(LogicBoardGUI boardGUI_, ImageStorage imageStorage) {
		images = imageStorage;
		boardGUI = boardGUI_;
		emptyTileImage = images.getImage(TileType.EMPTY);
		tilesToDraw = new TreeMap<Vector2Int, TileType>();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		drawBoard(g); // split this into: drawEmptyBoard and drawGatesOnBoard
		drawTiles(g);
	}
	
    /**
     * Draws graphical elements to the screen.
     * @param g ()
     */
    private void drawBoard(Graphics g) 
    {
    	System.out.println("Images null??? = " + (images == null));
    	int columns = boardGUI.getBoardGUIWidth();
    	int rows = boardGUI.getBoardGUIHeight();
        Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < columns; i++) {
            for (int k = 0; k < rows; k++) {
            	g2.drawImage(emptyTileImage, 
            			i * LogicBoardGUI.TILE_WIDTH, 
            			k * LogicBoardGUI.TILE_HEIGHT, null);            			
            }
        }
	}
    
    public void drawTiles(Graphics g) {
    	System.out.println("Images null? = " + (images == null));
    	for(Vector2Int key : tilesToDraw.keySet()) {
            Graphics2D g2 = (Graphics2D) g;
    		g2.drawImage(images.getImage(tilesToDraw.get(key)),
    				key.x * LogicBoardGUI.TILE_WIDTH, 
            		key.y * LogicBoardGUI.TILE_HEIGHT, null);
    	}
    }
}