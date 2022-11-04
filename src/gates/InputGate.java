<<<<<<< HEAD
/**
 * 
 */
package gates;

/**
 * Inherit from this class for gates which have one or more input gates.
 * @author Dominik Baumann
 */
public abstract class InputGate extends Gate {
	/** Stores the gates representing input (boolean) values. */
	Gate[] inputs;
=======
package gates;

import java.util.EnumMap;

/**
 * Inherit from this class for gates which have one or more input gates.
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
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
	@Override
	public void setInput(Gate g, GateIndex i) { inputs.put(i, g); }
	
	
	
	@Override
	public boolean output() { 
		boolean result = computeOutput(topInput(), bottomInput());
		setStatus(result);
		return result;
	}
	
	
	
	/** Use this function to compute the truth value of a gate
	 * (which depends on the type of gate).
	 * @param a One input value of the gate (usually TOP).
	 * @param b One input value of the gate (usually BOTTOM).
	 * @return The truth value of the gates for the given input.
	 */
	protected abstract boolean computeOutput(boolean a, boolean b);
	
	
	
	/**
	 * @return The Input value specified by index {@link gates.GateIndex#TOP TOP}.
	 */
	protected boolean topInput() {
		try { return inputs.get(GateIndex.TOP).output();
		} catch (NullPointerException e) { return false; }
	}
	
	
	
	/**
	 * @return The Input value specified by index {@link gates.GateIndex#BOTTOM BOTTOM}.
	 */
	protected boolean bottomInput() {
		try { return inputs.get(GateIndex.BOTTOM).output();
		} catch (NullPointerException e) { return false; }
	}
	
	@Override
	public void resetStatus() { status = Status.UNEVALUATED; }
>>>>>>> total_refactor_philipp_additions
}