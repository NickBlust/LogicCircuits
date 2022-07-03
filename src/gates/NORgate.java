/**
 * 
 */
package gates;

/**
 * This gate returns true iff all input values are false.
 * @author Dominik Baumann, Philipp Grzywaczyk
 */
public class NORgate extends InputGate {

	/** By default, this gate uses two input values. */
	public NORgate() { }
	
	@Override
	public boolean output() {
		try {
			return !(inputs.get(GateIndex.TOP).output() || inputs.get(GateIndex.BOTTOM).output());
		} catch (NullPointerException e) {
			System.out.println("ERROR: Input gates not specified! " + this);
			if(inputs.get(GateIndex.TOP) == null 
					&& inputs.get(GateIndex.BOTTOM) == null) { return true; }
			
			else if(inputs.get(GateIndex.TOP) == null 
					&& inputs.get(GateIndex.BOTTOM).output() == false) { return true; }
			
			else if(inputs.get(GateIndex.TOP).output() == false 
					&& inputs.get(GateIndex.BOTTOM) == null) { return true; }
			return false;
		}
	}
}