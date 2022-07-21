package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/** A generic window which only displays a text.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class InfoWindow extends JFrame {

	/** Constructor for a window with a given name, width, height
	 * containing a given text.
	 * @param windowName The name of the window / frame.
	 * @param width The width of the window / frame.
	 * @param height The height of the window / frame.
	 * @param infoText The text to be displayed in the window / frame.
	 */
	public InfoWindow(String windowName, int width, int height, String infoText) {
		setTitle(windowName);
		setPreferredSize(new Dimension(width, height));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextArea textArea = new JTextArea(infoText);
		textArea.setFont(new Font(Font.MONOSPACED,  Font.BOLD, 13));
		System.out.println(textArea.getFont());
		textArea.setBackground(UIManager.getColor( "Panel.background" ));
		textArea.setEditable(false);
		getContentPane().add(BorderLayout.CENTER, textArea);
		pack();
		setLocationByPlatform(true);
		setVisible(true);
	}
	
	/** This is just here because Eclipse complained about it. */
	private static final long serialVersionUID = 1L;
}