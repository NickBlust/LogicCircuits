/**
 * 
 */
package gates;

import gui.BoardEditor.TileType;

/**
 * A helper for converting between gates, gate's names and the corresponding {@link TileType tile types}.
 * @author Dominik Baumann
 * @see Gate
 */
public class TileConverter {
	
	/** Get the name of a gate from its class / type.
	 * @param g The gate under investigation.
	 * @return The name of that gate as String.
	 */
	public static String gateName(Gate g) {
		if(g instanceof TRUEgate)
			return "TRUE";
		else if(g instanceof FALSEgate)
			return "FALSE";
		else if(g instanceof ANDgate)
			return "AND";
		else if(g instanceof NANDgate)
			return "NAND";
		else if(g instanceof NORgate)
			return "NOR";
		else if(g instanceof NOTgate)
			return "NOT";
		else if(g instanceof ORgate)
			return "OR";
		else if(g instanceof XORgate)
			return "XOR";
		else {
			System.out.println("ERROR (TileConverter): Unknown gate type: " + g);
			return "";
		}
	}
	
	/** Reversal of {@link TileConverter#gateName gateName}: 
	 * From a Gate's name, get such a gate object.
	 * @param name The name of the desired gate.
	 * @return A gate object of the desired type, or null if the request is invalid.
	 */
	public static Gate getGateFromName(String name) {
		if(name.equals("TRUE"))
			return new TRUEgate();
		else if(name.equals("FALSE"))
			return new FALSEgate();
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
			System.out.println("ERROR (TileConverter): unknown gate name: \'" + name + "\'");
			return null;
		}
	}
	
	/** From a gate object, get the corresponding {@link TileType TileType} enum.
	 * @param g The gate under investigation.
	 * @return The corresponding TileType, or TileType.EMPTYTile if the type of gate is unknown.
	 */
	public static TileType getTileTypeFromGate(Gate g) {
		if(g == null)
			return TileType.EMPTYTILE;
		
		if(g instanceof TRUEgate)
			return TileType.TRUE;
		else if(g instanceof FALSEgate)
			return TileType.FALSE;
		else if(g instanceof ANDgate)
			return TileType.AND;
		else if(g instanceof NANDgate)
			return TileType.NAND;
		else if(g instanceof NORgate)
			return TileType.NOR;
		else if(g instanceof NOTgate)
			return TileType.NOT;
		else if(g instanceof ORgate)
			return TileType.OR;
		else if(g instanceof XORgate)
			return TileType.XOR;
		else {
			System.out.println("ERROR (TileConverter): Unknown gate type: " + g);
			return TileType.EMPTYTILE;
		}
	}
}