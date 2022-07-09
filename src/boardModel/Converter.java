/**
 * 
 */
package boardModel;

import gates.*;
import gui.TiledCanvas.TileType;

/**
 * @author domin
 *
 */
public class Converter {
	public static Gate getGateFromType(TileType t) {
		if(t== TileType.EMPTY)
			return null;
		else if(t== TileType.FALSE)
			return new FALSEgate();
		else if(t== TileType.TRUE)
			return new TRUEgate();
		else if(t== TileType.AND)
			return new ANDgate();
		else if(t== TileType.NAND)
			return new NANDgate();
		else if(t== TileType.NOR)
			return new NORgate();
		else if(t== TileType.NOT)
			return new NOTgate();
		else if(t== TileType.OR)
			return new ORgate();
		else if(t== TileType.XOR)
			return new XORgate();
		else {
			System.out.println("ERROR: Evaluation failed: " + t);
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
	
//	/**
//	 * @param gate
//	 * @return
//	 */
//	public static TileType getTypeFromGate(Gate gate) {
//		if (gate instanceof FALSEgate)
//			return TileType.FALSE;
//		else if (gate instanceof TRUEgate)
//			return TileType.TRUE;
//		else if (gate instanceof ANDgate)
//			return TileType.AND;
//		else if (gate instanceof NANDgate)
//			return TileType.NAND;
//		else if (gate instanceof NORgate)
//			return TileType.NOR;
//		else if (gate instanceof NOTgate)
//			return TileType.NOT;
//		else if (gate instanceof ORgate)
//			return TileType.OR;
//		else if (gate instanceof XORgate)
//			return TileType.XOR;
//		else {
//			System.out.println("ERROR (Converter): Evaluation failed: " + gate.toString());
//			return null;
//		}
//	}
//
//	/**
//	 * @param temp
//	 * @param boolean1
//	 * @return
//	 */
//	public static TileType getTypeFromGate(Gate gate, boolean b) {
//		if (gate instanceof FALSEgate)
//			return TileType.FALSE;
//		else if (gate instanceof TRUEgate)
//			return TileType.TRUE;
//		else if (gate instanceof ANDgate)
//			return b ? TileType.AND_TRUE : TileType.AND_FALSE;
//		else if (gate instanceof NANDgate)
//			return b ? TileType.NAND_TRUE : TileType.NAND_FALSE;
//		else if (gate instanceof NORgate)
//			return b ? TileType.NOR_TRUE : TileType.NOR_FALSE;
//		else if (gate instanceof NOTgate)
//			return b ? TileType.NOT_TRUE : TileType.NOT_FALSE;
//		else if (gate instanceof ORgate)
//			return b ? TileType.OR_TRUE : TileType.OR_FALSE;
//		else if (gate instanceof XORgate)
//			return b ? TileType.XOR_TRUE : TileType.XOR_FALSE;
//		else {
//			System.out.println("ERROR (Converter): Evaluation failed: " + gate.toString());
//			return null;
//		}
//	}
}