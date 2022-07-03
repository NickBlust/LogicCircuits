/**
 * 
 */
package gates;

/**
 * This gate returns true iff not all input values are true.
 * @author Dominik Baumann, Philipp Grzywaczyk
 */
public class NANDgate extends InputGate {

	/** By default, this gate uses two input values. */
	public NANDgate() { }
	
	@Override
	public boolean output() {
		try {
			return !(inputs.get(GateIndex.TOP).output() && inputs.get(GateIndex.BOTTOM).output());
		} catch (NullPointerException e) {
			System.out.println("ERROR: Input gates not specified! " + this);
			if(inputs.get(GateIndex.TOP) == null 
					&& inputs.get(GateIndex.BOTTOM) == null) { return true; }
			
			else if (inputs.get(GateIndex.TOP) == null 
					&& inputs.get(GateIndex.BOTTOM).output() == true) { return true; }
			
			else if (inputs.get(GateIndex.TOP).output() == true 
					&& inputs.get(GateIndex.BOTTOM) == null) { return true; }
			return false;
		}
	}
}