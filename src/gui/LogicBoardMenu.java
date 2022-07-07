/**
 * 
 */
package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	public LogicBoardMenu(Controller controller_) {
		controller = controller_;
        
		
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
                    public void run() { /*showInformationAboutProgram();*/ } });
        	}
        });
        
        JMenuItem menuItem_Help = new JMenuItem("Help");
        helpMenu.add(menuItem_Help);
        menuItem_Help.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EventQueue.invokeLater(new Runnable()
                { @Override
                    public void run() { /*openHelpMenu();*/ } }); }
        });    
    }
	
	// TODO
	// private void openHelpMenu() {}
	
	// TODO
	// private void showInformationAboutProgram() {}
	
}