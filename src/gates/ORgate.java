package gates;

/**
 * This gate returns true iff at least one of its input values was treu.
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 */
public class ORgate extends InputGate {

	/** By default, this gate uses two input values. */
	public ORgate() { }

	@Override
	protected boolean computeOutput(boolean a, boolean b) {
		return a || b;
	}

	@Override
	public String name() { return "OR";}
}