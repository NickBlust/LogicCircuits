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
 * @author cmcgregor, Dominik Baumann
 */
public class BoardGUI extends JFrame implements MouseListener, MouseMotionListener
{
	/**
	 * The {@link gui.BoardEditor BoardEditor} associated with this GUI.
	 */
	BoardEditor boardEditor;
	
	BoardPositionCalculator positionCalculator;
	
    /**
     * The two final int attributes below set the size of some graphical elements,
     * specifically the display height and width of tiles on the board. Tile sizes 
     * should match the size of the image files used.
     */
    public static final int TILE_WIDTH = 64;
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

     /**
     * Method to update the graphical elements on the screen
     */
    public void updateDisplay(TileType[][] tiles) 
    {
        canvas.update(tiles);
    }

    
    /**
     * Stores the current board in a .txt-file
     */
    private void clickedSave() { // TODO
    	System.out.println("Clicked on Save");
    	// format suggestion:
    	// #row + " " + #columns + " " + numberOfTilesOnBoard
    	// #rowOfThisTile + " " + #columnOfThisTile + " " + tileName
    	// ... numberOfTilesOnBoard-many lines
    	// #rowOfInput + " " + #columnOfInput + " " + #rowOfTarget + " " + #columnOfTarget + " " + #slotIDOnTarget
    	// ... (iterate until file has no more nextLine)
    }
    
    /**
     * Load a board from a .txt-file
     */
    private void clickedLoad() { // TODO
    	System.out.println("Clicked on Load");
    }
    
    /**
     * Delete all gates and connections on the board an in the model.
     */
    private void resetBoard() { // TODO
    	System.out.println("Resetting Board");
    }
    
    /**
     * Evaluates the truth values of all gates and colors there output fields accordingly:
     * 1) white: not yet evaluated
     * 2) green: output evaluates to true
     * 3) red:   output evaluates to false
     */
    private void evaluateCircuits() { // TODO
    	System.out.println("Evaluating all circuits on the board");
    }
    
    /**
     * Displays a window with information about the program.
     */
    private void showInformationAboutProgram() {
        JFrame frame = new JFrame("About");
        frame.setPreferredSize(new Dimension(400, 200));
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try 
        { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (Exception e) { e.printStackTrace(); }
        
        JTextArea textArea = new JTextArea("About");
        textArea.setEditable(false);

        frame.getContentPane().add(BorderLayout.CENTER, textArea);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    
    /**
     * Displays a window with information on how to use the program.
     */
    private void openHelpMenu() {
        JFrame frame = new JFrame("Help");
        frame.setPreferredSize(new Dimension(400, 200));
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try 
        { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (Exception e) { e.printStackTrace(); }
        
        JTextArea textArea = new JTextArea("Help");
        textArea.setEditable(false);

        frame.getContentPane().add(BorderLayout.CENTER, textArea);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    /**
     * Sets up the menu bar and its menus.
     * @author Dominik Baumann
     */
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
     * Provide all buttons for gate / tile selection 
     * in a scroll pane on the right-hand side of the main frame.
     * It is CRUCIAL that the arrays for the button initialization
     * are ordered correctly!
     * @author Dominik Baumann
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
    	            boardEditor.SelectTileToPlace(tileTypes[k]); } });  
        	panel.add(tileButtons[i], new GBConstraints(0, i, 5));
        }
    }
    
	/**
	 * Internal class used to draw elements within a JPanel. The Canvas class loads
	 * images from an asset folder inside the main project folder.
	 * @author cmcgrego, Dominik Baumann
	 */
	class Canvas extends JPanel {
	
		public ArrayList<ConnectionInfo> connections = new ArrayList<ConnectionInfo>();
		
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
	    Map<TileType, BufferedImage> tileMap;
	
	    
	    /**
	     * Constructor that loads tile images for use in this class
	     */
	    public Canvas() 
	    {
	        loadTileImages();
	    }
	    
	    /**
	     * Loads tiles images from a fixed folder location within the project directory
	     */
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
	            
	            tileMap = new HashMap<>();
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
	    
	    /**
	     * Updates the current graphics on the screen to display the tiles
	     */
	    public void update(TileType[][] t) 
	    {
	        currentTiles = t;
	        repaint();
	    }
	    
	    /**
	     * Override of method in super class, it draws the custom elements for this
	     * board such as the tiles.
	     * @param g 
	     */
	    @Override
	    public void paintComponent(Graphics g)
	    {
	        super.paintComponent(g);
	        drawBoard(g);
	        if(drawLine) {
	        	Graphics2D g2D = (Graphics2D) g;
	        	g2D.drawLine(lineStart.x, lineStart.y,  lineEnd.x, lineEnd.y);
	        }
	        drawAllConnections(g);
	    }
	
	    /**
	     * Draws graphical elements to the screen.
	     * @param g 
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
	 * Internal class to simplify the button instantiation in 
	 * {@link BoardGUI.initButtons initButtons}.
	 * @author Dominik Baumann
	 */
	class GBConstraints extends GridBagConstraints {
		public GBConstraints(int x, int y, int bottom) {
			gridx = x;
			gridy = y;
			insets = new Insets(0, 0, bottom, 0);
		}
	}

	boolean drawLine = false;
	Vector2Int lineStart = null;
	Vector2Int lineEnd = null;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(boardEditor.tileToPlace != null && !drawLine) {
			SetTile(positionCalculator.GetTileIndices(e.getX(),e.getY()), boardEditor.tileToPlace);
		}
	}

	
	
	public void SetBoardEditor(BoardEditor be) { 
		boardEditor = be; 
        positionCalculator = new BoardPositionCalculator(boardEditor);	
	}
	
	public void SetTile(Vector2Int v, TileType type) {
		canvas.currentTiles[v.x][v.y] = type;
		repaint();
	}
	
	boolean startedDragging = false;
	
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
	
	int inputIndex = -1;
	
	private boolean isValidStart(Vector2Int v) {
		Vector2Int coord = positionCalculator.GetTileIndices(v);
		TileType t = canvas.currentTiles[coord.x][coord.y];
		if(t == TileType.EMPTYTILE) { return false; }
		Vector2Int target = new Vector2Int(TILE_WIDTH * (coord.x + 1) - 4, TILE_HEIGHT * coord.y + (TILE_HEIGHT / 2));
		double dist = target.squaredDistance(v);
		if(dist <= 10*10) 
			return true;
		return false;
	}
	
	private boolean isValidEnd(Vector2Int v) {
		Vector2Int coord = positionCalculator.GetTileIndices(v);
		if(coord.equals(positionCalculator.GetTileIndices(lineStart)))
			return false; // drag remained on same tile
		TileType t = canvas.currentTiles[coord.x][coord.y];
		if(t == TileType.EMPTYTILE) { return false; }
		else if(t == TileType.TRUE || t == TileType.FALSE) { return false; }
		else if(t == TileType.NOT) // has only one output
		{ return isValidEndOneInput(v); }
		else { return isValidEndTwoInputs(v); }
	}
	
	private boolean isValidEndOneInput(Vector2Int v) {
		Vector2Int coord = positionCalculator.GetTileIndices(v);
		Vector2Int target = new Vector2Int(TILE_WIDTH * coord.x - 4, TILE_HEIGHT * coord.y + (TILE_HEIGHT / 2));
		double dist = target.squaredDistance(v);
		System.out.println(dist + " " + v + " " + target);		
		inputIndex = 1;
		if(dist <= 10*10) 
			return true;
		return false;
	}
	
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
	
	private void addConnection() {
		// IN MODEL
		// get input gate
		// get target gate
		// set input using inputIndex
		Vector2Int start = positionCalculator.GetTileIndices(lineStart);
		Vector2Int end = positionCalculator.GetTileIndices(lineEnd);
		ConnectionInfo cInfo = new ConnectionInfo(start.x, start.y, end.x, end.y, inputIndex);
		if(!connectionAlreadyExists(cInfo)) {
			System.out.println("Adding connection");
			canvas.connections.add(cInfo);
		}
			
		System.out.println(cInfo);
		// keep a list of entries
	}
	
	private boolean connectionAlreadyExists(ConnectionInfo c) {
		for(ConnectionInfo cInfo : canvas.connections) {
			if(c.equals(cInfo)) { return true; }
		}
		return false;
	}
	
	private void drawAllConnections(Graphics g) {
		
	}
	
	@Override public void mouseMoved(MouseEvent e) { }
	@Override public void mousePressed(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) {	}
}