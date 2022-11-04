package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;

import javax.imageio.ImageIO;

/** This class maintains a mapping, which allows
 * to get the proper visualization of each 
 * {@link gates.Gate gate} based on its
 * {@link gui.TileType type}.
 * <p>
 * Images are taken and modified from this
 * <a href="https://en.wikipedia.org/wiki/Logic_gate#Symbols">Wikipedia-article</a>
 * under the public domain.
 * @author Dominik Baumann, Cameron McGregor
 * @version 2, July 2022
 */
public class ImageStorage {
    
    /** Store the images for easy future access. */
    EnumMap<TileType, BufferedImage> imageMap;
    
    
    /** The GUI parent for displaying everything. */
    LogicBoardGUI boardGUI;
    
    
    
    /** Load the images from their files and set up the mapping.
     * @param boardGUI_ The GUI parent for displaying everything.
     */
    public ImageStorage(LogicBoardGUI boardGUI_) {
    	boardGUI = boardGUI_;
    	imageMap = new EnumMap<TileType, BufferedImage>(TileType.class);
    	loadTileImages();
    }
    
    
    
    /** Allow the GUI to get the proper visualization of a 
     * {@link gates.Gate gate} based on its {@link gui.TileType type}.
     * @param t Identifier for the gate to be visualized.
     * @return The proper image (tile) for visualizing the given gate.
     */
    public BufferedImage getImage(TileType t) { return imageMap.get(t); }
    
    
    
    /**Loads tiles images from a fixed folder location within the project directory. */
    private void loadTileImages() {
        try { // Adapted code for generating the filePath from here: https://stackoverflow.com/a/15804263
        	
        	// path of project Folder
        	String absolutePath = new File(".").getAbsolutePath(); 
        	
        	// remove the dot at the end of path
        	absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
        	
        	// load images directly into EnumMap
        	imageMap.put(TileType.EMPTY, ImageIO.read(new File(absolutePath + "assets\\tileEmpty64.png")));
			imageMap.put(TileType.FALSE, ImageIO.read(new File(absolutePath + "assets\\FALSE.png")));
			imageMap.put(TileType.TRUE, ImageIO.read(new File(absolutePath + "assets\\TRUE.png")));
			
			imageMap.put(TileType.AND, ImageIO.read(new File(absolutePath + "assets\\AND_White.png")));
			imageMap.put(TileType.AND_TRUE, ImageIO.read(new File(absolutePath + "assets\\AND_TRUE.png")));
			imageMap.put(TileType.AND_FALSE, ImageIO.read(new File(absolutePath + "assets\\AND_FALSE.png")));
                    			    	
			imageMap.put(TileType.NAND, ImageIO.read(new File(absolutePath + "assets\\NAND_White.png")));
			imageMap.put(TileType.NAND_TRUE, ImageIO.read(new File(absolutePath + "assets\\NAND_TRUE.png")));
			imageMap.put(TileType.NAND_FALSE, ImageIO.read(new File(absolutePath + "assets\\NAND_FALSE.png")));
			    	
			imageMap.put(TileType.NOR, ImageIO.read(new File(absolutePath + "assets\\NOR_White.png")));
			imageMap.put(TileType.NOR_TRUE, ImageIO.read(new File(absolutePath + "assets\\NOR_TRUE.png")));
			imageMap.put(TileType.NOR_FALSE, ImageIO.read(new File(absolutePath + "assets\\NOR_FALSE.png")));

			imageMap.put(TileType.NOT, ImageIO.read(new File(absolutePath + "assets\\NOT_White.png")));
			imageMap.put(TileType.NOT_TRUE, ImageIO.read(new File(absolutePath + "assets\\NOT_TRUE.png")));
			imageMap.put(TileType.NOT_FALSE, ImageIO.read(new File(absolutePath + "assets\\NOT_FALSE.png")));
			
			imageMap.put(TileType.OR, ImageIO.read(new File(absolutePath + "assets\\OR_White.png")));
			imageMap.put(TileType.OR_TRUE, ImageIO.read(new File(absolutePath + "assets\\OR_TRUE.png")));
			imageMap.put(TileType.OR_FALSE, ImageIO.read(new File(absolutePath + "assets\\OR_FALSE.png")));
			    	
			imageMap.put(TileType.XOR, ImageIO.read(new File(absolutePath + "assets\\XOR_White.png")));
			imageMap.put(TileType.XOR_TRUE, ImageIO.read(new File(absolutePath + "assets\\XOR_TRUE.png")));
			imageMap.put(TileType.XOR_FALSE, ImageIO.read(new File(absolutePath + "assets\\XOR_FALSE.png")));
            
            for(TileType t : TileType.values()) {
              assert imageMap.get(t).getHeight() == boardGUI.getTileHeight() &&
              imageMap.get(t).getWidth() == boardGUI.getTileWidth();
            }

        } catch (IOException e) {
            System.out.println("Exception loading images: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}