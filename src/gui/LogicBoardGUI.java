package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import app.Controller;
import utility.PointTuple;
import utility.PositionCalculator;
import utility.Vector2Int;

/** The core of the visualization of the model. 
 * <p><b>Functionality:</b>
 * <ul>
 * <li> Contains a display of the model.
 * <li> Allows the user to choose which gate to add to 
 * the model from a {@link gui.ButtonPalette palette}.
 * <li> Allows the user to connect gates.
 * <li> ALlows user to evaluate the built circuits.
 * <li> User can save / load models to / from files.
 * </ul>
 * @author Dominik Baumann, Philipp Grzywaczyk, Cameron McGregor
 * @version 2, July 2022
 */
public class LogicBoardGUI extends JFrame implements MouseListener, MouseMotionListener {
	
	/** Specifies the width of tiles used for visualization on the board. 
	 * Tile sizes should match the size of the image files used. */
	private final int TILE_WIDTH = 64;

	/** Specifies the height of tiles used for visualization on the board. 
	 * Tile sizes should match the size of the image files used. */
	private final int TILE_HEIGHT = 64;
	
	
	/** Specifies the default number of columns in the model. */
	private final int DEFAULT_BOARD_WIDTH = 8;
	
	/** Specifies the default number of rows in the model. */
	private final int DEFAULT_BOARD_HEIGHT = 5;
	
	/** Stores the current number of columns in the model. */
	private int boardWidth = 8;
	
	/** Stores the current number of rows in the model. */
	private int boardHeight = 5;
		
	/**	The GUI informs the controller over user interactions,
	 * which the controller then processes.
	 */
	Controller controller;
	
	/**	Stores the images needed to display the gates. */
	ImageStorage images;
	
	/**	Display the actual model as a grid. */
	TiledCanvas canvas;
	
	/**	Contains all menus. */
	LogicBoardMenu menuBar;
	
	/**	A scrollable pane with buttons, which allow the user
	 * to select which gate they want to place in the model.
	 */
	ButtonPalette buttonPalette;
	
	/**	Used for converting and examining mouse positions etc. */
	PositionCalculator positionCalculator;

	
	
	/** Create the (parent) GUI.
	 * @param controller_ The GUI informs the controller 
	 * over user interactions, which the controller then processes.
	 */
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

        positionCalculator = new PositionCalculator(TILE_WIDTH, TILE_HEIGHT, this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		/* get the position of mouse (events) relative 
		 * to the canvas representing the board */
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}


    
    
    /********************* MOUSE EVENTS *********************/
    
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
    }
    
    
    
	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2Int v = new Vector2Int(e.getX(), e.getY());
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
				controller.addConnection(v, canvas.lineStart);
			}
			canvas.repaint();
		}
		canvas.startedDragging = false;
	}

	
	
	/** Allows the model to communicate which gates
	 * are on the board at which position and
	 * which connections between gates exist.
	 * @param tiles The Gates in the model.
	 * @param connections The connections in the model.
	 */
	public void setTilesAndConnections(TreeMap<Vector2Int, TileType> tiles,
			ArrayList<PointTuple> connections) {
		canvas.tilesToDraw = tiles;
		canvas.connectionsToDraw = connections;
		repaint();
	}
	
	
	
	/** If a gate gets placed at the outermost column
	 * or row of the current grid, expand the grid.
	 * @param dim The grid coordinates of a gate.
	 */
	public void updateDimensions(Vector2Int dim) {
		if(boardWidth == dim.x + 1) { boardWidth++; }
		if(boardHeight == dim.y + 1) { boardHeight++; }
	}
	
	
	
	/** Change the number of rows and columns in the grid. 
	 * Minimum size is 2 by 2.
	 * @param rows The number of rows the visualization shall have.
	 * @param cols The number of columns the visualization shall have.
	 */
	public void setDimensions(int rows, int cols) {
		boardWidth = (cols >= 2 ? cols : DEFAULT_BOARD_WIDTH); 
		boardHeight = (rows >= 2 ? rows : DEFAULT_BOARD_HEIGHT);
	}
	
	

	/** Communicate with the menuBar, to only have the 
	 * "Undo" menu item enabled, when there actually is
	 * a command to undo.
	 * @param undoCount The current number of undoable commands.
	 * <p>
	 * Implementation note: Using events was considered for this task,
	 * but was not done, since there would have only been
	 * one listener for the event.
	 */
	public void updateUndoMenu(int undoCount) {
		menuBar.updateUndoMenu(undoCount);
	}
	

	/********************* GETTERS *********************/

	/** Get the current number of rows in the grid.
	 * @return The current number of rows in the grid.
	 */
	public int getBoardGUIHeight() { return boardHeight; }

	/** Get the current number of columns in the grid.
	 * @return The current number of columns in the grid.
	 */
	public int getBoardGUIWidth() { return boardWidth; }
	
	/** Get the width of a tile (image).
	 * @return The width of a tile used for visualization (in pixels).
	 */
	public int getTileWidth() { return TILE_WIDTH; }
	
	/** Get the height of a tile (image).
	 * @return The height of a tile used for visualization (in pixels).
	 */
	public int getTileHeight() { return TILE_HEIGHT; }
	
	/**
	 * @return The {@link utility.PositionCalculator PositionCalculator} 
	 * this GUI is using.
	 */
	public PositionCalculator getPositionCalculatorFromGUI() {
		return positionCalculator;
	}
	
	/************** UNUSED MOUSE EVENTS **************/
	@Override public void mouseMoved(MouseEvent e) { }
	@Override public void mousePressed(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { }
	
	/** This is just here because Eclipse complained about it. */
	private static final long serialVersionUID = 1L;
}