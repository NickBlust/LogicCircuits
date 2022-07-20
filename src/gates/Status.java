package gates;

/**
 * @author Dominik Baumann
 * @version 2, July 2022
 */

public enum Status {
	/** if this gate has not been evaluated */
	UNEVALUATED,
	/** if this gate currently evaluates to true */
	TRUE,
	/** if this gate currently evaluates to false */
	FALSE
};