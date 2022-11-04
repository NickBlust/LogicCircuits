package gates;

/**
 * This gate returns true iff its input is false.
 * @author Dominik Baumann, Philipp Grzywaczyk
<<<<<<< HEAD
=======
 * @version 2, July 2022
>>>>>>> total_refactor_philipp_additions
 */
public class NOTgate extends InputGate {

	/** By default, this gate uses one input value. */
<<<<<<< HEAD
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
=======
	public NOTgate() { }

	@Override
	protected boolean computeOutput(boolean a, boolean b) {
		return !a;
	}
	
	@Override
	public void setInput(Gate g, GateIndex i) {
		if(g == null || i == null) { return; }
		if(i.equals(GateIndex.TOP)) {
			inputs.put(i, g); 
			inputs.put(GateIndex.BOTTOM, null);
		}
		else {
			inputs.put(i, g); 
			inputs.put(GateIndex.TOP, null);
>>>>>>> total_refactor_philipp_additions
		}
	}
	
	@Override
<<<<<<< HEAD
	public void setInput(Gate g, int i) {
		if(i != 0) { return; }
		inputs[i] = g;
	}
=======
	public String name() { return "NOT";}
>>>>>>> total_refactor_philipp_additions
}