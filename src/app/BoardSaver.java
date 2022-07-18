/**
 * 
 */
package app;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import boardModel.LogicBoard;
import gui.InfoWindow;

/**
 * @author domin
 *
 */
public class BoardSaver {

	static InfoWindow savingFailedWindow;
	static InfoWindow whySaveEmptyBoardWindow;
	
	public static void save(LogicBoard model, int width, int height) {
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
	
	private static void openSavingFailedWindow() {
		if(savingFailedWindow != null) // only allow one open help window at a time
			savingFailedWindow.dispatchEvent(new WindowEvent(savingFailedWindow, WindowEvent.WINDOW_CLOSING));
		savingFailedWindow = new InfoWindow("Saving Failed", 400, 80, "  The board could not be saved ...");
	}
	

	private static void openWhySaveEmptyBoardWindow() {
		if(whySaveEmptyBoardWindow != null) // only allow one open help window at a time
			whySaveEmptyBoardWindow.dispatchEvent(new WindowEvent(whySaveEmptyBoardWindow, WindowEvent.WINDOW_CLOSING));
		whySaveEmptyBoardWindow = new InfoWindow("Why try to save an empty board?", 400, 80, "  Why are you trying to save an empty board?");		
	}
}