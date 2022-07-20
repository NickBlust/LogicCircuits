package gates;

/** 
 * Inherit from this class for gates which have no input gates 
 * ({@link gates.TRUEgate TRUEgate} and {@link gates.FALSEgate FALSEgate})
 * <p> Not deleting this class for now, as it might become useful to distinguish
 * between ConstGates and InputGates at some point ...
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public abstract class ConstGate extends Gate {
	// This class needs no array of inputs
}