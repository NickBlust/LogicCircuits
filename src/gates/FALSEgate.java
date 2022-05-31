/**
 * 
 */
package gates;

/**
 * @author Dominik Baumann
 * This gate always returns false!
 * Use this internally for user input.
 */
public class FALSEgate extends Gate {

	public FALSEgate() {
		inputs = new Gate[0];
	}
		
	@Override
	public boolean output() { return false;	}

	@Override
	public void setInput(Gate g, int i) { /*do nothing*/ }

}
