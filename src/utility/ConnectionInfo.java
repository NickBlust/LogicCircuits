/**
 * 
 */
package utility;

/**
 * This helps the GUI store information about connections,
 * by storing the positions of the input and target gate on the board
 * with the information which input is set on the target gate.
 * @author Dominik Baumann
 */
public class ConnectionInfo {
	public int input_col, input_row;
	public int target_col, target_row;
	
	/** 
	 * Which of the inputs is set?
	 * Gate has one input => ID is always 1
	 * Gate has two inputs => ID is 1 for top, and 2 for bottom input
	 */
	public int id;
	
	public ConnectionInfo(int i_col, int i_row, int t_col, int t_row, int id_) {
		input_col = i_col; input_row = i_row;
		target_col = t_col; target_row = t_row;
		id = id_;
	}
	
	public boolean equals(ConnectionInfo c) {
		return input_col == c.input_col 
				&& input_row == c.input_row 
				&& target_col == c.target_col
				&& target_row == c.target_row
				&& id == c.id;
	}
	
	public boolean isPartOfConnection(Vector2Int pos) {
		return ((input_col == pos.x) && (input_row == pos.y)) || ((target_col == pos.x) && (target_row == pos.y)); 
	}
	
	public String toString() {
		return input_col + " " + input_row + " " + target_col + " " + target_row + " " + id;
	}
}
