/**
 * 
 */
package gates;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 * This gate always returns false!
 * Use this internally for user input.
 */
public class FALSEgate extends Gate {

	public FALSEgate() {
		inputs = new Gate[0];
	}
		
	public Gate getInput(int i) {
		try { return this.inputs[i];			
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	@Override
	public boolean output() { return false;	}

	@Override
	public void setInput(Gate g, int i) { /*do nothing*/ }

}
