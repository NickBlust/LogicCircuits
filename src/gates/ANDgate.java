/**
 * 
 */
package gates;

/**
 * @author Dominik Baumann
 *
 */
public class ANDgate extends Gate {

	public ANDgate() {
		inputs = new Gate[2];
	}
	
	@Override
	public boolean output() {
		try {
			return inputs[0].output() && inputs[1].output();
		} catch (NullPointerException e) {
			System.out.println("ERROR: Input gates not specified!");
			return false;
		}
	}

	@Override
	public void setInput(Gate g, int i) {
		if(i != 0 && i != 1) { return; }
		inputs[i] = g;
	}

}
