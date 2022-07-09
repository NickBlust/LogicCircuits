/**
 * 
 */
package gates;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 * This gate always returns false!
 * Use this internally for user input.
 */
public class FALSEgate extends ConstGate {

	/** This gate uses no input values. */
	public FALSEgate() {}

	/** Always returns false.
	 * @return false
	 */
	@Override
	public boolean output() { return false;	}

	@Override
	public Gate getInput(GateIndex i) { return null; }

	@Override
	public void setInput(Gate g, GateIndex i) { /* do nothing */ }

	@Override
	public String name() { return "FALSE";}
}