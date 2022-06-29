/**
 * 
 */
package gates;

/**
 * This gate returns true iff all input values are false.
 * @author Dominik Baumann, Philipp Grzywaczyk
 */
public class NORgate extends InputGate {

	/** By default, this gate uses two input values. */
	public NORgate() {
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
			return !(inputs[0].output() || inputs[1].output());
		} catch (NullPointerException e) {
			System.out.println("ERROR: Input gates not specified!");
			if(inputs[0] == null && inputs[1] == null) { return true; }
			else if(inputs[0] == null && inputs[1].output() == false) { return true; }
			else if(inputs[0].output() == false && inputs[1] == null) { return true; }
			return false;
		}
	}

	@Override
	public void setInput(Gate g, int i) {
		if(i != 0 && i != 1) { return; }
		inputs[i] = g;
	}
}