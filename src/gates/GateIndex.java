package gates;

/**
 * Used to for indexing which of the inputs to set in a Gate
 * and on a tile representing a gate in the GUI.
 * <p>
 * "TOP" and "BOTTOM" are references to how the gates are 
 * displayed in the GUI!
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public enum GateIndex { 
	/** Use this one for setting the input gate at the top of the tile. */
	TOP, 
	/** Use this one for setting the input gate at the bottom of the tile. */		
	BOTTOM 
};