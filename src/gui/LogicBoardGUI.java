package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import app.Controller;
import utility.PointTupleWithStatus;
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
	private final int DEFAULT_BOARD_HEIGHT = 11;
	
	/** Stores the current number of columns in the model. */
	private int boardWidth = 8;
	
	/** Stores the current number of rows in the model. */
	private int boardHeight = 11;
		
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
		positionCalculator = new PositionCalculator(TILE_WIDTH, TILE_HEIGHT, this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Logic Circuits Simulator");
		setLocationRelativeTo(null); //sets position of frame on screen
		
		setJMenuBar(menuBar = new LogicBoardMenu(controller));
		buttonPalette = new ButtonPalette(images, controller);
		buttonPalette.setPreferredSize(new Dimension(160, 704)); // 704 = 64*11

		canvas = new TiledCanvas(this, images);
		canvas.setPreferredSize(new Dimension(boardWidth * TILE_WIDTH, boardHeight * TILE_HEIGHT));

        // initialize the layout, the scroll panes and their constraints
		GridBagLayout gbl_TiledCanvas = new GridBagLayout();
		gbl_TiledCanvas.columnWidths = new int[]{280, 148, 0};
		gbl_TiledCanvas.rowHeights = new int[]{261, 0};
		gbl_TiledCanvas.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_TiledCanvas.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gbl_TiledCanvas);
		
		JScrollPane scrollPane_TiledCanvas = new JScrollPane();
		scrollPane_TiledCanvas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_TiledCanvas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_TiledCanvas.getHorizontalScrollBar().setUnitIncrement(16);
		scrollPane_TiledCanvas.getVerticalScrollBar().setUnitIncrement(16);
		// NOTE: set size of window to width (height) of all tiles + width (height) of a scrollbar - 1
		// to allow scrolling without having to resize the window
		scrollPane_TiledCanvas.setPreferredSize(new Dimension(526, 718)); 
		
		JScrollPane scrollPane_ButtonPalette = new JScrollPane();
		scrollPane_ButtonPalette.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_ButtonPalette.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_ButtonPalette.getVerticalScrollBar().setUnitIncrement(16);
		
		GridBagConstraints gbc_scroll_TiledCanvas = new GridBagConstraints();
		gbc_scroll_TiledCanvas.fill = GridBagConstraints.BOTH;
		gbc_scroll_TiledCanvas.insets = new Insets(0, 0, 0, 5);
		gbc_scroll_TiledCanvas.gridx = 0;
		gbc_scroll_TiledCanvas.gridy = 0;
		
		GridBagConstraints gbc_scroll_ButtonPalette = new GridBagConstraints();
		gbc_scroll_ButtonPalette.fill = GridBagConstraints.BOTH;
		gbc_scroll_ButtonPalette.gridx = 1;
		gbc_scroll_ButtonPalette.gridy = 0;
		
		
		// actually add the components to the frame
		scrollPane_TiledCanvas.setViewportView(canvas);
		getContentPane().add(scrollPane_TiledCanvas, gbc_scroll_TiledCanvas);
		
		getContentPane().add(scrollPane_ButtonPalette, gbc_scroll_ButtonPalette);
		scrollPane_ButtonPalette.setViewportView(buttonPalette);	
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		/* get the position of mouse (events) relative to the canvas representing the board */
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		setKeyBindings();
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
			ArrayList<PointTupleWithStatus> connections) {
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
	
	
	
	/********************* KEY BINDINGS *********************/
	
	private void setKeyBindings() {
		JRootPane rootPane = getRootPane();

		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                java.awt.event.InputEvent.CTRL_DOWN_MASK), "evaluate");
		rootPane.getActionMap().put("evaluate",
				new AbstractAction(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				System.out.println("CTRL + E");
				controller.evaluateCircuits(); } });

		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                java.awt.event.InputEvent.CTRL_DOWN_MASK), "open");
		rootPane.getActionMap().put("open",
				new AbstractAction(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				System.out.println("CTRL + O");
				controller.loadFromFile(); } });
		
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                java.awt.event.InputEvent.CTRL_DOWN_MASK), "reset");
		rootPane.getActionMap().put("reset",
				new AbstractAction(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				System.out.println("CTRL + R");
				controller.resetBoard(); } });
		
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                java.awt.event.InputEvent.CTRL_DOWN_MASK), "save");
		rootPane.getActionMap().put("save",
				new AbstractAction(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				System.out.println("CTRL + S");
				controller.saveToFile(); } });
		
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                java.awt.event.InputEvent.CTRL_DOWN_MASK), "undo");
		rootPane.getActionMap().put("undo",
				new AbstractAction(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				System.out.println("CTRL + Z");
				controller.undoCommand(); } });	
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