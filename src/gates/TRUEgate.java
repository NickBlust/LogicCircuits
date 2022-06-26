/**
 * 
 */
package gates;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 * This gate always returns true!
 * Use this internally for user input.
 */
public class TRUEgate extends Gate {
	
//	public TRUEgate() {
//	inputs = new Gate[0];
//}
	
	public Gate getInput(int i) { return null; /* Do nothing. */
//	try { return this.inputs[i];			
//	} catch (NullPointerException e) {
//		return null;
//	}
	}
		
	@Override
	public boolean output() { return true;	}

	@Override
	public void setInput(Gate g, int i) { /*do nothing*/ }
}