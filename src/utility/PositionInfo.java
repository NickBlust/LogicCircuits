/**
 * 
 */
package utility;

import gates.Gate;
import gates.TileConverter;

/**
 * Objects of this class are used for saving the model to a file, by representing
 * 		a type of gate and its position on the board / in the model
 * @author Dominik Baumann
 */
public class PositionInfo {
	
	/** The type of gate stored in the object. */
	public Gate gate;
	
	/** The position of the gate on the board / in the model */
	public Vector2Int v;
	
	/** Constructor.
	 * @param g type of gate
	 * @param vec position on board / in model
	 */
	public PositionInfo(Gate g, Vector2Int vec) { gate = g; v = vec; }
	
	public String toString() {
		return gate.toString() + " at " + v;
	}
	
	/** @return A String representation of this object, suitable for storage. */
	public String toStorageString() {
		return TileConverter.gateName(gate) + " " + v.x + " " + v.y;
	}
}