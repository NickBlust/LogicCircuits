package gates;


/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 * This gate always returns true!
 * Use this internally for user input.
 */
public class TRUEgate extends ConstGate {
	
	/** This gate uses no input values. */
	public TRUEgate() { resetStatus(); }
		
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
	
	@Override
	public void resetStatus() { status = Status.TRUE; }
}