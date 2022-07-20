package utility;

import gates.Gate;
import gates.GateIndex;

/** Utility class for storing a Gate with a GateIndex.
 * @author Dominik Baumann
 * @version 2, July 2022
 * @see gates.Gate
 * @see gates.GateIndex
 */
public class GateWithIndexTuple {

	/** The gate in this tuple. */
	public Gate gate;
	
	
	/** The GateIndex in this tuple. */
	public GateIndex ind;
	
	/** Construct a tuple from a given gate and GateIndex.
	 * @param g_ The gate in this tuple.
	 * @param i_ The GateIndex in this tuple
	 */
	public GateWithIndexTuple(Gate g_, GateIndex i_) {
		gate = g_; ind = i_;
	}
}