/**
 * 
 */
package gates;

// IDEA: original input (true false), is represented as
// gate which just returns its value

/** 
 * @author Dominik Baumann, Philipp Grzywaczyk
 * Abstract class for representing a generic logic gate.
 * Any gate needs to be able to compute its output value.
 * For the purpose of computing the output value, each gate can get
 * one or more inputs from other gates (unless it is a {@link gates.ConstGate constant gate})
 * @see InputGate
 * @see ConstGate
 */
public abstract class Gate {
	
	/**
	 * @param i The identifier of the input gate.
	 * @return The input gate with identifier i. Returns null if the identifier is not valid.
	 */
	public abstract Gate getInput(int i);
	
	/** Compute the output truth value of this gate, 
	 * depending on the type of gate and its input gates/values.
	 * If an input gate is not set to an object (i.e. is null),
	 * the value for that gate defaults to false. 
	 * 
	 * IF A GATES INPUTS ARE NOT SET, THEY ARE TREATED AS FALSE!!!
	 * @return The output truth value of this gate.
	 */
	public abstract boolean output();
	
	/** Change the input of this gate.
	 * @param g The new gate whose output value is to be input.
	 * @param i The identifier of the gate which should be changed. Does nothing if the identifier is not valid.
	 */
	public abstract void setInput(Gate g, int i);
}