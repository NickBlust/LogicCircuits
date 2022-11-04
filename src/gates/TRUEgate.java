/**
 * 
 */
package gates;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 * This gate always returns true!
 * Use this internally for user input.
 */
public class TRUEgate extends ConstGate {
	
	/** This gate uses no input values. */
	public TRUEgate() {}
	
	/** Always returns null, as this gate has no input. */
	@Override
	public Gate getInput(int i) { return null; /* Do nothing. */
//	try { return this.inputs[i];			
//	} catch (NullPointerException e) {
//		return null;
//	}
	}
		
	/** Always returns true.
	 * @return true
	 */
	@Override
	public boolean output() { return true;	}

	/** This gate has no inputs => do nothing.
	 * @param g (ignored)
	 * @param i (ignored)
	 */
	@Override
	public void setInput(Gate g, int i) { /*do nothing*/ }
}