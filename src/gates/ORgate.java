/**
 * 
 */
package gates;

/**
 * This gate returns true iff at least one of its input values was treu.
 * @author Dominik Baumann, Philipp Grzywaczyk
 */
public class ORgate extends InputGate {

	/** By default, this gate uses two input values. */
	public ORgate() {
		inputs = new Gate[2];
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
			return inputs[0].output() || inputs[1].output();
		} catch (NullPointerException e) {
			System.out.println("ERROR: Input gates not specified!");
			if (inputs[0] == null && inputs[1] == null) { return false; }
			else if (inputs[0] == null && inputs[1].output() == true) { return true; }
			else if (inputs[0].output() == true && inputs[1] == null) { return true; }
			return false;
		}
	}

	@Override
	public void setInput(Gate g, int i) {
		if(i != 0 && i != 1) { return; }
		inputs[i] = g;
	}
}