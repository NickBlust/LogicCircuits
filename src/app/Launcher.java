package app;

import java.awt.EventQueue;

/**
 * Entry point into the program
 * @author Dominik Baumann, Philipp Grzywaczyk, Cameron McGregor
 * @version 2, July 2022
 * <p>
 * This class serves as the entry point into the program by 
 * creating a new {@link app.Controller controller}, which
 * is the central component of the entire program.
 */
public class Launcher {
	
    /** Creates a new {@link app.Controller controller}, which
     * is the central component of the entire program.
     * @param args (ignored)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            /** The run method starts the board in a separate thread. */
            @Override
            public void run() {
            	@SuppressWarnings("unused")
				Controller controller = new Controller();
            }
        });
    }
}