/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import app.Controller;
import gui.TiledCanvas.TileType;
import utility.PointTuple;
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
	private final int TILE_WIDTH = 64;
	private final int TILE_HEIGHT = 64;
	
	private int boardWidth = 8;
	private int boardHeight = 5;
	
	Controller controller;
	ImageStorage images;
	TiledCanvas canvas;
	JMenuBar menuBar;
	ButtonPalette buttonPalette;
	PositionCalculator positionCalculator;

	
	public LogicBoardGUI(Controller controller_) {
		
		controller = controller_;
		images = new ImageStorage(this);

		
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

        positionCalculator = new PositionCalculator(TILE_WIDTH, TILE_HEIGHT, boardWidth, boardHeight);
		
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
		if(!positionCalculator.validateMousePosition(v)) { return; }
		// convert mouse position to (row, column)-coordinates on the board
		if(SwingUtilities.isLeftMouseButton(e))
			controller.handleLeftClick(positionCalculator.mousePositionToGridCoordinates(v));
		else 
			controller.handleRightClick(v);
    	canvas.repaint();
//    	System.out.println("Clicked at " + positionCalculator.mousePositionToGridCoordinates(v));
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
		canvas.repaint();
	}


	@Override public void mouseReleased(MouseEvent e) { 
		if(canvas.drawTentativeLine) {
			canvas.drawTentativeLine = false;
			Vector2Int v = new Vector2Int(e.getX(), e.getY());
			
			if(positionCalculator.validateMousePosition(v) 
					&& controller.isValidEnd(v, canvas.lineStart)) {
//				System.out.println("This connection was valid!");
				controller.addConnection(v, canvas.lineStart);
			}
			canvas.repaint();
		}
		canvas.startedDragging = false;
	}




	/**
	 * @param tiles
	 * @param connections
	 */
	public void setTilesAndConnections(TreeMap<Vector2Int, TileType> tiles,
			ArrayList<PointTuple> connections) {
		canvas.tilesToDraw = tiles;
		canvas.connectionsToDraw = connections;
		repaint();
//		System.out.println("Setting stuff to draw");
	}
	
	
	// HELPERS
	public int getBoardGUIWidth() { return boardWidth; }
	public int getBoardGUIHeight() { return boardHeight; }
	public int getTileWidth() { return TILE_WIDTH; }
	public int getTileHeight() { return TILE_HEIGHT; }
	
	public PositionCalculator getPositionCalculatorFromGUI() {
		return positionCalculator;
	}
	
	// UNUSED MOUSE EVENTS
	@Override public void mouseMoved(MouseEvent e) { }
	@Override public void mousePressed(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { }
}