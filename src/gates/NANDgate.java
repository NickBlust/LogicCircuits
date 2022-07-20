package gates;

/**
 * This gate returns true iff not all input values are true.
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 */
public class NANDgate extends InputGate {

	/** By default, this gate uses two input values. */
	public NANDgate() { }

	@Override
	protected boolean computeOutput(boolean a, boolean b) {
		return !(a && b);
	}

	@Override
	public String name() { return "NAND";}
}