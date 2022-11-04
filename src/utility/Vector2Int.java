<<<<<<< HEAD
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
=======
package utility;

/** Utility class for 2D-vectors with integer components.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class Vector2Int implements Comparable<Vector2Int> {

	/** First component of a 2D-vector. */
	public int x;
	
	/** Second component of a 2D-vector. */
	public int y;
	
	/** Construct a 2D-vector with the given integer components.
	 * @param x_ First component of the new 3D-vector.
	 * @param y_ Second component of the new 3D-vector.
	 */
	public Vector2Int(int x_, int y_) { x = x_; y = y_; }
	
	
	
	/** Compute the square of the distance between the point
	 * represented by this vector and another point in the plane.
	 * <p> Used for validation of mouse positions in 
	 * {@link utility.PositionCalculator PositionCalculator}.
	 * @param v Another vector whose distance to this vector we are interested in.
>>>>>>> total_refactor_philipp_additions
	 * @return The squared distance from this vector to the input vector.
	 */
	public double squaredDistance(Vector2Int v) {
		return (x - v.x) * (x - v.x) + (y - v.y) * (y - v.y);  
	}
	
<<<<<<< HEAD
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
=======
	
	
	/** Compare two vectors for equality.
	 * @param v Another 2D vector.
	 * @return "true" iff all components of this vector 
	 * and the argument vector are equal.
	 */
	public boolean equals(Vector2Int v) {
		return v.x == x && v.y == y;
	}
	
	
	
	/** Compare this vector's components to those of another
	 * vector, and set this vector's component to the 
	 * maximum of the components.
	 * @param a Another 2D vector.
	 */
	public void componentwiseMaximum(Vector2Int a) {
		x = x > a.x ? x : a.x;
		y = y > a.y ? y : a.y;
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
>>>>>>> total_refactor_philipp_additions
