/**
 * 
 */
package utility;

import gui.BoardEditor.TileType;

/**
 * @author domin
 *
 */
public class EvaluationInfo {
	public TileType type;
	public int col, row;
	public boolean truthValue;
	
	public EvaluationInfo(TileType t_, int c, int r, boolean b) {
		type = t_; col = c; row = r; truthValue = b;
	}
}
