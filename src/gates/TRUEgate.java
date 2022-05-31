/**
 * 
 */
package gates;

/**
 * @author Dominik Baumann
 * This gate always returns true!
 * Use this internally for user input.
 */
public class TRUEgate extends Gate {

	public TRUEgate() {
		inputs = new Gate[0];
	}
		
	@Override
	public boolean output() { return true;	}

	@Override
	public void setInput(Gate g, int i) { /*do nothing*/ }

}
