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
	protected boolean computeOutput(boolean a, boolean b) {
		return a && b;
	}

	@Override
	public String name() { return "AND";}
}