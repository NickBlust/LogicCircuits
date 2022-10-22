package utility;

import gates.Status;

/** Utility class for storing two 
 * 2D-vectors / points in one object.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class PointTupleWithStatus {

	/** First point in the tuple. */
	public Vector2Int a;
	
	/** Second point in the tuple. */
	public Vector2Int b;
	
	/** The truth value of the connection between the two points (which represent gates). */
	public Status status;
	
	/** Construct a tuple with two given points.
	 * @param a_ First point in the tuple.
	 * @param b_ Second point in the tuple.
	 * @param s_ The truth value of this connection (which determines the color it is drawn in).
	 */
	public PointTupleWithStatus(Vector2Int a_, Vector2Int b_, Status s_) { a = a_; b = b_; status = s_; }
}