package gui;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import app.Controller;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk, Cameron McGregor
 * @version 2, July 2022
 * The menu bar and all the menus + items for the GUI
 * are implemented here.
 */
public class LogicBoardMenu extends JMenuBar {
	
	/** The controller can access information from the GUI and pass it on to the model. */
	Controller controller;
	
	/** A window for displaying information on how to use this program. */
	JFrame helpWindow;

	/** The text displayed in the {@link gui.LogicBoardMenu#helpWindow Help window}. */
	String helpText;
	
	/** A window for displaying information about the program. */
	JFrame aboutWindow;

	/** The text displayed in the {@link gui.LogicBoardMenu#aboutWindow About window}. */
	String aboutText;
	
	/** The menu item in the "Edit" menu which can be 
	 * used to undo commands (like placing gates etc.).
	 */
	JMenuItem menuItem_Undo;
	
	/** The menu item in the "Edit" menu which can be 
	 * used to redo undone commands (like placing gates etc.).
	 */
	JMenuItem menuItem_Redo;
	
	
	
	/** Construct the menu bar and menus + menu items for the GUI.
	 * @param controller_ Executes the actions and commands bound to the menu items.
	 */
	public LogicBoardMenu(Controller controller_) {
		controller = controller_;
		setHelpAndAboutText();
		initFileMenu(); // FILE MENU => Load / Save Files
		initEditMenu(); // EDIT menu => reset board, evaluate circuits, undo / redo commands
        initHelpAndAboutMenu(); // HELP MENU => Open "Help" and "About" windows
    }

	
	
	/** Fetch the texts that are displayed in the 
	 * "{@link gui.LogicBoardMenu#aboutWindow Help}" window and 
	 * "{@link gui.LogicBoardMenu#aboutWindow About}" window.
	 */
	private void setHelpAndAboutText() {
    	try {
			helpText = Files.readString(Path.of("bin\\assets\\helpText.txt"));
		} catch (IOException e1) {
			helpText = "Failed to load \'Help\' text"; 
			e1.printStackTrace();
		}
    	try {
			aboutText = Files.readString(Path.of("bin\\assets\\aboutText.txt"));
		} catch (IOException e1) {
			aboutText = "Failed to load \'About\' text"; 
			e1.printStackTrace();
		}
	}
	
	
	
	/** Create the menu that allows loading models from files
	 * and saving models to files.
	 * @see app.BoardLoader
	 * @see app.BoardSaver
	 */
	private void initFileMenu() {
		JMenu fileMenu = new JMenu("File");
		this.add(fileMenu);

		JMenuItem menuItem_SaveToFile = new JMenuItem("Save Board to File");
        fileMenu.add(menuItem_SaveToFile);
    	menuItem_SaveToFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.saveToFile();
        	}
        });
        
        JMenuItem menuItem_LoadFromFile = new JMenuItem("Load Board from File");
        fileMenu.add(menuItem_LoadFromFile);
        menuItem_LoadFromFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.loadFromFile();
        	}
        });
	}
	
	
	
	/** Create the menu that allows
	 * <ul>
	 * <li> resetting the board
	 * <li> evaluating circuits on the board
	 * <li> undoing commands
	 * <li> redoing commands
	 * </ul>
	 */
	private void initEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        this.add(editMenu);

        menuItem_Undo = new JMenuItem("Undo");
        editMenu.add(menuItem_Undo);
        menuItem_Undo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.undoCommand();
        	}
        });
        
        menuItem_Redo = new JMenuItem("Redo");
        editMenu.add(menuItem_Redo);
        menuItem_Redo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.redoCommand();
        	}
        });
        
        JMenuItem menuItem_ResetBoard = new JMenuItem("Reset Board");
        editMenu.add(menuItem_ResetBoard);
        menuItem_ResetBoard.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.resetBoard();
        	}
        });
        
        JMenuItem menuItem_Evaluate = new JMenuItem("Evaluate");
        editMenu.add(menuItem_Evaluate);
        menuItem_Evaluate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.evaluateCircuits();
        	}
        });		
	}

	
	
	/** Create the menu that gives access
	 * to the "Help" and "About" window.
	 */
	private void initHelpAndAboutMenu() {
        JMenu helpMenu = new JMenu("About & Help");
        this.add(helpMenu);
        
        JMenuItem menuItem_About = new JMenuItem("About");
        helpMenu.add(menuItem_About);
        menuItem_About.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EventQueue.invokeLater(new Runnable()
                { @Override
                    public void run() { showInformationAboutProgram(); } });
        	}
        });
        
        JMenuItem menuItem_Help = new JMenuItem("Help");
        helpMenu.add(menuItem_Help);
        menuItem_Help.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EventQueue.invokeLater(new Runnable()
                { @Override
                    public void run() { openHelpMenu(); } }); }
        });   
    }
	
	
	
	/** Open a window that displays information on how to use this program. */
	private void openHelpMenu() { // TODO: complete help text
		if(helpWindow != null) // only allow one open help window at a time
			helpWindow.dispatchEvent(new WindowEvent(helpWindow, WindowEvent.WINDOW_CLOSING));
		helpWindow = new InfoWindow("Help", 700, 250, helpText);
	}
	
	/** Open a window that displays information about this program. */
	private void showInformationAboutProgram() {
		if(aboutWindow != null) // only allow one open about window at a time
			aboutWindow.dispatchEvent(new WindowEvent(aboutWindow, WindowEvent.WINDOW_CLOSING));
        aboutWindow = new InfoWindow("About", 700, 220, aboutText);
	}
	
	
	
	/** Only have the "{@link gui.LogicBoardMenu#menuItem_Undo Undo}"
	 *  menu item enabled, if the number
	 * of commands that can be undone is positive.
	 * @param undoCount The current number of undoable commands.
	 */
	public void updateUndoMenu(int undoCount) {
		menuItem_Undo.setEnabled(undoCount > 0);
	}
	
	public void updateRedoMenu(int redoCount) {
		menuItem_Redo.setEnabled(redoCount > 0);
	}
	
	/** This is just here because Eclipse complained about it. */
	private static final long serialVersionUID = 1L;
}