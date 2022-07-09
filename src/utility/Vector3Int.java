/**
 * 
 */
package utility;

/**
 * @author domin
 *
 */
public class Vector3Int /*implements Comparable<Vector3Int> */{

	public int x;
	public int y;
	public int z;
	
	public Vector3Int(int x_, int y_, int z_) { x = x_; y = y_; z = z_; }
	
	public Vector3Int(Vector2Int v) { x = v.x; y = v.y; z = 0; }
	
	public double abs() { return java.lang.Math.sqrt(x*x + y*y + z*z); }
	
	public Vector3Int cross(Vector3Int v) {
		int a = y * v.z - z * v.y;
		int b = z * v.x - x * v.z; 
		int c = x * v.y - y * v.x; 
		return new Vector3Int(a, b, c);
	}
	
	public int dotProduct(Vector3Int v) { return x * v.x + y * v.y + z * v.z; }
	
	public Vector3Int subtract(Vector3Int v) {
		return new Vector3Int(x - v.x, y - v.y, z - v.z);
	}
	
	/**
	 * @param a
	 * @param b
	 * @return The distance of this vector to the line between a and b
	 */
	public double distToLine(Vector3Int a, Vector3Int b) {
		Vector3Int line = b.subtract(a);
		return (this.subtract(a).cross(line)).abs() / line.abs();
	}
	
//	/** 
//	 * Used for validation of mouse positions.
//	 * @param v Another vector whose distance to this vector we are interested in.
//	 * @return The squared distance from this vector to the input vector.
//	 */
//	public double squaredDistance(Vector3Int v) {
//		return (x - v.x) * (x - v.x) + (y - v.y) * (y - v.y);  
//	}
	
	
	public boolean equals(Vector3Int v) {
		return v.x == x && v.y == y && v.z == z;
	}
	
//	@Override
//	public int compareTo(Vector3Int v) {
//        int retVal = x - v.x;
//        if (retVal != 0) {
//            return retVal;
//        }
//        retVal = y - v.y;
//        if (retVal != 0) {
//        	return retVal;        	
//        }
//        return z - v.z;
//	}

	@Override
	public String toString() { return "(" + x + ", " + y + ", " + z + ")"; }

	/** distance to line has to be small enough
	 * and the target has to be on a line parallel to the line SEGMENT (not the infinite line!)
	 * @param a
	 * @param b
	 * @param i
	 * @return
	 */
	public boolean nearToLineSegment(Vector3Int a, Vector3Int b, int maxDist) {
		Vector3Int line = b.subtract(a);
		Vector3Int reverseLine = a.subtract(b);
//		System.out.println(this + " <---> " + a + "    " + b);
//		System.out.println(this.distToLine(a, b) + "  " + line.dotProduct(this.subtract(a)) 
//		+ "  " + reverseLine.dotProduct(this.subtract(b)));
		return this.distToLine(a, b) < maxDist
				&& line.dotProduct(this.subtract(a)) >= 0
				&& reverseLine.dotProduct(this.subtract(b)) >= 0;
	}
}