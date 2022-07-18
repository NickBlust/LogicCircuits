/**
 * 
 */
package utility;

import gates.Gate;
import gates.GateIndex;

/**
 * @author domin
 *
 */
public class GateWithIndexTuple {

	public Gate gate;
	public GateIndex ind;
	
	public GateWithIndexTuple(Gate g_, GateIndex i_) {
		gate = g_; ind = i_;
	}
}