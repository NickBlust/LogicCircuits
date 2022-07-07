/**
 * 
 */
package app;

import java.awt.EventQueue;

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
            	controller.start();
            }
        });
    }
}