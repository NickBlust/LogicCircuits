package gates;


/**
 * @author Dominik Baumann, Philipp Grzywaczyk
<<<<<<< HEAD
=======
 * @version 2, July 2022
>>>>>>> total_refactor_philipp_additions
 * This gate always returns true!
 * Use this internally for user input.
 */
public class TRUEgate extends ConstGate {
	
	/** This gate uses no input values. */
<<<<<<< HEAD
	public TRUEgate() {}
	
	/** Always returns null, as this gate has no input. */
	@Override
	public Gate getInput(int i) { return null; /* Do nothing. */
//	try { return this.inputs[i];			
//	} catch (NullPointerException e) {
//		return null;
//	}
	}
=======
	public TRUEgate() { resetStatus(); }
>>>>>>> total_refactor_philipp_additions
		
	/** Always returns true.
	 * @return true
	 */
	@Override
	public boolean output() { return true;	}
	
	@Override
	public Gate getInput(GateIndex i) { return null; }

	/** This gate has no inputs => do nothing.
	 * @param g (ignored)
	 * @param i (ignored)
	 */
	@Override
<<<<<<< HEAD
	public void setInput(Gate g, int i) { /*do nothing*/ }
=======
	public void setInput(Gate g, GateIndex i) { /* do nothing */ }

	@Override
	public String name() { return "TRUE";}
	
	@Override
	public void resetStatus() { status = Status.TRUE; }
>>>>>>> total_refactor_philipp_additions
}