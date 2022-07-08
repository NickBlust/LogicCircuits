/**
 * 
 */
package gates;

/**
 * This gate returns true iff its input is false.
 * @author Dominik Baumann, Philipp Grzywaczyk
 */
public class NOTgate extends InputGate {

	/** By default, this gate uses one input value. */
	public NOTgate() { }

	@Override
	protected boolean computeOutput(boolean a, boolean b) {
		return !a;
	}
}