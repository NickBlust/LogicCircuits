package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

/** A generic scrollable window which only displays a text.
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
		textArea.setMargin(new Insets(5, 10, 5, 10));
		textArea.setBackground(UIManager.getColor( "Panel.background" ));
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textArea, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
		getContentPane().add(BorderLayout.CENTER, scrollPane);
		pack();
		setLocationByPlatform(true);
		setVisible(true);
		
		// close this window when pressing "ESCAPE"
		JRootPane rootPane = getRootPane();
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
			.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeWindow");
		rootPane.getActionMap().put("closeWindow", new AbstractAction(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
                dispose(); // close this window
            }
        });
	}
	
	/** This is just here because Eclipse complained about it. */
	private static final long serialVersionUID = 1L;
}