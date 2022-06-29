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
	
	public double squaredDistance(Vector2Int v) {
		return (x - v.x) * (x - v.x) + (y - v.y) * (y - v.y);  
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	

	public boolean equals(Vector2Int v) { return x == v.x && y == v.y; }
}
