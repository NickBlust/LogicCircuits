package app;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import boardModel.Converter;
import boardModel.LogicBoard;
import gates.Gate;
import gates.GateIndex;
import gui.InfoWindow;
import gui.LogicBoardGUI;
import utility.Vector2Int;

/**
 * This class provides the functionality to load a board
 * @author Dominik Baumann
 * @version 2, July 2022
 * <p>
 * This class provides the functionality to load a given
 * {@link boardModel.LogicBoard LogicBoard} from a .txt-file
 * with proper formatting. The proper formatting is described in 
 * {@link app.BoardSaver BoardSaver}.
 */
public class BoardLoader {
	
	/** A small window displaying a message that loading the board failed. */
	InfoWindow loadingFailedWindow;
	
	
	
	/** Load a logic circuit model from a .txt-file.
	 * Failure to load the board will open a window with an error message.
	 * @param model The {@link boardModel.LogicBoard LogicBoard}-Model the information
	 * from the file will be applied to.
	 * @param gui The {@link gui.LogicBoardGUI GUI} which is to display the loaded model.
	 * @return "true" if loading was successful, false otherwise.
	 */
	public boolean load(LogicBoard model, LogicBoardGUI gui) {
		JFileChooser chooser = new JFileChooser();
    	chooser.showOpenDialog(null);
    	
    	File saveFile;
    	Scanner fileInfo;
    
    	try {
	    		saveFile = chooser.getSelectedFile();
	    		fileInfo = new Scanner(saveFile);
	    		int cols = fileInfo.nextInt();
	    		int rows = fileInfo.nextInt();
	    		int numberOfGates = fileInfo.nextInt();
	    		fileInfo.nextLine();
	    		
	    		gui.setDimensions(rows, cols);
	    		for(int i = 0; i < numberOfGates; i++) { // add ages
	    			Gate g = Converter.getGateFromName(fileInfo.next());
	    			int row = fileInfo.nextInt();
    				int col = fileInfo.nextInt();
    				model.addGate(g, new Vector2Int(row, col));
	    		}
	    		
				fileInfo.nextLine();
	    		while(fileInfo.hasNextLine()) { // add connections
	    			int row_from = fileInfo.nextInt();
    				int col_from = fileInfo.nextInt();
    				int row_to = fileInfo.nextInt();
    				int col_to = fileInfo.nextInt();
    				String index = fileInfo.next();
    				model.addConnection(new Vector2Int(row_from, col_from), new Vector2Int(row_to, col_to), index.equals("TOP") ? GateIndex.TOP : GateIndex.BOTTOM);
    				fileInfo.nextLine();
	    		}
	    		fileInfo.close();	
	    		return true;
    	} catch (FileNotFoundException 
    			| java.util.NoSuchElementException ex) {
    		System.out.println(ex);
    		// open window with error message but do nothing else
    		EventQueue.invokeLater(new Runnable()
            { @Override public void run() { openLoadingFailedWindow(); } });    		
			return false;
    	} catch(java.lang.NullPointerException ex) { 
    		/* If the user cancels the process of opening a file,
    		 * don't display an error message. */
    		return false;
    	}
    	
	}
	/** Open a small window displaying a message that saving the board failed. */
	private void openLoadingFailedWindow() {
		if(loadingFailedWindow != null) // only allow one open window at a time
			loadingFailedWindow.dispatchEvent(new WindowEvent(loadingFailedWindow, WindowEvent.WINDOW_CLOSING));
		loadingFailedWindow = new InfoWindow("Loading Failed", 600, 420, "  Loading the board failed ... \n  Please make sure that the file is a .txt-file \n  and has the correct formatting, which is as follows: \n \n  In the first line should be three integers which \n  represent the number of coloumns, rows and gates. \n \n  In the next lines are specified the gates (type) and their \n  position by two ints (row, coloumn). \n \n  After that, all connections are specified, by stating one \n  connection in each line in the way \n  that four integers specify the row and coloumn of output and \n  input gate, and a TOP or BOTTOM specified if it reaches \n  the gate at top or bottom input.");
	}
}