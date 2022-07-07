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
		if (gate instanceof FALSEgate)
			return TileType.FALSE;
		else if (gate instanceof TRUEgate)
			return TileType.TRUE;
		else if (gate instanceof ANDgate)
			return TileType.AND;
		else if (gate instanceof NANDgate)
			return TileType.NAND;
		else if (gate instanceof NORgate)
			return TileType.NOR;
		else if (gate instanceof NOTgate)
			return TileType.NOT;
		else if (gate instanceof ORgate)
			return TileType.OR;
		else if (gate instanceof XORgate)
			return TileType.XOR;
		else {
			System.out.println("ERROR: Evaluation failed: " + gate.toString());
			return null;
		}
	}
}