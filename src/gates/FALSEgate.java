package gates;

/**
 * This gate always returns false!
 * <p> Use this internally for user input.
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 */
public class FALSEgate extends ConstGate {

	/** This gate uses no input values. */
	public FALSEgate() { resetStatus(); }

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
	
	@Override
	public void resetStatus() { status = Status.FALSE; }
}