package gates;

/**
 * This gate returns true iff all input values are false.
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 */
public class NORgate extends InputGate {

	/** By default, this gate uses two input values. */
	public NORgate() { }

	@Override
	protected boolean computeOutput(boolean a, boolean b) {
		return !(a || b);
	}

	@Override
	public String name() { return "NOR";}
}