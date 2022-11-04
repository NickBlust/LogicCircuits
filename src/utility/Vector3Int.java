package utility;

/** Utility class for 3D-vectors with integer components.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class Vector3Int {

	/** First component of a 3D-vector. */
	public int x;
	/** Second component of a 3D-vector. */
	public int y;
	/** Third component of a 3D-vector. */
	public int z;
	
	/** Construct a 3D-vector with the given integer components.
	 * @param x_ First component of the new 3D-vector.
	 * @param y_ Second component of the new 3D-vector.
	 * @param z_ Third component of the new 3D-vector.
	 */
	public Vector3Int(int x_, int y_, int z_) { x = x_; y = y_; z = z_; }
	
	
	
	/** Construct a 3D-vector integer components from a 2D vector,
	 * s.t. the third component is 0.
	 * @param v A 2D vector.
	 */
	public Vector3Int(Vector2Int v) { x = v.x; y = v.y; z = 0; }
	
	
	
	/** Compute the length of this 3D-vector.
	 * @return The length of this vector.
	 */
	public double abs() { return java.lang.Math.sqrt(x*x + y*y + z*z); }
	
	
	
	/** Compute the cross product of this vector and another vector.
	 * @param v Another 3D-vector.
	 * @return The cross product of this vector and another vector
	 */
	public Vector3Int cross(Vector3Int v) {
		int a = y * v.z - z * v.y;
		int b = z * v.x - x * v.z; 
		int c = x * v.y - y * v.x; 
		return new Vector3Int(a, b, c);
	}
	
	
	
	/** Compute the scalar / dot product of this vector
	 * with another vector.
	 * @param v Another 3D-vector.
	 * @return The scalar / dot product of this vector with the given vector.
	 */
	public int dotProduct(Vector3Int v) { return x * v.x + y * v.y + z * v.z; }
	
	
	
	/** Compute the shortest distance between the point
	 * represented by this vector and the line represented
	 * by the argument vectors.
	 * @param a One endpoint of the line.
	 * @param b The other endpoint of the line.
	 * @return The distance of the point represented by 
	 * this vector to the line between a and b.
	 */
	public double distToLine(Vector3Int a, Vector3Int b) {
		Vector3Int line = b.subtract(a);
		return (this.subtract(a).cross(line)).abs() / line.abs();
	}
	
	
	
	/** Subtract a vector (componentwise) from this vector.
	 * @param v The vector to subtract.
	 * @return This vector, where from each component the corresponding
	 * component of the argument vector has been subtracted.
	 */
	public Vector3Int subtract(Vector3Int v) {
		return new Vector3Int(x - v.x, y - v.y, z - v.z);
	}
	
	
	
	/** Compare two vectors for equality.
	 * @param v Another 3D vector.
	 * @return "true" iff all components of this vector 
	 * and the argument vector are equal.
	 */
	public boolean equals(Vector3Int v) {
		return v.x == x && v.y == y && v.z == z;
	}

	
	
	@Override
	public String toString() { return "(" + x + ", " + y + ", " + z + ")"; }

	/** Check if the point specified (by the first two components of) this 3D-vector
	 * is not farther away than a set maximum distance from the
	 * line segment spanned between two points.
	 * <p>
	 * The point must be reachable by a line orthogonal to the line segment,
	 * which starts <b>within</b> the line segment - i.e., we are <b>not</b> 
	 * considering the infinite line going through the two points.
	 * <p>
	 * Another way to look at this restriction, is that the three
	 * points form a triangle without obtuse angle.
	 * @param a One endpoint of the line segment.
	 * @param b One endpoint of the line segment.
	 * @param maxDist Maximal distance this point may have to the line segment.
	 * @return True iff the point was close to the <b>finite</b> line segment.
	 */
	public boolean nearToLineSegment(Vector3Int a, Vector3Int b, int maxDist) {
		Vector3Int line = b.subtract(a);
		Vector3Int reverseLine = a.subtract(b);
		return this.distToLine(a, b) < maxDist
				&& line.dotProduct(this.subtract(a)) >= 0
				&& reverseLine.dotProduct(this.subtract(b)) >= 0;
	}
}