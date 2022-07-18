/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import app.Controller;

/**
 * @author domin
 *
 */
public class ButtonPalette extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImageStorage images;
	private Controller controller;
	
	/** TODO: explain why this has to start as -1
	 * 
	 */
	private int indexOfLastSelectedTile = -1;
	
	public ButtonPalette(ImageStorage imageStorage, Controller controller_) {
		images = imageStorage;
		controller = controller_;
		initPalette();
	}
	
	/**
     * Provide all buttons for gate / tile selection 
     * in a scroll pane on the right-hand side of the main frame.
     * It is CRUCIAL that the arrays for the button initialization
     * are ordered correctly!
     */
    private void initPalette() {
//    	JPanel panel = new JPanel();
//        getContentPane().add(panel, BorderLayout.EAST);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gbl_panel);
        
        JButton button_Empty = null, button_FALSE = null, button_TRUE = null, 
                button_AND = null, button_OR = null, button_NOT = null, 
                button_NOR = null, button_NAND = null, button_XOR = null;
        
        String[] buttonTexts = {"   Empty  ", "   FALSE  ", "    TRUE  ", "     AND  ",
        		"      OR  ", "     NOT  ", "     NOR  ", "    NAND  ", "     XOR  "};
        
        TileType[] tileTypes = { TileType.EMPTY, TileType.FALSE, TileType.TRUE,
        		TileType.AND, TileType.OR, TileType.NOT, TileType.NOR, TileType.NAND, TileType.XOR };
        
        JButton[] tileButtons = { button_Empty, button_FALSE, button_TRUE, 
                button_AND, button_OR, button_NOT, button_NOR, button_NAND, button_XOR };

        for(int i = 0; i < tileButtons.length; i++) {
        	final int k = i; // later the index has to final for access to tileTypes
        	tileButtons[i] = new JButton(new ImageIcon(images.getImage(tileTypes[i])));
        	tileButtons[i].setText(buttonTexts[i]);
        	tileButtons[i].addActionListener(new ActionListener(){  
        		@Override
        		public void actionPerformed(ActionEvent e){
        			// if no button selected = select it and color the button
        			// if button is selected => deselect it and uncolor the button
        			// if last selected button was now clicked button = don't reselect        			
        			if(controller.selectedTileToPlace == null) {
        				indexOfLastSelectedTile = k;
        				controller.selectedTileToPlace = tileTypes[k];
        				tileButtons[k].setBackground(Color.ORANGE);
        			}
        			else { // selectedTileToPlace != null => deselect
        				tileButtons[indexOfLastSelectedTile].setBackground(null);
        				controller.selectedTileToPlace = null;
        				if(k != indexOfLastSelectedTile) { // try selecting new Tile
            				indexOfLastSelectedTile = k;
            				tileButtons[k].setBackground(Color.ORANGE);
            				controller.selectedTileToPlace = tileTypes[k];
        				} // end inner if
        			} // end if/else
        		} });  
        	this.add(tileButtons[i], new GBConstraints(0, i, 5));
        }
    }
	
	/**
	 * Internal class to simplify the button instantiation in 
	 * {@link gui.ButtonPalette#initPalette() initPalette}.
	 */
	class GBConstraints extends GridBagConstraints {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
}