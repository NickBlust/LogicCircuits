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
	
	public Status status;
	
	/** Construct a tuple with two given points.
	 * @param a_ First point in the tuple.
	 * @param b_ Second point in the tuple.
	 */
	public PointTupleWithStatus(Vector2Int a_, Vector2Int b_, Status s_) { a = a_; b = b_; status = s_; }
}