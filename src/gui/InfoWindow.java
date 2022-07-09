/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * @author domin
 *
 */
public class InfoWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
}