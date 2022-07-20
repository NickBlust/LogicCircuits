package utility;

/** Utility class for storing two 
 * 2D-vectors / points in one object.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class PointTuple {

	/** First point in the tuple. */
	public Vector2Int a;
	
	/** Second point in the tuple. */
	public Vector2Int b;

	
	/** Construct a tuple with two given points.
	 * @param a_ First point in the tuple.
	 * @param b_ Second point in the tuple.
	 */
	public PointTuple(Vector2Int a_, Vector2Int b_) { a = a_; b = b_; }
}