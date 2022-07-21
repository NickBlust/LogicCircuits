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

/** The button palette is a scrollable panel
 * displayed on the right of the main window.
 * It allows the user to select which kind of 
 * gate they want to place.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class ButtonPalette extends JPanel {

	/** Responsible for handling user input. */
	private Controller controller;
	
	/** Stores the images to place on the buttons
	 * (as well as other images).
	 */
	private ImageStorage images;
	
	
	/** The buttons are stored in an array, and the
	 * this value stores the index of the last
	 * gate / tile / button that was clicked.
	 * <p>
	 * Starts as -1 (could be any other value out of the array range)
	 * because otherwise the user could not select the button
	 * with this index in the array.
	 */
	private int indexOfLastSelectedTile = -1;
	
    /** The types of tiles / gates which are placed when the
     * corresponding button is selected.
     */
    private TileType[] tileTypes;

    /** The buttons for selecting which tile to place. */
    private JButton[] tileButtons;
	
	
	/** Construct the palette for selecting Gates.
	 * @param imageStorage Load the images for the buttons from here.
	 * @param controller_ TODO do we actually need this?
	 */
	public ButtonPalette(ImageStorage imageStorage, Controller controller_) {
		images = imageStorage;
		controller = controller_;
		initPalette();
	}
	
	/**
     * Provide all buttons for gate / tile selection 
     * in a scroll pane on the right-hand side of the main frame.
     * It is <b>CRUCIAL</b> that the arrays for the button initialization
     * are ordered correctly!
     */
    private void initPalette() {
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
        
        tileTypes = new TileType[] { TileType.EMPTY, TileType.FALSE, TileType.TRUE,
        		TileType.AND, TileType.OR, TileType.NOT, TileType.NOR, TileType.NAND, TileType.XOR };
        
        tileButtons = new JButton[] { button_Empty, button_FALSE, button_TRUE, 
                button_AND, button_OR, button_NOT, button_NOR, button_NAND, button_XOR };

        for(int i = 0; i < tileButtons.length; i++) {
        	final int k = i; // later the index has to final for access to tileTypes
        	tileButtons[i] = new JButton(new ImageIcon(images.getImage(tileTypes[i])));
        	tileButtons[i].setText(buttonTexts[i]);
        	tileButtons[i].addActionListener(new ActionListener(){  
        		@Override
        		public void actionPerformed(ActionEvent e){        			
        			// no button selected => select it and color the button
        			if(controller.isSelectedTileToPlace(null))
        				selectButton(k);

        			else { // if a button is selected => deselect it and uncolor the button 
        				deselectButton(indexOfLastSelectedTile);
        				
        				// if last selected button was now clicked button => don't reselect
        				if(k != indexOfLastSelectedTile) 
        					selectButton(k);
        			} // end if/else
        		} });  
        	this.add(tileButtons[i], new GBConstraints(0, i, 5));
        }
    }
    
    /** Select the button with this index.
     * @param k The index of a button in the palette.
     */
    private void selectButton(int k) {
		indexOfLastSelectedTile = k;
		controller.setSelectedTileToPlace(tileTypes[k]);
		tileButtons[k].setBackground(Color.ORANGE);
    }

    /** Deselect the button with this index.
     * @param k The index of a button in the palette.
     */
    private void deselectButton(int k) {
		controller.setSelectedTileToPlace(null);
		tileButtons[indexOfLastSelectedTile].setBackground(null);
    }
    
	
	/**
	 * Internal class to simplify the button instantiation in 
	 * {@link gui.ButtonPalette#initPalette() initPalette}.
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
		
		/** This is just here because Eclipse complained about it. */
		private static final long serialVersionUID = 1L;
	}
	
	/** This is just here because Eclipse complained about it. */
	private static final long serialVersionUID = 1L;
}