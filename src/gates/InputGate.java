/**
 * 
 */
package gates;

import java.util.EnumMap;

/**
 * Inherit from this class for gates which have one or more input gates.
 * @author Dominik Baumann
 */
public abstract class InputGate extends Gate {
	
	/** Stores the gates representing input (boolean) values. */
	EnumMap<GateIndex, Gate> inputs = new EnumMap<GateIndex, Gate>(GateIndex.class);
	
	/**
	 * @param i The identifier of the input gate.
	 * @return The input gate with identifier i. Returns null if the identifier is not valid.
	 */
	public Gate getInput(GateIndex i) { return inputs.get(i); }
	
	/** Change the input of this gate.
	 * @param g The new gate whose output value is to be input.
	 * @param i The identifier of the gate which should be changed. Does nothing if the identifier is not valid.
	 */
	public void setInput(Gate g, GateIndex i) { inputs.put(i, g); }
}