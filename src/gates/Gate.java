/**
 * 
 */
package gates;

// IDEA: original input (true false), is represented as
// gate which just returns its value

/** 
 * @author Dominik Baumann
 * Abstract class for representing a generic logic gate.
 * Any gate needs to be able to compute its output value.
 * For the purpose of computing the output value, each gate can get
 * one or more inputs from other gates
 */
public abstract class Gate {
	Gate[] inputs;
	public abstract boolean output();
	public abstract void setInput(Gate g, int i);
}