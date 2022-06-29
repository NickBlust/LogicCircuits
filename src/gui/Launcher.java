package gui;

import java.awt.EventQueue;

import logicCircuits.LogicCircuit;

/**
 * This class is the entry point for the project, containing the main method that
 * starts the board.
 * @author cmcgregor
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
                BoardGUI gui = new BoardGUI();            //create GUI
                gui.setVisible(true);                   //display GUI
                BoardEditor eng = new BoardEditor(gui);   //create engine
                gui.SetBoardEditor(eng);
                eng.startBoard();                        //starts the game
            }
        });
    }
    
}

