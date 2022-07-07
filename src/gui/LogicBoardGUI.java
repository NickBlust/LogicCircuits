/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;

import app.Controller;
import gui.TiledCanvas.TileType;
import utility.PositionCalculator;
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
	ImageStorage images;
	TiledCanvas canvas;
	JMenuBar menuBar;
	ButtonPalette buttonPalette;

	
	public LogicBoardGUI(Controller controller_) {
		
		controller = controller_;
		images = new ImageStorage();

		
		setSize(816, 615);
		setTitle("Logic Circuits Simulator");
		setLocationRelativeTo(null); //sets position of frame on screen
		
		getContentPane().add(canvas = new TiledCanvas(this, images));
		setJMenuBar(menuBar = new LogicBoardMenu(controller));
		getContentPane().add(buttonPalette = new ButtonPalette(images, controller), BorderLayout.EAST);
        
        JScrollPane scroll = new JScrollPane(buttonPalette);
        getContentPane().add(scroll, BorderLayout.EAST);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

		
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
    	controller.handleMouseClick(PositionCalculator.mousePositionToGridCoordinates(v));
    	System.out.println("Clicked at " + PositionCalculator.mousePositionToGridCoordinates(v));
    }
    
    // TODO: encapsulate public variables with getters and setters?
	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2Int v = new Vector2Int(e.getX(), e.getY());
//		System.out.println("idk " + v);
		if(!canvas.drawTentativeLine && !canvas.startedDragging && controller.isValidStart(v)) {
			canvas.lineStart = v;
			canvas.lineEnd = canvas.lineStart;
			canvas.drawTentativeLine = true;
		}
		else {
			canvas.lineEnd = v;
		}
		canvas.startedDragging = true;
		repaint();
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
		
	
	// UNUSED MOUSE EVENTS
	@Override public void mouseMoved(MouseEvent e) { }
	@Override public void mousePressed(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { }
}