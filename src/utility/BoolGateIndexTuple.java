/**
 * 
 */
package utility;

import gates.Gate.GateIndex;

/**
 * @author domin
 *
 */
public class BoolGateIndexTuple {
	boolean key;
	GateIndex value;
	
	public BoolGateIndexTuple(boolean k, GateIndex v) { key = k; value = v; }
	
	public boolean key() { return key; }
	public GateIndex value() { return value; }
	
	@Override
	public String toString() {
		return "(" + key + ", " + value.toString() + ")";
	}
}