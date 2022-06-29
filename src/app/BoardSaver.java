/**
 * 
 */
package app;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import gates.Gate;
import logicCircuits.LogicCircuit;
import utility.ConnectionInfo;
import utility.PositionInfo;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class BoardSaver {

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
    	                
    	                System.out.println("SAVING: " + dimensions);
    	                
    	                // write to file
    	             // format suggestion:
    	            	// #row + " " + #columns + " " + numberOfTilesOnBoard
    	            	// #rowOfThisTile + " " + #columnOfThisTile + " " + tileName
    	            	// ... numberOfTilesOnBoard-many lines
    	            	// #rowOfInput + " " + #columnOfInput + " " + #rowOfTarget + " " + #columnOfTarget + " " + #slotIDOnTarget
    	            	// ... (iterate until file has no more nextLine)
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
