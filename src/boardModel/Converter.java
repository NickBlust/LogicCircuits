/**
 * 
 */
package boardModel;

import gates.*;
import gui.TileType;

/**
 * @author domin
 *
 */
public class Converter {
	
	public static Gate getGateFromType(TileType t) {
		return getGateFromName(t.toString());
	}
	
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
	
	/**
	 * @param gate
	 * @return
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
			System.out.println("ERROR (Converter): Evaluation failed: " + gate.toString() + " with status: " + s);
			return null;
		}
	}
}