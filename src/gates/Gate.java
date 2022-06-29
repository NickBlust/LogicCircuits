/**
 * 
 */
package gates;

import gui.BoardEditor.TileType;

// IDEA: original input (true false), is represented as
// gate which just returns its value

/** 
 * @author Dominik Baumann, Philipp Grzywaczyk
 * Abstract class for representing a generic logic gate.
 * Any gate needs to be able to compute its output value.
 * For the purpose of computing the output value, each gate can get
 * one or more inputs from other gates (unless it is a {@link gates.ConstGate constant gate})
 * @see InputGate
 * @see ConstGate
 */
public abstract class Gate {
	
	/**
	 * @param i The identifier of the input gate.
	 * @return The input gate with identifier i. Returns null if the identifier is not valid.
	 */
	public abstract Gate getInput(int i);
	
	/** Compute the output truth value of this gate, 
	 * depending on the type of gate and its input gates/values.
	 * If an input gate is not set to an object (i.e. is null),
	 * the value for that gate defaults to false. 
	 * 
	 * IF A GATES INPUTS ARE NOT SET, THEY ARE TREATED AS FALSE!!!
	 * @return The output truth value of this gate.
	 */
	public abstract boolean output();
	
	/** Change the input of this gate.
	 * @param g The new gate whose output value is to be input.
	 * @param i The identifier of the gate which should be changed. Does nothing if the identifier is not valid.
	 */
	public abstract void setInput(Gate g, int i);
	
	public String gateName() {
		if(this instanceof TRUEgate)
			return "TRUE";
		else if(this instanceof FALSEgate)
			return "FALSE";
		else if(this instanceof ANDgate)
			return "AND";
		else if(this instanceof NANDgate)
			return "NAND";
		else if(this instanceof NORgate)
			return "NOR";
		else if(this instanceof NOTgate)
			return "NOT";
		else if(this instanceof ORgate)
			return "OR";
		else if(this instanceof XORgate)
			return "XOR";
		else {
			System.out.println("Unknown gate type: " + this);
			return "";
		}
	}
	
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
			System.out.println("ERROR: unknown gate name: \'" + name + "\'");
			return null;
		}
	}
	
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
			System.out.println("Unknown gate type: " + g);
			return TileType.EMPTYTILE;
		}
	}
}