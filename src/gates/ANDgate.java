/**
 * 
 */
package gates;

/**
 * This gate returns true iff all input values are true.
 * @author Dominik Baumann, Philipp Grzywaczyk
 */
public class ANDgate extends InputGate {

	
	/** By default, this gate uses two input values. */
	public ANDgate() { }
	
	@Override
	public boolean output() {
		try {
			return inputs.get(GateIndex.TOP).output() && inputs.get(GateIndex.BOTTOM).output();
		} catch (NullPointerException e) {
			System.out.println("ERROR: Input gates not specified! " + this);
			return false;
		}
	}
}