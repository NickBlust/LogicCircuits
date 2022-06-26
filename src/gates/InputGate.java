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
}