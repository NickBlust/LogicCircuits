/**
 * 
 */
package utility;

import gui.BoardEditor.TileType;

/**
 * Objects of this class store information about 
 * 		the type of gate
 * 		its position in the model / on the board
 * 		and its truth value after evaluation.
 * @author Dominik Baumann
 */
public class EvaluationInfo {
	
	/** The type of gate. */
	public TileType type;
	
	/** Column and row position of this gate in the model / on the board */
	public int col, row;
	
	/** The truth value of this gate after evaluation */
	public boolean truthValue;
	
	/** Default constructor.
	 * @param t_ type of gate / tile.
	 * @param c column index
	 * @param r row index
	 * @param b truth value of gate
	 */
	public EvaluationInfo(TileType t_, int c, int r, boolean b) {
		type = t_; col = c; row = r; truthValue = b;
	}
}
