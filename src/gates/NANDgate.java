/**
 * 
 */
package gates;

/**
 * This gate returns true iff not all input values are true.
 * @author Dominik Baumann, Philipp Grzywaczyk
 */
public class NANDgate extends InputGate {

	/** By default, this gate uses two input values. */
	public NANDgate() {
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
			return !(inputs[0].output() && inputs[1].output());
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