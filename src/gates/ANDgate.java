package gates;

/**
 * This gate returns true iff all input values are true.
 * @author Dominik Baumann, Philipp Grzywaczyk
<<<<<<< HEAD
 */
public class ANDgate extends InputGate {

	
	/** By default, this gate uses two input values. */
	public ANDgate() {
		inputs = new Gate[2];
	}
	
	public Gate getInput(int i) {
		try { return this.inputs[i];			
		} catch (NullPointerException e) {
			return null;
		}
	}
	
=======
 * @version 2, July 2022
 */
public class ANDgate extends InputGate {

	/** By default, this gate uses two input values. */
	public ANDgate() { }

>>>>>>> total_refactor_philipp_additions
	@Override
	protected boolean computeOutput(boolean a, boolean b) {
		return a && b;
	}

	@Override
<<<<<<< HEAD
	public void setInput(Gate g, int i) {
		if(i != 0 && i != 1) { return; }
		inputs[i] = g;
	}
=======
	public String name() { return "AND";}
>>>>>>> total_refactor_philipp_additions
}