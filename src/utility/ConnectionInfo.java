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
	
	/** The column and row indices of the gate which serves as input. */
	public int input_col, input_row;
	
	/** The column and row indices of the gate which is to receive input. */
	public int target_col, target_row;
	
	/** 
	 * Which of the inputs is set?
	 * Gate has one input => ID is always 1
	 * Gate has two inputs => ID is 1 for top, and 2 for bottom input
	 */
	public int id;
	
	/** Constructor
	 * @param i_col The column index of the gate which serves as input.
	 * @param i_row The row index of the gate which serves as input.
	 * @param t_col The column index of the gate which is to receive input.
	 * @param t_row The row index of the gate which is to receive input.
	 * @param id_ Which of the inputs on the target is connected to?
	 */
	public ConnectionInfo(int i_col, int i_row, int t_col, int t_row, int id_) {
		input_col = i_col; input_row = i_row;
		target_col = t_col; target_row = t_row;
		id = id_;
	}
	
	/** Copy Constructor
	 * @param c The ConnectionInfo to copy.
	 */
	public ConnectionInfo(ConnectionInfo c) {
		input_col = c.input_col;
		input_row = c.input_row;
		target_col = c.target_col;
		target_row = c.target_row;
		id = c.id;
	}

	/** Compare two ConnectionInfos for equality.
	 * @param c The ConnectionInfo this object is to be compared to.
	 * @return True if the objects contain the same information, false otherwise.
	 */
	public boolean equals(ConnectionInfo c) {
		return input_col == c.input_col 
				&& input_row == c.input_row 
				&& target_col == c.target_col
				&& target_row == c.target_row
				&& id == c.id;
	}
	
	/** Check if gate (to be precise, its position) is part of a connection.
	 * @param pos The position under investigation.
	 * @return True, if the connection has a start or end equivalent to <strong>pos</strong>, false otherwise.
	 */
	public boolean isPartOfConnection(Vector2Int pos) {
		return ((input_col == pos.x) && (input_row == pos.y)) || ((target_col == pos.x) && (target_row == pos.y)); 
	}
	
	public String toString() {
		return "{(" + input_col + ", " + input_row + ") --> (" + target_col + ", " + target_row + ") : " + id + "}";
	}
	
	
	/** @return A String representation of this ConnectionInfo in a format suitable for storing. */
	public String toStorageString() {
		return input_col + " " + input_row + " " + target_col + " " + target_row + " " + id;
	}
}
