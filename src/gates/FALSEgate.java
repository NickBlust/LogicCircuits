package gates;

/**
<<<<<<< HEAD
 * @author Dominik Baumann, Philipp Grzywaczyk
=======
>>>>>>> total_refactor_philipp_additions
 * This gate always returns false!
 * <p> Use this internally for user input.
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 */
public class FALSEgate extends ConstGate {

	/** This gate uses no input values. */
<<<<<<< HEAD
	public FALSEgate() {}
		
	/** Always returns null, as this gate has no input. */
	@Override
	public Gate getInput(int i) { return null; /* do nothing */
//		try { return this.inputs[i];			
//		} catch (NullPointerException e) {
//			return null;
//		}
	}
	
=======
	public FALSEgate() { resetStatus(); }

>>>>>>> total_refactor_philipp_additions
	/** Always returns false.
	 * @return false
	 */
	@Override
	public boolean output() { return false;	}

	/** This gate has no inputs => do nothing.
	 * @param g (ignored)
	 * @param i (ignored)
	 */
	@Override
<<<<<<< HEAD
	public void setInput(Gate g, int i) { /*do nothing*/ }
=======
	public Gate getInput(GateIndex i) { return null; }

	@Override
	public void setInput(Gate g, GateIndex i) { /* do nothing */ }

	@Override
	public String name() { return "FALSE";}
	
	@Override
	public void resetStatus() { status = Status.FALSE; }
>>>>>>> total_refactor_philipp_additions
}