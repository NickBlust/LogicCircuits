/**
 * 
 */
package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JFileChooser;

import boardModel.Converter;
import boardModel.LogicBoard;
import gates.Gate;
import gui.LogicBoardGUI;
import utility.Vector2Int;

/**
 * @author domin
 *
 */
public class BoardLoader {
	
	public static boolean load(LogicBoard model, LogicBoardGUI gui) {
		JFileChooser chooser = new JFileChooser();
    	chooser.showOpenDialog(null);
    	
    	TreeMap<Vector2Int, Gate> gates = new TreeMap<Vector2Int, Gate>();
    	HashMap<Gate, Integer> outputGates = new HashMap<Gate, Integer>();
    	File saveFile;
    	Scanner fileInfo;
    
    	try {
    		// TODO: validate save File (i.e. check if this is a valid input file
    		saveFile = chooser.getSelectedFile();
//    		// count number of lines in file => equals the length of the river
//    		try { // counting lines in file
	    		fileInfo = new Scanner(saveFile);
	    		int cols = fileInfo.nextInt();
	    		int rows = fileInfo.nextInt();
	    		int numberOfGates = fileInfo.nextInt();
	    		fileInfo.nextLine();
	    		
	    		gui.setDimensions(rows, cols);
	    		for(int i = 0; i < numberOfGates; i++) {
	    			Gate g = Converter.getGateFromName(fileInfo.next());
	    			int row = fileInfo.nextInt();
    				int col = fileInfo.nextInt();
    				model.addGate(g, new Vector2Int(row, col));
	    		}
//	    		String line = fileInfo.nextLine();
//	    		while(fileInfo.hasNextLine()) {
//	    			System.out.println(fileInfo.nextLine());
//	    		}
	    		fileInfo.close();	
	    		return true;
    	} catch (FileNotFoundException 
    			| java.lang.NullPointerException 
    			| java.util.NoSuchElementException ex) {
    		// TODO open window with error message but do nothing else
			return false;
    	}
	}
}