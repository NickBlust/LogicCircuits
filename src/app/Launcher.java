package app;

import java.awt.EventQueue;

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
     * @param args (ignored)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
        
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

