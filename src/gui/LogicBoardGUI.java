/**
 * 
 */
package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author domin
 *
 */
public class LogicBoardGUI extends JFrame {
	
	/**
	 * The two final int attributes below set the size of some graphical elements,
	 * specifically the display height and width of tiles on the board. Tile sizes 
	 * should match the size of the image files used.
	 */
	public static final int TILE_WIDTH = 64;
	public static final int TILE_HEIGHT = 64;
	
	TiledCanvas canvas;

	JMenuBar menuBar;
	
	public LogicBoardGUI() {
		setSize(816, 615);
		setTitle("Logic Circuits Simulator");
		setLocationRelativeTo(null); //sets position of frame on screen
		
		getContentPane().add(canvas = new TiledCanvas());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		initCanvas();
		initMenuBar();
	}

	/**
	 * 
	 */
	private void initMenuBar() {
    	menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        JMenuItem menuItem_SaveToFile = new JMenuItem("Save Board to File");
        fileMenu.add(menuItem_SaveToFile);
    	menuItem_SaveToFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
//        		clickedSave();
        	}
        });
    	
        
        JMenuItem menuItem_LoadFromFile = new JMenuItem("Load Board from File");
        fileMenu.add(menuItem_LoadFromFile);
        menuItem_LoadFromFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
//        		clickedLoad();
        	}
        });
        
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        
        JMenuItem menuItem_ResetBoard = new JMenuItem("Reset Board");
        editMenu.add(menuItem_ResetBoard);
        menuItem_ResetBoard.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
//        		resetBoard();
        	}
        });
        
        
        JMenuItem menuItem_Evaluate = new JMenuItem("Evaluate");
        editMenu.add(menuItem_Evaluate);
        menuItem_Evaluate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
//        		evaluateCircuits();
        	}
        });
        
        
        JMenu helpMenu = new JMenu("About & Help");
        menuBar.add(helpMenu);
        
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

	/**
	 * 
	 */
	private void initCanvas() {
		// TODO Auto-generated method stub
		
	}

}