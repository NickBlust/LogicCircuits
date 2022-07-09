/**
 * 
 */
package app;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import boardModel.LogicBoard;

/**
 * @author domin
 *
 */
public class BoardSaver {

	public static void save(LogicBoard model, int width, int height) {
	if(model != null) {
		int numOfGates = model.numberOfGates();
		if(numOfGates <= 0) {
			// TODO: Open fail window
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
	            	// TODO: open fail window
	                ex.printStackTrace();
	            }
	        }
	}
}