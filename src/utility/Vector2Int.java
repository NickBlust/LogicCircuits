/**
 * 
 */
package utility;

/**
 * Class for storing two integer values - in the context
 * of this project: positions on the board / in the model.
 * @author Dominik Baumann
 */
public class Vector2Int {
	
	/** Row and column indices (no guarantee that it is not column and row ...) */
	public int x, y;

	/** Default constructor: (0,0) */
	public Vector2Int() { x = 0; y = 0; }
	
	/** Constructor
	 * @param x_ row position (or other way round?)
	 * @param y_ column position
	 */
	public Vector2Int(int x_, int y_) { x = x_; y = y_; }
	
	/** 
	 * Used for validation of mouse positions in {@link gui.BoardGUI BoardGUI}.
	 * @param v Another vector whose distance to this vectore we are interested in.
	 * @return The squared distance from this vector to the input vector.
	 */
	public double squaredDistance(Vector2Int v) {
		return (x - v.x) * (x - v.x) + (y - v.y) * (y - v.y);  
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	

	/** 
	 * Compare to vectors for equality of their contents.
	 * @param v The vector to compare this to.
	 * @return True if both vectors have the same values, false otherwise.
	 */
	public boolean equals(Vector2Int v) { return x == v.x && y == v.y; }
}
