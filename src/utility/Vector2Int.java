/**
 * 
 */
package utility;

/**
 * @author domin
 *
 */
public class Vector2Int implements Comparable<Vector2Int> {

	public int x;
	public int y;
	
	public Vector2Int(int x_, int y_) { x = x_; y = y_; }
	
	public boolean equals(Vector2Int v) {
		return v.x == x && v.y == y;
	}
	
	@Override
	public int compareTo(Vector2Int v) {
        int retVal = x - v.x;
        if (retVal != 0) {
            return retVal;
        }
        return y - v.y;
	}

	@Override
	public String toString() { return "(" + x + ", " + y + ")"; }
}