package LogicBoard;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import LogicBoard.BoardEditor.TileType;


/**
 * The BoardGUI class is responsible for rendering graphics to the screen to display
 * the grid.
 * @author cmcgregor
 */
public class BoardGUI extends JFrame
{

    /**
     * The two final int attributes below set the size of some graphical elements,
     * specifically the display height and width of tiles on the board. Tile sizes 
     * should match the size of the image files used.
     */
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;

    /**
     * The canvas is the area that graphics are drawn to. It is an internal class
     * of the BoardGUI class.
     */
    Canvas canvas;

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
        add(canvas = new Canvas());     //adds canvas to this frame
        setTitle("LogicCircuit");
        setSize(816, 615); //Can be changed by user but must fit the measurements of the tile
        setLocationRelativeTo(null);        //sets position of frame on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

     /**
     * Method to update the graphical elements on the screen
     */
    public void updateDisplay(TileType[][] tiles) 
    {
        canvas.update(tiles);
    }

/**
 * Internal class used to draw elements within a JPanel. The Canvas class loads
 * images from an asset folder inside the main project folder.
 * @author cmcgrego
 */
class Canvas extends JPanel {

    private BufferedImage tileEmpty;
    private BufferedImage tileOR; //TO BE ADDED
    private BufferedImage tileAND; //TO BE ADDED

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
        try {

            tileEmpty = ImageIO.read(new File("assets/tileEmpty.png"));
            assert tileEmpty.getHeight() == BoardGUI.TILE_HEIGHT &&
                    tileEmpty.getWidth() == BoardGUI.TILE_WIDTH;

        } catch (IOException e) 
        {
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