/**
 * 
 */
package app;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import logicCircuits.LogicCircuit;
import utility.ConnectionInfo;
import utility.PositionInfo;
import utility.Vector2Int;

/**
 * @author Dominik Baumann
 * This class stores the current layout of a Board on the GUI into a .txt-file.
 * The format is as follows:
 * 	#row + " " + #columns + " " + numberOfTilesOnBoard
 *  #rowOfThisTile + " " + #columnOfThisTile + " " + tileName
 *  --> repeat  numberOfTilesOnBoard-many lines
 *  #rowOfInput + " " + #columnOfInput + " " + #rowOfTarget + " " + #columnOfTarget + " " + #slot_ID_OnTarget
 *  --> repeat until all connections are listed
 * 
 * @see BoardLoader
 * @see gui.BoardGUI
 * @see LogicCircuit
 */
public class BoardSaver {

	/**
	 * Save a given model to a .txt-file.
	 * See class description for format description.
	 * @param model the model to save
	 */
	public static void save(LogicCircuit model) {
    	if(model != null) {
    		try {
    	    	JFileChooser chooser = new JFileChooser();
    	    	int retrival = chooser.showSaveDialog(null);
    	        if (retrival == JFileChooser.APPROVE_OPTION) {    	            
    	        		// create file 
    	                FileWriter writer = new FileWriter(chooser.getSelectedFile() + ".txt");
    	                
    	                // get data
    	                Vector2Int dimensions = model.getModelDimensions();
    	                ArrayList<PositionInfo> gatesWithPositions = model.getAllGates();
    	                ArrayList<ConnectionInfo> connections = model.getConnections();
    	                
    	                // write to file    	            
    	                writer.write(dimensions.x + " " + dimensions.y + " " + gatesWithPositions.size() + "\n");
    	                for(int i = 0; i < gatesWithPositions.size() - 1; i++) {
    	                	writer.write(gatesWithPositions.get(i).toStorageString() + "\n");
    	                }
    	                if(gatesWithPositions.size() > 0)
    	                	writer.write(gatesWithPositions.get(gatesWithPositions.size() - 1).toStorageString() + "\n");
    	                for(int i = 0; i < connections.size() - 1; i++) {
    	                	writer.write(connections.get(i).toStorageString() + "\n");
    	                }
    	                if(connections.size() > 0)
    	                	writer.write(connections.get(connections.size() - 1).toStorageString());
    		        	writer.close();
    	        } // end if
    	            } catch (IOException ex) {
    	                ex.printStackTrace();
    	            }
    	        }
	}
}
