/**
 * 
 */
package gates;

/**
 * This gate returns true iff its input is false.
 * @author Dominik Baumann, Philipp Grzywaczyk
 */
public class NOTgate extends InputGate {

	/** By default, this gate uses one input value. */
	public NOTgate() {
		inputs = new Gate[1];
	}
	
	@Override
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
			return true;
		}
	}

	@Override
	public void setInput(Gate g, int i) {
		if(i != 0) { return; }
		inputs[i] = g;
	}
}