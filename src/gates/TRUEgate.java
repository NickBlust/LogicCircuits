/**
 * 
 */
package gates;


/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 * This gate always returns true!
 * Use this internally for user input.
 */
public class TRUEgate extends ConstGate {
	
	/** This gate uses no input values. */
	public TRUEgate() {}
		
	/** Always returns true.
	 * @return true
	 */
	@Override
	public boolean output() { return true;	}
	
	@Override
	public Gate getInput(GateIndex i) { return null; }

	@Override
	public void setInput(Gate g, GateIndex i) { /* do nothing */ }

	@Override
	public String name() { return "TRUE";}
}