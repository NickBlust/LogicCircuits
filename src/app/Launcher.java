package app;

import java.awt.EventQueue;

<<<<<<< HEAD
import gui.BoardEditor;
import gui.BoardGUI;

import java.awt.Dimension;

/**
 * This class is the entry point for the project, containing the main method that
 * starts the board.
 * @author cmcgregor
 */
public class Launcher {
    
    /**
     * Runs the program (launches a GUI and underlying model).
=======
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
>>>>>>> total_refactor_philipp_additions
     * @param args (ignored)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
<<<<<<< HEAD
        
            /**
             * The run method starts the board in a separate thread. It creates
             * the GUI.
             */
            @Override
            public void run() {
                BoardGUI gui = new BoardGUI();            //create GUI
                gui.setVisible(true);                   //display GUI
                gui.setMinimumSize(new Dimension(1220, 800));           //sets minimum size for the window
                BoardEditor eng = new BoardEditor(gui);   //create engine
                gui.SetBoardEditor(eng);
                eng.startBoard();                        //starts the game
            }
        });
    }
    
}

=======
            /** The run method starts the board in a separate thread. */
            @Override
            public void run() {
            	@SuppressWarnings("unused")
				Controller controller = new Controller();
            }
        });
    }
}
>>>>>>> total_refactor_philipp_additions
