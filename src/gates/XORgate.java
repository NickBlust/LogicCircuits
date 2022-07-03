/**
 * 
 */
package gates;

/**
 * This gate returns true iff one input value is true and the other is false.
 * @author Dominik Baumann, Philipp Grzywaczyk
 */
public class XORgate extends InputGate {

	/** By default, this gate uses two input values. */
	public XORgate() { }
	
	@Override
	public boolean output() {
		try {
			return inputs.get(GateIndex.TOP).output() ^ inputs.get(GateIndex.BOTTOM).output();
		} catch (NullPointerException e) {
			System.out.println("ERROR: Input gates not specified! " + this);
			if (inputs.get(GateIndex.TOP) == null 
					&& inputs.get(GateIndex.BOTTOM).output() == true) { return true; }
			
			else if (inputs.get(GateIndex.TOP).output() == true 
					&& inputs.get(GateIndex.BOTTOM) == null) { return true; }
			return false;
		}
	}
}