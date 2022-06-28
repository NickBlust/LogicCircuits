package LogicBoard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import LogicBoard.BoardEditor.TileType;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * The BoardGUI class is responsible for rendering graphics to the screen to display
 * the grid.
 * @author cmcgregor, Dominik Baumann
 */
public class BoardGUI extends JFrame
{

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
    JMenuBar menuBar;
//    MenuBar menuBar;

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
        getContentPane().add(canvas = new Canvas());     //adds canvas to this frame
//        menuBar = new JMenuBar();
//        JMenu fileMenu = new JMenu("File"); 
//        menuBar.add(fileMenu);
//        add(menuBar = new MenuBar()); // add menu bar to this frame
        setTitle("Logic Circuits Simulator");
        setSize(816, 615); //Can be changed by user but must fit the measurements of the tile
        setLocationRelativeTo(null);        //sets position of frame on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initMenuBar();
    }

     /**
     * Method to update the graphical elements on the screen
     */
    public void updateDisplay(TileType[][] tiles) 
    {
        canvas.update(tiles);
    }

    private void initMenuBar() {
    	menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        JMenuItem menuItem_SaveToFile = new JMenuItem("Save Board to File");
        fileMenu.add(menuItem_SaveToFile);
    	menuItem_SaveToFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clickedSave();
        	}
        });
    	
        
        JMenuItem menuItem_LoadFromFile = new JMenuItem("Load Board from File");
        fileMenu.add(menuItem_LoadFromFile);
        menuItem_LoadFromFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clickedLoad();
        	}
        });
        
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        
        JMenuItem menuItem_ResetBoard = new JMenuItem("Reset Board");
        editMenu.add(menuItem_ResetBoard);
        menuItem_ResetBoard.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		resetBoard();
        	}
        });
        
        
        JMenuItem menuItem_Evaluate = new JMenuItem("Evaluate");
        editMenu.add(menuItem_Evaluate);
        menuItem_Evaluate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		evaluateCircuits();
        	}
        });
        
        
        JMenu helpMenu = new JMenu("About & Help");
        menuBar.add(helpMenu);
        
        JMenuItem menuItem_About = new JMenuItem("About");
        helpMenu.add(menuItem_About);
        menuItem_About.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EventQueue.invokeLater(new Runnable()
                { @Override
                    public void run() { showInformationAboutProgram(); } });
        	}
        });
        
        JMenuItem menuItem_Help = new JMenuItem("Help");
        helpMenu.add(menuItem_Help);
        menuItem_Help.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EventQueue.invokeLater(new Runnable()
                { @Override
                    public void run() { openHelpMenu(); } }); }
        });    
    }
    
    private void clickedSave() {
    	System.out.println("Clicked on Save");
    }
    
    private void clickedLoad() {
    	System.out.println("Clicked on Load");
    }
    
    private void resetBoard() {
    	System.out.println("Resetting Board");
    }
    
    private void evaluateCircuits() {
    	System.out.println("Evaluating all circuits on the board");
    }
    
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
 * Internal class used to draw elements within a JPanel. The Canvas class loads
 * images from an asset folder inside the main project folder.
 * @author cmcgrego, Dominik Baumann
 */
class Canvas extends JPanel {

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
    
    TileType[][] currentTiles;  //the current 2D array of tiles to display

    
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
        try { // Took code for generating the filePath from here: https://stackoverflow.com/a/15804263
        	
        	// path of project Folder
        	String absolutePath = new File(".").getAbsolutePath(); 
        	
        	// remove the dot at the end of path
        	absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
        	tileEmpty = ImageIO.read(new File(absolutePath + "bin\\Assets\\tileEmpty64.png"));
            
        	tileTRUE = ImageIO.read(new File(absolutePath + "bin\\Assets\\TRUE.png"));
            tileFALSE = ImageIO.read(new File(absolutePath + "bin\\Assets\\FALSE.png"));
        	
            tileAND_White = ImageIO.read(new File(absolutePath + "bin\\Assets\\AND_White.png"));
            tileAND_TRUE = ImageIO.read(new File(absolutePath + "bin\\Assets\\AND_TRUE.png"));
            tileAND_FALSE = ImageIO.read(new File(absolutePath + "bin\\Assets\\AND_FALSE.png"));
            
            tileNAND_White = ImageIO.read(new File(absolutePath + "bin\\Assets\\NAND_White.png"));
            tileNAND_TRUE = ImageIO.read(new File(absolutePath + "bin\\Assets\\NAND_TRUE.png"));
            tileNAND_FALSE = ImageIO.read(new File(absolutePath + "bin\\Assets\\NAND_FALSE.png"));
            
            tileNOR_White = ImageIO.read(new File(absolutePath + "bin\\Assets\\NOR_White.png"));
            tileNOR_TRUE = ImageIO.read(new File(absolutePath + "bin\\Assets\\NOR_TRUE.png"));
            tileNOR_FALSE = ImageIO.read(new File(absolutePath + "bin\\Assets\\NOR_FALSE.png"));
            
            tileNOT_White = ImageIO.read(new File(absolutePath + "bin\\Assets\\NOT_White.png"));
            tileNOT_TRUE = ImageIO.read(new File(absolutePath + "bin\\Assets\\NOT_TRUE.png"));
            tileNOT_FALSE = ImageIO.read(new File(absolutePath + "bin\\Assets\\NOT_FALSE.png"));
            
            tileOR_White = ImageIO.read(new File(absolutePath + "bin\\Assets\\OR_White.png"));
            tileOR_TRUE = ImageIO.read(new File(absolutePath + "bin\\Assets\\OR_TRUE.png"));
            tileOR_FALSE = ImageIO.read(new File(absolutePath + "bin\\Assets\\OR_FALSE.png"));
            
            tileXOR_White = ImageIO.read(new File(absolutePath + "bin\\Assets\\XOR_White.png"));
            tileXOR_TRUE = ImageIO.read(new File(absolutePath + "bin\\Assets\\XOR_TRUE.png"));
            tileXOR_FALSE = ImageIO.read(new File(absolutePath + "bin\\Assets\\XOR_FALSE.png"));
            
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
                        switch (currentTiles[i][j]) 
                        {
                            case EMPTYTILE:
                                g2.drawImage(tileEmpty, i * BoardGUI.TILE_WIDTH, j * BoardGUI.TILE_HEIGHT, null);
                                break;
                        }
                    }
                }
            }
        }
    }
}
}