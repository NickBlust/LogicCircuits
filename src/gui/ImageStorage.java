/**
 * 
 */
package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;

import javax.imageio.ImageIO;

/**
 * @author domin
 *
 */
public class ImageStorage {
    
    EnumMap<TileType, BufferedImage> imageMap;
    LogicBoardGUI boardGUI;
    
    public ImageStorage(LogicBoardGUI boardGUI_) {
    	boardGUI = boardGUI_;
    	imageMap = new EnumMap<TileType, BufferedImage>(TileType.class);
    	loadTileImages();
    }
    
    public BufferedImage getImage(TileType t) { return imageMap.get(t); }
    
    /**
     * Loads tiles images from a fixed folder location within the project directory
     */
    private void loadTileImages() {
        try { // Took code for generating the filePath from here: https://stackoverflow.com/a/15804263
        	
        	// path of project Folder
        	String absolutePath = new File(".").getAbsolutePath(); 
        	// remove the dot at the end of path
        	absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
        	// load images directly into EnumMap
        	imageMap.put(TileType.EMPTY, ImageIO.read(new File(absolutePath + "bin\\assets\\tileEmpty64.png")));
			imageMap.put(TileType.FALSE, ImageIO.read(new File(absolutePath + "bin\\assets\\FALSE.png")));
			imageMap.put(TileType.TRUE, ImageIO.read(new File(absolutePath + "bin\\assets\\TRUE.png")));
			
			imageMap.put(TileType.AND, ImageIO.read(new File(absolutePath + "bin\\assets\\AND_White.png")));
			imageMap.put(TileType.AND_TRUE, ImageIO.read(new File(absolutePath + "bin\\assets\\AND_TRUE.png")));
			imageMap.put(TileType.AND_FALSE, ImageIO.read(new File(absolutePath + "bin\\assets\\AND_FALSE.png")));
                    			    	
			imageMap.put(TileType.NAND, ImageIO.read(new File(absolutePath + "bin\\assets\\NAND_White.png")));
			imageMap.put(TileType.NAND_TRUE, ImageIO.read(new File(absolutePath + "bin\\assets\\NAND_TRUE.png")));
			imageMap.put(TileType.NAND_FALSE, ImageIO.read(new File(absolutePath + "bin\\assets\\NAND_FALSE.png")));
			    	
			imageMap.put(TileType.NOR, ImageIO.read(new File(absolutePath + "bin\\assets\\NOR_White.png")));
			imageMap.put(TileType.NOR_TRUE, ImageIO.read(new File(absolutePath + "bin\\assets\\NOR_TRUE.png")));
			imageMap.put(TileType.NOR_FALSE, ImageIO.read(new File(absolutePath + "bin\\assets\\NOR_FALSE.png")));

			imageMap.put(TileType.NOT, ImageIO.read(new File(absolutePath + "bin\\assets\\NOT_White.png")));
			imageMap.put(TileType.NOT_TRUE, ImageIO.read(new File(absolutePath + "bin\\assets\\NOT_TRUE.png")));
			imageMap.put(TileType.NOT_FALSE, ImageIO.read(new File(absolutePath + "bin\\assets\\NOT_FALSE.png")));
			
			imageMap.put(TileType.OR, ImageIO.read(new File(absolutePath + "bin\\assets\\OR_White.png")));
			imageMap.put(TileType.OR_TRUE, ImageIO.read(new File(absolutePath + "bin\\assets\\OR_TRUE.png")));
			imageMap.put(TileType.OR_FALSE, ImageIO.read(new File(absolutePath + "bin\\assets\\OR_FALSE.png")));
			    	
			imageMap.put(TileType.XOR, ImageIO.read(new File(absolutePath + "bin\\assets\\XOR_White.png")));
			imageMap.put(TileType.XOR_TRUE, ImageIO.read(new File(absolutePath + "bin\\assets\\XOR_TRUE.png")));
			imageMap.put(TileType.XOR_FALSE, ImageIO.read(new File(absolutePath + "bin\\assets\\XOR_FALSE.png")));
            
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