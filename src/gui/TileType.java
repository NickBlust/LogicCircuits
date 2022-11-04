package gui;

/** In order to visualize gates in the Logic Board in the GUI,
 * we identify them using enums of this type.
 * <p>
 * All types of gates (except {@link gates.TRUEgate TRUEgate}
 * and {@link gates.FALSEgate FALSEgate}) can be shown in their
 * {@link gates.Status#UNEVALUATED UNEVALUATED} status (their
 * output is displayed white), their {@link gates.Status#TRUE TRUE}
 * status (their output is displayed green) or their 
 * {@link gates.Status#FALSE FALSE} status (their output is red).  
 * @author Cameron McGregor, Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 * @see gates.Gate
 * @see gates.Status
 */
public enum TileType {
	/** If there is no gate. */
	EMPTY,
	
	/** Represents a {@link gates.FALSEgate FALSEgate}. */
	FALSE,
	
	/** Represents a {@link gates.TRUEgate TRUEgate}. */
	TRUE,
	
	/** Represents an {@link gates.ANDgate ANDgate} in its "UNEVALUATED" status. */
	AND,
	
	/** Represents a {@link gates.NANDgate NANDgate} in its "UNEVALUATED" status. */
	NAND,
	
	/** Represents a {@link gates.NORgate NORgate} in its "UNEVALUATED" status. */
	NOR,
	
	/** Represents a {@link gates.NOTgate NOTgate} in its "UNEVALUATED" status. */
	NOT,
	
	/** Represents an {@link gates.ORgate ORgate} in its "UNEVALUATED" status. */
	OR,
	
	/** Represents a {@link gates.XORgate XORgate} in its "UNEVALUATED" status. */
	XOR,
	
	
	
	/** Represents an {@link gates.ANDgate ANDgate} in its "TRUE" status. */
	AND_TRUE, 
	
	/** Represents a {@link gates.NANDgate NANDgate} in its "TRUE" status. */
	NAND_TRUE, 
	
	/** Represents a {@link gates.NORgate NORgate} in its "TRUE" status. */
	NOR_TRUE, 
	
	/** Represents a {@link gates.NOTgate NOTgate} in its "TRUE" status. */
	NOT_TRUE, 
	
	/** Represents an {@link gates.ORgate ORgate} in its "TRUE" status. */
	OR_TRUE, 

	/** Represents a {@link gates.XORgate XORgate} in its "TRUE" status. */
	XOR_TRUE,
	
	
	
	/** Represents an {@link gates.ANDgate ANDgate} in its "FALSE" status. */
	AND_FALSE, 
	
	/** Represents a {@link gates.NANDgate NANDgate} in its "FALSE" status. */
	NAND_FALSE, 
	
	/** Represents a {@link gates.NORgate NORgate} in its "FALSE" status. */
	NOR_FALSE, 
	
	/** Represents a {@link gates.NOTgate NOTgate} in its "FALSE" status. */
	NOT_FALSE, 
	
	/** Represents an {@link gates.ORgate ORgate} in its "FALSE" status. */
	OR_FALSE, 

	/** Represents a {@link gates.XORgate XORgate} in its "FALSE" status. */
	XOR_FALSE
};