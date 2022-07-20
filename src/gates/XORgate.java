package gates;

/**
 * This gate returns true iff one input value is true and the other is false.
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 */
public class XORgate extends InputGate {

	/** By default, this gate uses two input values. */
	public XORgate() { }

	@Override
	protected boolean computeOutput(boolean a, boolean b) {
		return a ^ b;
	}

	@Override
	public String name() { return "XOR";}
}