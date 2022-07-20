package utility;

import gates.GateIndex;

/** Utility class for storing a boolean and a GateIndex.
 * <p> Used for checking if a position is over an
 * input (distinguishing between TOP and BOTTOM) or output.
 * @author Dominik Baumann
 * @version 2, July 2022
 * @see gates.GateIndex
 */
public class BoolGateIndexTuple {
	
	/** The boolean value in this tuple. */
	boolean key;
	
	/** The GateIndex in this tuple. */
	GateIndex value;
	
	/** Constructor for a tuple with given boolean and GateIndex.
	 * @param k The boolean value in this tuple
	 * @param v The GateIndex in this tuple.
	 */
	public BoolGateIndexTuple(boolean k, GateIndex v) { key = k; value = v; }
	
	/**
	 * @return The boolean value stored in this tuple.
	 */
	public boolean key() { return key; }
	
	/**
	 * @return The GateIndex stored in this tuple.
	 */
	public GateIndex value() { return value; }
	
	@Override
	public String toString() {
		return "(" + key + ", " + value.toString() + ")";
	}
}