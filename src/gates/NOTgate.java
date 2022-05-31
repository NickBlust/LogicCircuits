/**
 * 
 */
package gates;

/**
 * @author Dominik Baumann
 *
 */
public class NOTgate extends Gate {

	public NOTgate() {
		inputs = new Gate[1];
	}
	
	@Override
	public boolean output() {
		try {
			return !inputs[0].output();
		} catch (NullPointerException e) {
			System.out.println("ERROR: Input gates not specified!");
			return false;
		}
	}

	@Override
	public void setInput(Gate g, int i) {
		if(i != 0) { return; }
		inputs[i] = g;
	}

}
