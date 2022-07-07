/**
 * 
 */
package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import app.Controller;
import gates.Gate;
import gui.TiledCanvas.TileType;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class LogicBoardGUI extends JFrame implements MouseListener, MouseMotionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The two final int attributes below set the size of some graphical elements,
	 * specifically the display height and width of tiles on the board. Tile sizes 
	 * should match the size of the image files used.
	 */
	public static final int TILE_WIDTH = 64;
	public static final int TILE_HEIGHT = 64;
	
	private int boardWidth = 8;
	private int boardHeight = 5;
	
	Controller controller;
	TiledCanvas canvas;
	JMenuBar menuBar;

	
	public LogicBoardGUI(Controller controller_) {
		
		controller = controller_;
		
		setSize(816, 615);
		setTitle("Logic Circuits Simulator");
		setLocationRelativeTo(null); //sets position of frame on screen
		
		getContentPane().add(canvas = new TiledCanvas(this));
		setJMenuBar(menuBar = new LogicBoardMenu(controller));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		/* get the position of mouse (events) relative 
		 * to the canvas representing the board */
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}


    
    
    // MOUSE EVENTS
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	Vector2Int v = new Vector2Int(e.getX(), e.getY());
		if(!validateMousePosition(v)) { return; }
		// convert mouse position to (row, column)-coordinates on the board
    	controller.handleMouseClick(mousePositionToGridCoordinates(v));
    	System.out.println("Cliecked at " + mousePositionToGridCoordinates(v));
    }
    
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override public void mouseReleased(MouseEvent e) { }

	// HELPERS
	public int getBoardGUIWidth() { return boardWidth; }
	public int getBoardGUIHeight() { return boardHeight; }
	
	
	
	private boolean validateMousePosition(Vector2Int v) {
		if(boardWidth * LogicBoardGUI.TILE_WIDTH < v.x 
				|| boardHeight * LogicBoardGUI.TILE_HEIGHT < v.y)
			return false;
		return true;
	}
	
	private Vector2Int mousePositionToGridCoordinates(Vector2Int pos) {
		return new Vector2Int((int) Math.ceil(pos.x / LogicBoardGUI.TILE_WIDTH),
				(int) Math.ceil(pos.y / LogicBoardGUI.TILE_HEIGHT));
	}
	
	// UNUSED MOUSE EVENTS
	@Override public void mouseMoved(MouseEvent e) { }
	@Override public void mousePressed(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { }



	/**
	 * @param tiles
	 * @param connections
	 */
	public void setTilesAndConnections(TreeMap<Vector2Int, TileType> tiles,
			TreeMap<Vector2Int, Vector2Int> connections) {
		canvas.tilesToDraw = tiles;
		repaint();
		System.out.println("Setting stuff to draw");
		// TODO Auto-generated method stub
		
	}
}