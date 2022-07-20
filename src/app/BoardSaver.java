package app;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import boardModel.LogicBoard;
import gui.InfoWindow;

/**
 * @author Dominik Baumann
 * @version 2, July 2022
 * <p>
 * This class provides the functionality to save a given
 * {@link boardModel.LogicBoard LogicBoard} to a .txt-file,
 * from which it can be read and loaded.
 * <p>
 * Correct formatting for such a file is:
 * numberOfColumns numberOfRows numberOfGates
 * gateName columnPositionOfGate rowPositionOfGate //TODO correct use of column and row
 * ... all gates ...
 * columnOfGateProvidingInput rowofGateProvidingInput columnOfGateReceivingInput rowOfGateReceivingInput inputIndex
 * 
 * <b>NOTES: </b>
 * <ol>
 * <li> inputIndex is a String ("TOP" or "BOTTOM") referring to 
 * {@link gates.GateIndex#TOP GateIndex.TOP} or {@link gates.GateIndex#BOTTOM GateIndex.BOTTOM}.
 * <li> gateName is a String from which the correct type of {@link gates.Gate Gate} can be inferred.
 * </ol>
 */
public class BoardSaver {

	/** A small window displaying a message that saving the board failed. */
	InfoWindow savingFailedWindow;
	
	/** A small window displaying a message that empty boards should and will not be saved. */
	InfoWindow whySaveEmptyBoardWindow;
	
	/** Save the given model on a board with a given number of rows (height) and columns (width).
	 * Failure to save the board or trying to save an empty board will open a window with a warning / error message. 
	 * @param model The {@link boardModel.LogicBoard board} displayed on the GUI.
	 * @param width The number of columns this board has.
	 * @param height The number of rows this board has.
	 */
	public void save(LogicBoard model, int width, int height) {
	if(model != null) {
		int numOfGates = model.numberOfGates();
		if(numOfGates <= 0) {
    		EventQueue.invokeLater(new Runnable()
            { @Override public void run() { openWhySaveEmptyBoardWindow(); } });
			return;
		}
			
		try {
	    	JFileChooser chooser = new JFileChooser();
	    	int retrival = chooser.showSaveDialog(null);
	        if (retrival == JFileChooser.APPROVE_OPTION) {    	            
                FileWriter writer = new FileWriter(chooser.getSelectedFile() + ".txt");                            
                writer.write(width + " " + height + " " + numOfGates + "\n");
            	writer.write(model.toStorageString());
                writer.close();
        	} // end if
	            } catch (IOException ex) {
	            	EventQueue.invokeLater(new Runnable()
	                { @Override
	                    public void run() { openSavingFailedWindow(); } });
	                ex.printStackTrace();
	            }
	        }
	}
	
	/** Open a small window displaying a message that saving the board failed. */
	private void openSavingFailedWindow() {
		if(savingFailedWindow != null) // only allow one open window at a time
			savingFailedWindow.dispatchEvent(new WindowEvent(savingFailedWindow, WindowEvent.WINDOW_CLOSING));
		savingFailedWindow = new InfoWindow("Saving Failed", 400, 80, "  The board could not be saved ...");
	}
	
	/** Open a small window displaying a message that empty boards should and will not be saved. */
	private void openWhySaveEmptyBoardWindow() {
		if(whySaveEmptyBoardWindow != null) // only allow one open window at a time
			whySaveEmptyBoardWindow.dispatchEvent(new WindowEvent(whySaveEmptyBoardWindow, WindowEvent.WINDOW_CLOSING));
		whySaveEmptyBoardWindow = new InfoWindow("Why try to save an empty board?", 400, 80, "  Why are you trying to save an empty board?");		
	}
}