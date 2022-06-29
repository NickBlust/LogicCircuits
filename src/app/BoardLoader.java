/**
 * 
 */
package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

import gates.*;
import logicCircuits.LogicCircuit;

/**
 * @author Dominik Baumann
 *
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
	    		System.out.println("Blub " + rows + " " + cols + " " + numberOfGates);
	    		System.out.println(fileInfo.nextLine());
	    		if(numberOfGates != 0) {
	    			model = new LogicCircuit(rows, cols);
	    			for(int i = 0; i < numberOfGates; i++) {
	    				Gate g = Gate.getGateFromName(fileInfo.next());
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
