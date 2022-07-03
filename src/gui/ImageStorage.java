/**
 * 
 */
package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author domin
 *
 */
public class ImageStorage {

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
    
    public ImageStorage() {
    	loadTileImages();
    }
    
    public BufferedImage getImage()
    
    /**
     * Loads tiles images from a fixed folder location within the project directory
     */
    private void loadTileImages() {
        try { // Took code for generating the filePath from here: https://stackoverflow.com/a/15804263
        	
        	// path of project Folder
        	String absolutePath = new File(".").getAbsolutePath(); 
        	
        	// remove the dot at the end of path
        	absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
tileEmpty = ImageIO.read(new File(absolutePath + "bin\\assets\\tileEmpty64.png"));
            
        	tileTRUE = ImageIO.read(new File(absolutePath + "bin\\assets\\TRUE.png"));
            tileFALSE = ImageIO.read(new File(absolutePath + "bin\\assets\\FALSE.png"));
        	
            tileAND_White = ImageIO.read(new File(absolutePath + "bin\\assets\\AND_White.png"));
            tileAND_TRUE = ImageIO.read(new File(absolutePath + "bin\\assets\\AND_TRUE.png"));
            tileAND_FALSE = ImageIO.read(new File(absolutePath + "bin\\assets\\AND_FALSE.png"));
            
            tileNAND_White = ImageIO.read(new File(absolutePath + "bin\\assets\\NAND_White.png"));
            tileNAND_TRUE = ImageIO.read(new File(absolutePath + "bin\\assets\\NAND_TRUE.png"));
            tileNAND_FALSE = ImageIO.read(new File(absolutePath + "bin\\assets\\NAND_FALSE.png"));
            
            tileNOR_White = ImageIO.read(new File(absolutePath + "bin\\assets\\NOR_White.png"));
            tileNOR_TRUE = ImageIO.read(new File(absolutePath + "bin\\assets\\NOR_TRUE.png"));
            tileNOR_FALSE = ImageIO.read(new File(absolutePath + "bin\\assets\\NOR_FALSE.png"));
            
            tileNOT_White = ImageIO.read(new File(absolutePath + "bin\\assets\\NOT_White.png"));
            tileNOT_TRUE = ImageIO.read(new File(absolutePath + "bin\\assets\\NOT_TRUE.png"));
            tileNOT_FALSE = ImageIO.read(new File(absolutePath + "bin\\assets\\NOT_FALSE.png"));
            
            tileOR_White = ImageIO.read(new File(absolutePath + "bin\\assets\\OR_White.png"));
            tileOR_TRUE = ImageIO.read(new File(absolutePath + "bin\\assets\\OR_TRUE.png"));
            tileOR_FALSE = ImageIO.read(new File(absolutePath + "bin\\assets\\OR_FALSE.png"));
            
            tileXOR_White = ImageIO.read(new File(absolutePath + "bin\\assets\\XOR_White.png"));
            tileXOR_TRUE = ImageIO.read(new File(absolutePath + "bin\\assets\\XOR_TRUE.png"));
            tileXOR_FALSE = ImageIO.read(new File(absolutePath + "bin\\assets\\XOR_FALSE.png"));
            
            // only need temporarily for size verification of the images
            BufferedImage[] temp = { tileEmpty, tileFALSE, tileTRUE,
            		    tileAND_White, tileAND_TRUE, tileAND_FALSE,
            		    tileNAND_White, tileNAND_TRUE, tileNAND_FALSE,
            		    tileNOR_White, tileNOR_TRUE, tileNOR_FALSE,
            		    tileNOT_White, tileNOT_TRUE, tileNOT_FALSE,
            		    tileOR_White, tileOR_TRUE, tileOR_FALSE,
            		    tileXOR_White, tileXOR_TRUE, tileXOR_FALSE };
            
            for(BufferedImage b : temp) {
              assert b.getHeight() == LogicBoardGUI.TILE_HEIGHT &&
              b.getWidth() == LogicBoardGUI.TILE_WIDTH;
            }

        } catch (IOException e) {
            System.out.println("Exception loading images: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}
