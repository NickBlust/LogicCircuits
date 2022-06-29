/**
 * 
 */
package utility;

import gates.Gate;

/**
 * @author domin
 *
 */
public class PositionInfo {
	public Gate gate;
	public Vector2Int v;
	
	public PositionInfo(Gate g, Vector2Int vec) { gate = g; v = vec; }
	
	public String toString() {
		return gate.toString() + " at " + v;
	}
	
	public String toStorageString() {
		return gate.gateName() + " " + v.x + " " + v.y;
	}
}
