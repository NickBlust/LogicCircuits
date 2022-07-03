/**
 * 
 */
package app;

import java.awt.EventQueue;

import gates.*;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk, Cameron McGregor
 *
 */
public class Launcher {
	
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
        
            /**
             * The run method starts the board in a separate thread. It creates
             * the GUI.
             */
            @Override
            public void run() {
            	System.out.println("Hello there!");
            	Controller controller = new Controller();
//                BoardGUI gui = new BoardGUI();            //create GUI
//                                   //display GUI
//                BoardEditor eng = new BoardEditor(gui);   //create engine
//                eng.startBoard();                        //starts the game
            }
        });
    }
}