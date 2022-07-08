/**
 * 
 */
package gates;

/**
 * This gate returns true iff at least one of its input values was treu.
 * @author Dominik Baumann, Philipp Grzywaczyk
 */
public class ORgate extends InputGate {

	/** By default, this gate uses two input values. */
	public ORgate() { }

	@Override
	protected boolean computeOutput(boolean a, boolean b) {
		return a || b;
	}
}