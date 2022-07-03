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
	public NOTgate() { }
	
	@Override
	public boolean output() {
		try {
			return !inputs.get(GateIndex.TOP).output();
		} catch (NullPointerException e) {
			System.out.println("ERROR: Input gates not specified! " + this);
			return true;
		}
	}

	@Override
	public Gate getInput(GateIndex i) {
		return inputs.get(GateIndex.TOP);
	}
	
	@Override
	public void setInput(Gate g, GateIndex i) {
		inputs.put(GateIndex.TOP, g);
	}
}