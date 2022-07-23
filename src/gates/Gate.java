/**
 * 
 */
package gates;

/** 
 * Abstract class for representing a generic logic gate.
 * Any gate needs to be able to compute its output value.
 * For the purpose of computing the output value, each gate can get
 * one or more inputs from other gates (unless it is a {@link gates.ConstGate constant gate}).
 * <p>
 *    ==> Input is not given as booleans, but as Gate!
 * <p>
 *    ==> Missing input gates are treated as false!
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 * @see InputGate
 * @see ConstGate
 */
public abstract class Gate {
	/** Each gate has a name,e.g. objects of type 
	 * {@link gates.ANDgate ANDgate} will be referred to by "AND".
	 * @return The name of this gate.
	 */
	public abstract String name();
	
	/** Compute the output truth value of this gate, 
	 * depending on the type of gate and its input gates/values.
	 * If an input gate is not set to an object (i.e. is null),
	 * the value for that gate defaults to false. 
	 * 
	 * IF A GATES INPUTS ARE NOT SET, THEY ARE TREATED AS FALSE!!!
	 * @return The output truth value of this gate.
	 */
	public abstract boolean output();
	
	/**
	 * @param i The identifier of the input gate.
	 * @return The input gate with identifier i. Returns null if the identifier is not valid.
	 */
	public abstract Gate getInput(GateIndex i);
	
	/** Change the input of this gate.
	 * @param g The new gate whose output value is to be input.
	 * @param i The identifier of the gate which should be changed. Does nothing if the identifier is not valid.
	 */
	public abstract void setInput(Gate g, GateIndex i);
	
	/** The status of a gate is relevant for displayint it in the GUI. */
	protected Status status = Status.UNEVALUATED;
	
	/**
	 * @return The Status of this gate.
	 */
	public Status status() { return status; }
	
	/** Reset the status to {@link gates.Status#UNEVALUATED UNEVALUATED}. */
	public abstract void resetStatus();

	/** Set the status of this gate to 
	 * {@link gates.Status#TRUE TRUE} or 
	 * {@link gates.Status#FALSE FALSE} depending
	 * on its truth value in the current evaluation.
	 * @param b The current truth value of this gates output.
	 */
	protected void setStatus(boolean b) { status = (b ? Status.TRUE : Status.FALSE); }
}