/**
 * 
 */
package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import gates.*;
import logicCircuits.LogicCircuit;

/**
 * @author Dominik Baumann
 * Load a board modelling a {@link logicCircuits.LogicCircuit logic circuit} into the GUI.
 * File format has to be as specified in {@link app.BoardSaver BoardSaver}.
 */
public class BoardLoader {
	
	public static LogicCircuit load() {
		LogicCircuit model = null;
    	JFileChooser chooser = new JFileChooser();
    	chooser.showOpenDialog(null);
    	try {
    		File saveFile = chooser.getSelectedFile();
    		// count number of lines in file => equals the length of the river
    		try { // counting lines in file
	    		Scanner fileInfo = new Scanner(saveFile);
	    		int rows = fileInfo.nextInt();
	    		int cols = fileInfo.nextInt();
	    		int numberOfGates = fileInfo.nextInt();
	    		fileInfo.nextLine();
	    		if(numberOfGates != 0) {
	    			model = new LogicCircuit(rows, cols);
	    			for(int i = 0; i < numberOfGates; i++) {
	    				Gate g = TileConverter.getGateFromName(fileInfo.next());
	    				int x = fileInfo.nextInt();
	    				int y = fileInfo.nextInt();
	    				model.addGate(g, x, y);
	    			}
	    			while(fileInfo.hasNextLine()) {
	    				model.connectGates(fileInfo.nextInt(), fileInfo.nextInt(),
	    						fileInfo.nextInt(), fileInfo.nextInt(), fileInfo.nextInt());
	    			}	
	    		}
	    		fileInfo.close();
    		} catch(NullPointerException e) {} // leave count at 0			

    	} catch (FileNotFoundException e) {
    	      e.printStackTrace();
    	}
    	return model;
	}
}