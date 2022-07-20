package boardModel;

import gates.*;
import gui.TileType;

/**
 * @author Dominik Baumann
 * @version 2, July 2022
 * <p>
 * Helper class which is used for example when loading a board from a file,
 * or when determining which version of a tile / gate to display in the GUI.
 */
public class Converter {
	
	
	/** When loading a board from a file,
	 * we need to be able to convert a String representation of
	 * a gate into an actual gate.
	 * @param name Name of the desired gate
	 * @return A new Gate object matching the name (or null if no proper name was given).
	 */
	public static Gate getGateFromName(String name) {
		if(name.equals("EMPTY"))
			return null;
		else if(name.equals("FALSE"))
			return new FALSEgate();
		else if(name.equals("TRUE"))
			return new TRUEgate();
		else if(name.equals("AND"))
			return new ANDgate();
		else if(name.equals("NAND"))
			return new NANDgate();
		else if(name.equals("NOR"))
			return new NORgate();
		else if(name.equals("NOT"))
			return new NOTgate();
		else if(name.equals("OR"))
			return new ORgate();
		else if(name.equals("XOR"))
			return new XORgate();
		else {
			System.out.println("ERROR: Could not find tile with name: \'" + name + "\'");
			return null;
		}
	}
	
	/** Get the proper (unevaluated) {@link gates.Gate Gate}
	 *  for a certain {@link gui.TileType TileType}.
	 * @param t Type of tile to be converted to a Gate.
	 * @return A {@link gates.Gate Gate} represented by the input type.
	 */
	public static Gate getGateFromType(TileType t) {
		return getGateFromName(t.toString());
	}
	
	/** Uses:
	 * <p>
	 * For computing valid input / output positions and
	 * connection lines, it is necessary to know the type of {@link gates.Gate Gate}.
	 * <p>
	 * The GUI needs to know which version of a tile / gate to display.
	 * @param gate A gate whose type we want to know.
	 * @return The {@link gui.TileType type of tile} representing this 
	 * {@link gates.Gate Gate}.
	 */
	public static TileType getTypeFromGate(Gate gate) {
		if(gate == null)
			return TileType.EMPTY;
		
		Status s = gate.status();
		if (gate instanceof FALSEgate)
			return TileType.FALSE;
		else if (gate instanceof TRUEgate)
			return TileType.TRUE;
		
		else if (gate instanceof ANDgate) {
			if(s == Status.TRUE) { return TileType.AND_TRUE; }
			else if(s == Status.FALSE) { return TileType.AND_FALSE; }
			return TileType.AND;
		}
		else if (gate instanceof NANDgate) {
			if(s == Status.TRUE) { return TileType.NAND_TRUE; }
			else if(s == Status.FALSE) { return TileType.NAND_FALSE; }
			return TileType.NAND;
		}
		else if (gate instanceof NORgate) {
			if(s == Status.TRUE) { return TileType.NOR_TRUE; }
			else if(s == Status.FALSE) { return TileType.NOR_FALSE; }
			return TileType.NOR;
		}
		else if (gate instanceof NOTgate) {
			if(s == Status.TRUE) { return TileType.NOT_TRUE; }
			else if(s == Status.FALSE) { return TileType.NOT_FALSE; }
			return TileType.NOT;
		}
		else if (gate instanceof ORgate) {
			if(s == Status.TRUE) { return TileType.OR_TRUE; }
			else if(s == Status.FALSE) { return TileType.OR_FALSE; }
			return TileType.OR;
		}
		else if (gate instanceof XORgate) {
			if(s == Status.TRUE) { return TileType.XOR_TRUE; }
			else if(s == Status.FALSE) { return TileType.XOR_FALSE; }
			return TileType.XOR;
		}
		else {
			System.out.println("ERROR (Converter): Conversion failed: " + gate.toString() + " with status: " + s);
			return null;
		}
	}
}