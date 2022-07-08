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
	protected boolean computeOutput(boolean a, boolean b) {
		return !(a || b);
	}
}