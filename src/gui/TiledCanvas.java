/**
 * 
 */
package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * @author domin
 *
 */
public class TiledCanvas extends JPanel {

	public enum TileType {
		EMPTY
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * Draws graphical elements to the screen.
     * @param g ()
     */
    private void drawBoard(Graphics g, int rows, int columns) 
    {
        Graphics2D g2 = (Graphics2D) g;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                        g2.drawImage(tileEmpty, i * BoardGUI.TILE_WIDTH, j * BoardGUI.TILE_HEIGHT, null);
                }
            }
	}
}