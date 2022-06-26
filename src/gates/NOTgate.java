/**
 * 
 */
package gates;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 *
 */
public class NOTgate extends InputGate {

	public NOTgate() {
		inputs = new Gate[1];
	}
	
	public Gate getInput(int i) {
		try { return this.inputs[i];			
		} catch (NullPointerException e) {
			return null;
		}
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
