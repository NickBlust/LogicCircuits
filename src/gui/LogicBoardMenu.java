/**
 * 
 */
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
 * @author domin
 *
 */
public class LogicBoardMenu extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Controller controller;
	JFrame helpWindow;
	JFrame aboutWindow;
	String helpText;
	String aboutText;
	
	// TODO refactor the stuff in the constructor
	public LogicBoardMenu(Controller controller_) {
		controller = controller_;
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
		
		// FILE MENU => Load / Save Files
		JMenu fileMenu = new JMenu("File");
		this.add(fileMenu);

		JMenuItem menuItem_SaveToFile = new JMenuItem("Save Board to File");
        fileMenu.add(menuItem_SaveToFile);
    	menuItem_SaveToFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.clickedSave();
        	}
        });
    	
        
        JMenuItem menuItem_LoadFromFile = new JMenuItem("Load Board from File");
        fileMenu.add(menuItem_LoadFromFile);
        menuItem_LoadFromFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.clickedLoad();
        	}
        });
        
        
        
        
        
        // EDIT MENU => 1) reset board
        // 				2) evaluate all circuits on board
        // 				3) undo / redo placement of gates or connections // TODO
        JMenu editMenu = new JMenu("Edit");
        this.add(editMenu);

        JMenuItem menuItem_Undo = new JMenuItem("Undo");
        editMenu.add(menuItem_Undo);
        menuItem_Undo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.undoCommand();
        	}
        });
        
        // TODO
//        JMenuItem menuItem_Redo = new JMenuItem("Redo");
//        editMenu.add(menuItem_Redo);
//        menuItem_Redo.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        		controller.redoCommand();
//        	}
//        });
        
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
        
        
        
        
        
        // HELP MENU => Open "Help" and "About" windows
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
	
	// TODO
	private void openHelpMenu() { // TODO: complete help text
		if(helpWindow != null) // only allow one open help window at a time
			helpWindow.dispatchEvent(new WindowEvent(helpWindow, WindowEvent.WINDOW_CLOSING));
		helpWindow = new InfoWindow("Help", 700, 250, helpText);
	}
	
	// TODO
	private void showInformationAboutProgram() {
		if(aboutWindow != null) // only allow one open about window at a time
			aboutWindow.dispatchEvent(new WindowEvent(aboutWindow, WindowEvent.WINDOW_CLOSING));
        aboutWindow = new InfoWindow("About", 700, 220, aboutText);
	}
}