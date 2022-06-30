package gui;

import utility.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import gui.BoardEditor.TileType;

import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;



/**
 * The BoardGUI class is responsible for rendering graphics to the screen to display
 * the grid.
 * @author cmcgregor, Dominik Baumann (latter is responsible for the horrible parts, which is most of this class)
 */
public class BoardGUI extends JFrame implements MouseListener, MouseMotionListener
{
	/**
	 * The {@link gui.BoardEditor BoardEditor} associated with this GUI.
	 */
	BoardEditor boardEditor;
	
	/** Used for calculation row / colum indices based on position (i.e. mouse position). */
	BoardPositionCalculator positionCalculator;
	
    /**
     * Sets the size of some graphical elements,
     * specifically the display width of tiles on the board. Tile sizes 
     * should match the size of the image files used.
     */
    public static final int TILE_WIDTH = 64;
    
    /**
     * Sets the size of some graphical elements,
     * specifically the display height of tiles on the board. Tile sizes 
     * should match the size of the image files used.
     */
    public static final int TILE_HEIGHT = 64;

    /**
     * The canvas is the area that graphics are drawn to. It is an internal class
     * of the BoardGUI class.
     */
    Canvas canvas;
    
    /**
     * The menu bar contains three menus:
     * 1) "File": Save and load boards to and from a file.
     * 2) "Edit": Reset the entire board or evaluate the truth values of all gates
     *            on the board
 	 * 3) "Help": Retrieve information about this program and on how to use it.
     */
    JMenuBar menuBar;

     /**
     * Constructor for the BoardGUI class. It calls the initGUI method to generate the
     * required objects for display.
     */
    public BoardGUI() 
    {
        initGUI();
    }

    /**
     * Method to create and initialise components for displaying elements of the
     * board on the screen.
     */
    private void initGUI() 
    {
        getContentPane().add(canvas = new Canvas());
        
        setTitle("Logic Circuits Simulator");
        setSize(816, 615); //Can be changed by user but must fit the measurements of the tile
        setLocationRelativeTo(null);        //sets position of frame on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initMenuBar();
        initButtons();
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
    }

     /** Method to update the graphical elements on the screen
     * @param tiles The tiles on the board.
     */
    public void updateDisplay(TileType[][] tiles) 
    { canvas.update(tiles); }

	
	/** Sets the {@link gui.BoardEditor BoardEditor} and the
	 *  {@link gui.BoardPositionCalculator BoardPositionCalculator} of this object.
	 * @param be - a boardEditor
	 */
	public void SetBoardEditor(BoardEditor be) { 
		boardEditor = be; 
        positionCalculator = new BoardPositionCalculator(boardEditor);	
	}
    
    /** Store the current board in a .txt-file */
    private void clickedSave() { boardEditor.saveBoard(); }
    
    /** Load a board from a .txt-file */
    private void clickedLoad() {
    	boardEditor.loadBoard(this);
    	repaint();
    }
    
    /** Delete all gates and connections on the board an in the model. */
    private void resetBoard() { 
    	for(int col = 0; col < canvas.currentTiles.length; col++) {
    		for(int row = 0; row < canvas.currentTiles[col].length; row++) {
    			Vector2Int v = new Vector2Int(col, row);
    			SetTile(v, TileType.EMPTYTILE);
    		}
    	}
    	boardEditor.removeAllGates();
    	canvas.connections.clear();
    }
    
    /**
     * Evaluates the truth values of all gates and colors there output fields accordingly:
     * 1) white: not yet evaluated
     * 2) green: output evaluates to true
     * 3) red:   output evaluates to false
     */
    private void evaluateCircuits() {
    	System.out.println("Evaluating all circuits on the board");
    	ArrayList<EvaluationInfo> info = boardEditor.evaluateAndVisualizeModel();
    	for(EvaluationInfo eInfo : info)
    		setColoredTile(eInfo);
    	repaint();
    }
    
    /** Displays a window with information about the program. */
    private void showInformationAboutProgram() {
        JFrame frame = new JFrame("About - Logical Circuits");
        frame.setPreferredSize(new Dimension(400, 250));
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try 
        { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (Exception e) { e.printStackTrace(); }
        
        JTextArea textArea = new JTextArea("Logical Circuits. \n Designed and developed by Dominik Baumann, \n" 
        		+ "Philipp Grzywaczyk and Cameron McGregor. \n Version 1.0, June 2022. \n \n" 
        		+ " This program allows the user \n to graphically design a logical circuit, \n " 
        		+ "by adding and connecting the standard \n logical gates, and evaluating the circuit \n for a specified input");
        textArea.setEditable(false);

        frame.getContentPane().add(BorderLayout.CENTER, textArea);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    
    /** Displays a window with information on how to use the program. */
    private void openHelpMenu() {
        JFrame frame = new JFrame("Help");
        frame.setPreferredSize(new Dimension(500, 350));
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try 
        { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (Exception e) { e.printStackTrace(); }
        
        JTextArea textArea = new JTextArea("To add a gate: \n Choose a type of gate on the right, \n" 
        		+ "and then click whereever on the board \n where you want to place it. \n \n "
        		+ "To connect two gates: \n Simply drag a line from the output \n " 
        		+ "point of one gate to a input \n point of another gate. \n \n" 
        		+ " To evaluate the output, press Edit -> Evaluate.");
        textArea.setEditable(false);

        frame.getContentPane().add(BorderLayout.CENTER, textArea);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    /** Sets up the menu bar and its menus. */
    private void initMenuBar() {
    	menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        JMenuItem menuItem_SaveToFile = new JMenuItem("Save Board to File");
        fileMenu.add(menuItem_SaveToFile);
    	menuItem_SaveToFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { clickedSave(); } });
    	
        
        JMenuItem menuItem_LoadFromFile = new JMenuItem("Load Board from File");
        fileMenu.add(menuItem_LoadFromFile);
        menuItem_LoadFromFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { clickedLoad(); } });
        
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        
        JMenuItem menuItem_ResetBoard = new JMenuItem("Reset Board");
        editMenu.add(menuItem_ResetBoard);
        menuItem_ResetBoard.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { resetBoard(); } });
        
        
        JMenuItem menuItem_Evaluate = new JMenuItem("Evaluate");
        editMenu.add(menuItem_Evaluate);
        menuItem_Evaluate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { evaluateCircuits(); } });
        
        
        JMenu helpMenu = new JMenu("About & Help");
        menuBar.add(helpMenu);
        
        JMenuItem menuItem_About = new JMenuItem("About");
        helpMenu.add(menuItem_About);
        menuItem_About.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EventQueue.invokeLater(new Runnable()
                { @Override public void run() { showInformationAboutProgram(); } }); } });
        
        JMenuItem menuItem_Help = new JMenuItem("Help");
        helpMenu.add(menuItem_Help);
        menuItem_Help.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EventQueue.invokeLater(new Runnable()
                { @Override public void run() { openHelpMenu(); } }); } });    
    }
    

	/**
	 * Internal class to simplify the button instantiation in 
	 * {@link gui.BoardGUI initButtons}.
	 */
	class GBConstraints extends GridBagConstraints {

		/** Constructor
		 * @param x ()
		 * @param y ()
		 * @param bottom ()
		 * @see GridBagConstraints
		 */
		public GBConstraints(int x, int y, int bottom) {
			gridx = x;
			gridy = y;
			insets = new Insets(0, 0, bottom, 0);
		}
	}
    
    /**
     * Provide all buttons for gate / tile selection 
     * in a scroll pane on the right-hand side of the main frame.
     * It is CRUCIAL that the arrays for the button initialization
     * are ordered correctly!
     */
    private void initButtons() {
    	JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.EAST);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        JScrollPane scroll = new JScrollPane(panel);
        getContentPane().add(scroll, BorderLayout.EAST);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JButton button_Empty = null, button_FALSE = null, button_TRUE = null, 
                button_AND = null, button_OR = null, button_NOT = null, 
                button_NOR = null, button_NAND = null, button_XOR = null;

        
        String[] fileLocations = {"tileEmpty64", "FALSE", "TRUE",
        		"AND_White", "OR_White", "NOT_White", "NOR_White", "NAND_White", "XOR_White" };
        
        String[] buttonTexts = {"   Empty  ", "   FALSE  ", "    TRUE  ", "     AND  ",
        		"      OR  ", "     NOT  ", "     NOR  ", "    NAND  ", "     XOR  "};
        
        TileType[] tileTypes = { TileType.EMPTYTILE, TileType.FALSE, TileType.TRUE,
        		TileType.AND, TileType.OR, TileType.NOT, TileType.NOR, TileType.NAND, TileType.XOR };
        
        JButton[] tileButtons = { button_Empty, button_FALSE, button_TRUE, 
                button_AND, button_OR, button_NOT, button_NOR, button_NAND, button_XOR };
        
        for(int i = 0; i < tileButtons.length; i++) {
        	final int k = i; // later the index has to final for access to tileTypes
        	tileButtons[i] = new JButton(new ImageIcon(BoardGUI.class.getResource(
        			"/Assets/" + fileLocations[i] + ".png")));
        	tileButtons[i].setText(buttonTexts[i]);
        	tileButtons[i].addActionListener(new ActionListener(){  
        		public void actionPerformed(ActionEvent e){  
    	            boardEditor.selectTileToPlace(tileTypes[k]); } });  
        	panel.add(tileButtons[i], new GBConstraints(0, i, 5));
        }
    }
    
	/**
	 * Internal class used to draw elements within a JPanel. The Canvas class loads
	 * images from an asset folder inside the main project folder.
	 * @author cmcgrego, Dominik Baumann
	 */
	class Canvas extends JPanel {
	
		/** Maintain a list of the connections to draw onto the boad. */
		public ArrayList<ConnectionInfo> connections = new ArrayList<ConnectionInfo>();
		
		// Store the images for all gates and their states.
	    private BufferedImage tileEmpty;
	    private BufferedImage tileFALSE;
	    private BufferedImage tileTRUE;
	    
	    private BufferedImage tileAND_White;
	    private BufferedImage tileAND_TRUE;
	    private BufferedImage tileAND_FALSE;
	    
	    private BufferedImage tileNAND_White;
	    private BufferedImage tileNAND_TRUE;
	    private BufferedImage tileNAND_FALSE;
	    
	    private BufferedImage tileNOR_White;
	    private BufferedImage tileNOR_TRUE;
	    private BufferedImage tileNOR_FALSE;
	    
	    private BufferedImage tileNOT_White;
	    private BufferedImage tileNOT_TRUE;
	    private BufferedImage tileNOT_FALSE;
	    
	    private BufferedImage tileOR_White;
	    private BufferedImage tileOR_TRUE;
	    private BufferedImage tileOR_FALSE;
	    
	    private BufferedImage tileXOR_White;
	    private BufferedImage tileXOR_TRUE;
	    private BufferedImage tileXOR_FALSE;
	    
	    public TileType[][] currentTiles;  //the current 2D array of tiles to display
	    Map<TileType, BufferedImage> tileMap; // retrieve correct image from TileType
	
	    
	    /** Constructor that loads tile images for use in this class */
	    public Canvas() { loadTileImages(); }
	    
	    
	    
	    /** Loads tiles images from a fixed folder location within the project directory. */
	    private void loadTileImages() {
	        try {
	        	tileEmpty = ImageIO.read(BoardGUI.class.getResource("/assets/tileEmpty64.png"));
	        	
			    tileTRUE = ImageIO.read(BoardGUI.class.getResource("/assets/TRUE.png"));
	            tileFALSE = ImageIO.read(BoardGUI.class.getResource("/assets/FALSE.png"));
	        	
	            tileAND_White = ImageIO.read(BoardGUI.class.getResource("/assets/AND_White.png"));
	            tileAND_TRUE = ImageIO.read(BoardGUI.class.getResource("/assets/AND_TRUE.png"));
	            tileAND_FALSE = ImageIO.read(BoardGUI.class.getResource("/assets/AND_FALSE.png"));
	            
	            tileNAND_White = ImageIO.read(BoardGUI.class.getResource("/assets/NAND_White.png"));
	            tileNAND_TRUE = ImageIO.read(BoardGUI.class.getResource("/assets/NAND_TRUE.png"));
	            tileNAND_FALSE = ImageIO.read(BoardGUI.class.getResource("/assets/NAND_FALSE.png"));
	            
	            tileNOR_White = ImageIO.read(BoardGUI.class.getResource("/assets/NOR_White.png"));
	            tileNOR_TRUE = ImageIO.read(BoardGUI.class.getResource("/assets/NOR_TRUE.png"));
	            tileNOR_FALSE = ImageIO.read(BoardGUI.class.getResource("/assets/NOR_FALSE.png"));
	            
	            tileNOT_White = ImageIO.read(BoardGUI.class.getResource("/assets/NOT_White.png"));
	            tileNOT_TRUE = ImageIO.read(BoardGUI.class.getResource("/assets/NOT_TRUE.png"));
	            tileNOT_FALSE = ImageIO.read(BoardGUI.class.getResource("/assets/NOT_FALSE.png"));
	            
	            tileOR_White = ImageIO.read(BoardGUI.class.getResource("/assets/OR_White.png"));
	            tileOR_TRUE = ImageIO.read(BoardGUI.class.getResource("/assets/OR_TRUE.png"));
	            tileOR_FALSE = ImageIO.read(BoardGUI.class.getResource("/assets/OR_FALSE.png"));
	            
	            tileXOR_White = ImageIO.read(BoardGUI.class.getResource("/assets/XOR_White.png"));
	            tileXOR_TRUE = ImageIO.read(BoardGUI.class.getResource("/assets/XOR_TRUE.png"));
	            tileXOR_FALSE = ImageIO.read(BoardGUI.class.getResource("/assets/XOR_FALSE.png"));
	            
	            // only need temporarily for size verification of the images
	            BufferedImage[] temp = { tileEmpty, tileFALSE, tileTRUE,
	            		    tileAND_White, tileAND_TRUE, tileAND_FALSE,
	            		    tileNAND_White, tileNAND_TRUE, tileNAND_FALSE,
	            		    tileNOR_White, tileNOR_TRUE, tileNOR_FALSE,
	            		    tileNOT_White, tileNOT_TRUE, tileNOT_FALSE,
	            		    tileOR_White, tileOR_TRUE, tileOR_FALSE,
	            		    tileXOR_White, tileXOR_TRUE, tileXOR_FALSE };
	            
	            for(BufferedImage b : temp) {
	              assert b.getHeight() == BoardGUI.TILE_HEIGHT &&
	              b.getWidth() == BoardGUI.TILE_WIDTH;
	            }
	            
	            tileMap = new HashMap<>(); // set up the tilemap
	            tileMap.put(TileType.EMPTYTILE, tileEmpty);
	            tileMap.put(TileType.FALSE, tileFALSE);
	            tileMap.put(TileType.TRUE, tileTRUE);
	            
	            tileMap.put(TileType.AND, tileAND_White);
	            tileMap.put(TileType.NAND, tileNAND_White);
	            tileMap.put(TileType.NOR, tileNOR_White);
	            tileMap.put(TileType.NOT, tileNOT_White);
	            tileMap.put(TileType.OR, tileOR_White);
	            tileMap.put(TileType.XOR, tileXOR_White);
	            
	            tileMap.put(TileType.AND_TRUE, tileAND_TRUE);
	            tileMap.put(TileType.NAND_TRUE, tileNAND_TRUE);
	            tileMap.put(TileType.NOR_TRUE, tileNOR_TRUE);
	            tileMap.put(TileType.NOT_TRUE, tileNOT_TRUE);
	            tileMap.put(TileType.OR_TRUE, tileOR_TRUE);
	            tileMap.put(TileType.XOR_TRUE, tileXOR_TRUE);
	            
	            tileMap.put(TileType.AND_FALSE, tileAND_FALSE);
	            tileMap.put(TileType.NAND_FALSE, tileNAND_FALSE);
	            tileMap.put(TileType.NOR_FALSE, tileNOR_FALSE);
	            tileMap.put(TileType.NOT_FALSE, tileNOT_FALSE);
	            tileMap.put(TileType.OR_FALSE, tileOR_FALSE);
	            tileMap.put(TileType.XOR_FALSE, tileXOR_FALSE);
	
	        } catch (IOException e) {
	            System.out.println("Exception loading images: " + e.getMessage());
	            e.printStackTrace(System.out);
	        }
	    }
	    
	    /** Updates the current graphics on the screen to display the tiles.
	     * @param t The tiles to display.
	     */
	    public void update(TileType[][] t) 
	    {
	        currentTiles = t;
	        repaint();
	    }
	    
	    /**
	     * Override of method in super class, it draws the custom elements for this
	     * board such as the tiles.
	     * @param g ()
	     */
	    @Override
	    public void paintComponent(Graphics g)
	    {
	        super.paintComponent(g);
	        drawBoard(g);
	        Graphics2D g2D = (Graphics2D) g;
	        if(drawLine) {
	        	g2D.drawLine(lineStart.x, lineStart.y,  lineEnd.x, lineEnd.y);
	        }
	        drawAllConnections(g);
	    }
	
	    /**
	     * Draws graphical elements to the screen.
	     * @param g ()
	     */
	    private void drawBoard(Graphics g) 
	    {
	        Graphics2D g2 = (Graphics2D) g;
	        if (currentTiles != null) {
	            for (int i = 0; i < currentTiles.length; i++) {
	                for (int j = 0; j < currentTiles[i].length; j++) {
	                    if (currentTiles[i][j] != null) {   //checks a tile exists
	                        g2.drawImage(tileMap.get(currentTiles[i][j]), i * BoardGUI.TILE_WIDTH, j * BoardGUI.TILE_HEIGHT, null);
	                    }
	                }
	            }
	        }
	    }
	}
	
	
	/** 
	 * Draw all connections (i.e. their lines) on the GUI.
	 * @param g ()
	 */
	private void drawAllConnections(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		for(ConnectionInfo c : canvas.connections) {
			Vector2Int v1 = getOutputPositionOnBoard(new Vector2Int(c.input_col, c.input_row));
			Vector2Int v2 = getInputPositionOnBoard(new Vector2Int(c.target_col, c.target_row), c.id);
			g2D.drawLine(v1.x, v1.y, v2.x, v2.y);
		}
	}

	/** True iff mouse has been clicked and is dragged, to preview connections */
	boolean drawLine = false;
	
	/** Start of the connection-preview line */
	Vector2Int lineStart = null;
	
	/** End of the connection-preview line (i.e. the mouse position)*/
	Vector2Int lineEnd = null;
	
	/** True iff the mouse is already dragging */
	boolean startedDragging = false;

	@Override
	public void mouseClicked(MouseEvent e) {
		if(boardEditor.tileToPlace != null && !drawLine) {
			Vector2Int v = positionCalculator.GetTileIndices(e.getX(), e.getY());
			SetTile(v, boardEditor.tileToPlace);
			boardEditor.placeTile(v);
		}
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2Int v = new Vector2Int(e.getX(), e.getY());
		if(!drawLine && !startedDragging && isValidStart(v)) {
			lineStart = v;
			lineEnd = lineStart;
			drawLine = true;
		}
		else {
			lineEnd = v;
		}
		startedDragging = true;
		repaint();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(drawLine) {
			drawLine = false;
			if(isValidEnd(new Vector2Int(e.getX(),e.getY())))
				addConnection();
			repaint();
		}
		startedDragging = false;
	}
	
	/** Stores which input to set if a connection is deemed valid. */
	int inputIndex = -1;
	
	/** 
	 * Check if the position at which the mouse was clicked is the output of a gate.
	 * @param v The position of the mouse click.
	 * @return True iff the position of the mouseclick was the output of a gate.
	 */
	private boolean isValidStart(Vector2Int v) {
		try {
			Vector2Int coord = positionCalculator.GetTileIndices(v); // coord may be null
			TileType t = canvas.currentTiles[coord.x][coord.y];
			if(t == TileType.EMPTYTILE) { return false; }
			Vector2Int target = new Vector2Int(TILE_WIDTH * (coord.x + 1) - 4, TILE_HEIGHT * coord.y + (TILE_HEIGHT / 2));
			double dist = target.squaredDistance(v);
			if(dist <= 10*10) 
				return true;
			return false;
		} catch (java.lang.NullPointerException ex) {
			return false;
		}
	}
	
	/**
	 * Check if the position at which the mouse was released is the input of a gate.
	 * @param v The position of the mouse release.
	 * @return True iff the position of the mouse release was the input of a gate.
	 */
	private boolean isValidEnd(Vector2Int v) {
		try { // coord may be null
			Vector2Int coord = positionCalculator.GetTileIndices(v);
			if(coord.equals(positionCalculator.GetTileIndices(lineStart)))
				return false; // drag remained on same tile
			
			TileType t = canvas.currentTiles[coord.x][coord.y];
			if(t == TileType.EMPTYTILE) { return false; } // no gate
			else if(t == TileType.TRUE || t == TileType.FALSE) { return false; } // no input
			else if(t == TileType.NOT || t == TileType.NOT_FALSE || t == TileType.NOT_TRUE)
			{ return isValidEndOneInput(v); } // NOT etc. has only one input
			else { return isValidEndTwoInputs(v); } // all other gates gave two inputs
		} 
		catch (java.lang.NullPointerException ex) { // e.g. mouse was not released on canvas
			return false;
		}
	}
	
	/** 
	 * Check if the position of the mouse release was the input
	 * of a gate with only one input (NOT and its colourings).
	 * @param v Position of the mouse release.
	 * @return True iff the position of the mouse release was the input of a NOT-gate. 
	 */
	private boolean isValidEndOneInput(Vector2Int v) {
		Vector2Int coord = positionCalculator.GetTileIndices(v);
		Vector2Int target = new Vector2Int(TILE_WIDTH * coord.x + 4, TILE_HEIGHT * coord.y + (TILE_HEIGHT / 2));
		double dist = target.squaredDistance(v);
		inputIndex = 1;
		if(dist <= 10*10) 
			return true;
		return false;
	}
	
	
	/**
	 * Check if the position of the mouse release was the input
	 * of a gate with two inputs (all except TRUE, FALSE, EMPTY as well as NOT and its colourings).
	 * Sets inputIndex accordingly if the result is true.
	 * @param v Position of the mouse release.
	 * @return True iff the position of the mouse release was the input of the target gate (also sets inputIndex in that case!).
	 */
	private boolean isValidEndTwoInputs(Vector2Int v) {
		Vector2Int coord = positionCalculator.GetTileIndices(v);
		Vector2Int target_Top = new Vector2Int(TILE_WIDTH * coord.x + 5, TILE_HEIGHT * coord.y + 5);
		double dist = target_Top.squaredDistance(v);
		if(dist <= 10*10) 
		{
			inputIndex = 1;
			return true;
		}
		Vector2Int target_Bottom = new Vector2Int(TILE_WIDTH * coord.x + 5, TILE_HEIGHT * (coord.y + 1) - 4);
		dist = target_Bottom.squaredDistance(v);
		if(dist <= 10*10) 
		{
			inputIndex = 2;
			return true;
		}
		return false;
	}
	
	
	/**
	 * Set a tile / gate on the GUI and in the {@link logicCircuits.LogicCircuit underlying model}.
	 * @param v Tile / Gate Position
	 * @param type The type of tile / gate to set
	 */
	public void SetTile(Vector2Int v, TileType type) {
		try { // v may be null			
			canvas.currentTiles[v.x][v.y] = type;
			if(true /*type == TileType.EMPTYTILE*/) {
				ArrayList<ConnectionInfo> newInfo = new ArrayList<ConnectionInfo>();
				// remove connections
				for(ConnectionInfo c : canvas.connections) {
					if(c.isPartOfConnection(v))
						newInfo.add(c);
				}
				for(ConnectionInfo c : newInfo) {
					canvas.connections.remove(c);
					boardEditor.removeConnection(c);
				}
			}
			repaint();
		} catch(java.lang.NullPointerException ex) { /* do nothing */ }
	}
	
	/**
	 * EXCLUSIVELY add a connection to the GUI (but not to the model).
	 * This function is used when {@link app.BoardLoader loading boards}.
	 * @param c Information about the connection to draw.
	 */
	public void addConnectionToGUI(ConnectionInfo c) {
		ConnectionInfo cInfo = new ConnectionInfo(c);
		cInfo.id += 1;
		canvas.connections.add(cInfo); // keep a list of entries
	}
	
	/**
	 * Add a connection to the GUI and the {@link logicCircuits.LogicCircuit underlying model}.
	 * Recreating a connection leads to its deletion. 
	 */
	private void addConnection() {
		Vector2Int start = positionCalculator.GetTileIndices(lineStart);
		Vector2Int end = positionCalculator.GetTileIndices(lineEnd);
		
		ConnectionInfo toDelete = null;
		// recreating a connection leads to its deletion
		for(ConnectionInfo c: canvas.connections) { 
			if(c.isPartOfConnection(start) && c.isPartOfConnection(end) && c.id == inputIndex) {
				toDelete = c;
			}
		}
		if(toDelete != null) {
			boardEditor.removeConnection(toDelete);
			canvas.connections.remove(toDelete);
			toDelete = null;
			return;
		}
		
		/* if the gate is already receiving input at this position
		 * remove the old connection.
		 */
		if(boardEditor.alreadyHasConnection(end, inputIndex))
		{
			// find the existing connection
			for(ConnectionInfo c: canvas.connections) {
				if(c.isPartOfConnection(end)) {
					toDelete = c;
					break;
				}
			} try {
				// remove old connection from model
				boardEditor.removeConnection(toDelete);
				// and gui
				canvas.connections.remove(toDelete);

			} catch(java.lang.NullPointerException ex) {
				System.out.println("ERROR in BoardGUI: Could not remove connection --> " + toDelete);
			}
		}
		
		// WIP, try DFS
		// if connection leads to circle, don't create it
//		ArrayList<Vector2Int> discovered = new ArrayList<Vector2Int>();
//		ArrayList<Vector2Int> frontier = new ArrayList<Vector2Int>();
//		discovered.add(start);
//		frontier.add(start);
//		
//		while(frontier.size() > 0) {
//			Vector2Int current = start;
//			for(ConnectionInfo c : canvas.connections) {
//				if(c.target_row == current.x && c.target_col == current.y) {
//					System.out.println("Found one!");
//					frontier.add(new Vector2Int(c.input_row, c.input_col));
//				}
//			}
//			frontier.remove(current);
//		}
		
		GateGraph graph = new GateGraph();
		for(int i = 0; i < canvas.currentTiles.length; i++) {
			for(int k = 0; k < canvas.currentTiles[0].length; k++) {
				if(canvas.currentTiles[i][k] != TileType.EMPTYTILE) {
					if(canvas.currentTiles[i][k] == TileType.TRUE
							|| canvas.currentTiles[i][k] == TileType.FALSE) {
						graph.addNode(new Vector2Int(i, k), 0);
							
						}
					else if(canvas.currentTiles[i][k] == TileType.NOT 
							|| canvas.currentTiles[i][k] == TileType.NOT_TRUE
							|| canvas.currentTiles[i][k] == TileType.NOT_FALSE) {
						Node in = new Node(new Vector2Int(i, k), 1);
						Node out = new Node(new Vector2Int(i, k), 0);
						graph.addNode(in);
						graph.addNode(out);
						graph.addEdge(in, out);
					}
					else {
						Node top_in = new Node(new Vector2Int(i, k), 1);
						Node bottom_in = new Node(new Vector2Int(i, k), 2);
						Node out = new Node(new Vector2Int(i, k), 0);
						graph.addNode(top_in);
						graph.addNode(bottom_in);
						graph.addNode(out);
						graph.addEdge(top_in, out);
						graph.addEdge(bottom_in, out);
					}
				}
			}
		}
		// add connections from model
		for(ConnectionInfo c : canvas.connections) {
			Vector2Int from = new Vector2Int(c.input_col, c.input_row);
			Vector2Int to = new Vector2Int(c.target_col, c.target_row);
			Node start_ = new Node(from, 0);
			Node end_ = new Node(to, c.id);
			graph.addEdge(start_, end_);
			System.out.println("Adding " + start_ + " --> " + end_);
		}

		// add the future edge:
		Node futureStart = new Node(start, 0);
		Node futureEnd = new Node(end, inputIndex);
		graph.addEdge(futureStart, futureEnd);
		
		Node targetNode = new Node(end, 0);
		Node searchBegin = new Node(start, 0);

		ArrayList<Node> visited = new ArrayList<Node>();
		ArrayList<Node> frontier = new ArrayList<Node>();
		visited.add(searchBegin); frontier.add(searchBegin);
		System.out.println(graph.toString());
		graph.removeNode(new Node(start, 0));
		while(frontier.size() > 0) {
			Node current = frontier.remove(0);
			ArrayList<Node> newNodes = graph.getNeighbouringNodes(current);
			System.out.println("Comparing " + current + " to " + targetNode);
			if(current.equals(targetNode))
				System.out.println("FOUND CYCLE!!!");
			for(Node n : newNodes) {
				for(Node n2 : visited) {
					if(n2.equals(n))
						break; // already now this node
					else
						frontier.add(n);
				}
			}
		}
		
		
		

		// add connection in model
		boardEditor.addConnection(start, end, inputIndex);

		// and in GUI
		ConnectionInfo cInfo = new ConnectionInfo(start.x, start.y, end.x, end.y, inputIndex);
//		if(!connectionAlreadyExists(cInfo)) { // this should be unnecessary
			canvas.connections.add(cInfo); // keep a list of entries
//		}
			
	}
	
	/** Check if a connection already exists (should be unused atm)
	 * @param c The connection to check.
	 * @return True iff the given connection was already present (in the GUI).
	 */
	private boolean connectionAlreadyExists(ConnectionInfo c) {
		for(ConnectionInfo cInfo : canvas.connections) {
			if(c.equals(cInfo)) { return true; }
		}
		return false;
	}

	
	/**
	 * Determine the position of the output of a gate / tile at a certain position.
	 * @param coord The position of the gate / tile.
	 * @return The position of the output of a gate / tile at that position.
	 */
	private Vector2Int getOutputPositionOnBoard(Vector2Int coord) {
		return new Vector2Int(TILE_WIDTH * (coord.x + 1) - 4, TILE_HEIGHT * coord.y + (TILE_HEIGHT / 2));
	}
	
	/**
	 * Determine the position of the input of a gate / tile at a certain position.
	 * Depends on the type of tile / gate!
	 * @param coord The position of the gate / tile.
	 * @param index The identifier of the input slot.
	 * @return The position of the output of a gate / tile at that position for the given input slot.
	 */
	private Vector2Int getInputPositionOnBoard(Vector2Int coord, int index) {
		TileType t = canvas.currentTiles[coord.x][coord.y];
		if(t == TileType.NOT || t == TileType.NOT_FALSE || t == TileType.NOT_TRUE)
			return new Vector2Int(TILE_WIDTH * coord.x + 4, TILE_HEIGHT * coord.y + (TILE_HEIGHT / 2));
		if(index == 1)
			return new Vector2Int(TILE_WIDTH * coord.x + 5, TILE_HEIGHT * coord.y + 5);
		if(index == 2)
			return new Vector2Int(TILE_WIDTH * coord.x + 5, TILE_HEIGHT * (coord.y + 1) - 4);
		System.out.println("ERROR: Unexpected Behaviour!!!: " + index + " " + coord);
		return new Vector2Int(0, 0);
	}
	
	/** 
	 * Set a tile with color, which represents the gate's output.
	 * Called upon board evaluation.
	 * @param e Contains the type of gate / tile, its position and its truth value.
	 */
	private void setColoredTile(EvaluationInfo e) {
		TileType t = e.type;
		System.out.println("(" + e.row + ", " + e.col + ") --> " + t);

		if(t== TileType.AND)
			t = (e.truthValue ? TileType.AND_TRUE : TileType.AND_FALSE);
		else if(t== TileType.NAND)
			t = (e.truthValue ? TileType.NAND_TRUE : TileType.NAND_FALSE);
		else if(t== TileType.NOR)
			t = (e.truthValue ? TileType.NOR_TRUE : TileType.NOR_FALSE);
		else if(t== TileType.NOT)
			t = (e.truthValue ? TileType.NOT_TRUE : TileType.NOT_FALSE);
		else if(t== TileType.OR)
			t = (e.truthValue ? TileType.OR_TRUE : TileType.OR_FALSE);
		else if(t== TileType.XOR)
			t = (e.truthValue ? TileType.XOR_TRUE : TileType.XOR_FALSE);
		else {
			System.out.println("ERROR: Evaluation failed: " + e.type);
		}
		canvas.currentTiles[e.row][e.col] = t;
	}
	
	/** Remove all existing connections in the GUI */
	public void clearConnections() {
		canvas.connections.clear();
	}
	
	@Override public void mouseMoved(MouseEvent e) { }
	@Override public void mousePressed(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) {	}
}