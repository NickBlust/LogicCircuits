/**
 * 
 */
package utility;

/**
 * Class for storing two integer values.
 * @author Dominik Baumann
 */
public class Vector2Int {
	
	public int x, y;

	public Vector2Int() { x = 0; y = 0; }
	
	public Vector2Int(int x_, int y_) { x = x_; y = y_; }
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
