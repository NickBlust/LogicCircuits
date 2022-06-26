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
		
	/** Always returns null, as this gate has no input. */
	@Override
	public Gate getInput(int i) { return null; /* do nothing */
//		try { return this.inputs[i];			
//		} catch (NullPointerException e) {
//			return null;
//		}
	}
	
	/** Always returns false.
	 * @return false
	 */
	@Override
	public boolean output() { return false;	}

	/** This gate has no inputs => do nothing.
	 * @param g (ignored)
	 * @param i (ignored)
	 */
	@Override
	public void setInput(Gate g, int i) { /*do nothing*/ }
}