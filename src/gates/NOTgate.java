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
	protected boolean computeOutput(boolean a, boolean b) {
		return !a;
	}
	
	@Override
	public void setInput(Gate g, GateIndex i) {
		if(g == null) { return; }
		if(i.equals(GateIndex.TOP)) {
			inputs.put(i, g); 
			inputs.put(GateIndex.BOTTOM, null);
		}
		else {
			inputs.put(i, g); 
			inputs.put(GateIndex.TOP, null);
		}
	}
	
	@Override
	public String name() { return "NOT";}
}