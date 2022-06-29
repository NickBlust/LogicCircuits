/**
 * This class contains the data structure for storing a circuit made from single Gates.
 */
package logicCircuits;

import gates.*;
import gui.BoardEditor;
import gui.BoardEditor.TileType;
import utility.ConnectionInfo;
import utility.EvaluationInfo;
import utility.PositionInfo;
import utility.Vector2Int;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 * A logic circuit / logic board is an assortment of connected gates.* The gates are organized in a twice-nested ArrayList.
 * We use this instead of normal arrays, so we can dynamically increase the size.
 * Adding / removing Gates as well as connecting them is done via their coordinates.
 * @see Gate
 */
public class LogicCircuit {

	//Gate[][] board;
	List<Gate> output_gates;
	ArrayList<ArrayList<Gate> > board;
	ArrayList<ConnectionInfo> connections;
	
	public Vector2Int getModelDimensions() { 
		return new Vector2Int(board.size(), board.get(0).size()); 
	}
	
	public ArrayList<ConnectionInfo> getConnections() { return connections; }
	
	public ArrayList<ArrayList<Gate> > getBoard() { return board; }
	
	public ArrayList<PositionInfo> getAllGates() {
		ArrayList<PositionInfo> gates = new ArrayList<PositionInfo>();
	
		Gate g;
		for (int row = 0; row < board.size(); row++) {
			for (int col = 0; col < board.get(0).size(); col++) {
				System.out.println(row + " " + col);
				if((g = board.get(row).get(col)) != null)
					gates.add(new PositionInfo(g, new Vector2Int(row, col)));
			}
		}
		
		return gates;
	}
	
	/**
	 * Constructor for a new board. The ArrayLists are initialized as n empty
	 * lists of size m.
	 * @param n size in one dimension
	 * @param m size in second dimension
	 */
	public LogicCircuit(int n, int m){
		if (n <= 2 || m <= 2) {
			n = 2;
			m = 2;
		}
		board = new ArrayList<ArrayList<Gate>>();
		
		for (int i = 0; i < n; i++) {
			//ArrayList<Gate> tmp = new ArrayList<Gate>();
			board.add(i, new ArrayList<Gate>());
			//board.set(i, tmp);
			for (int j = 0; j < m; j++) {
				board.get(i).add(j, null);
			}
		}
		output_gates = new ArrayList<Gate>();
		connections = new ArrayList<ConnectionInfo>();  
	}
	
	/**
	 * This method is used to specify the gate to be added based on the visual
	 * tile from the GUI
	 * @param t Type of tile and thus gate to be added
	 * @param col position 1
	 * @param row position 2
	 */
	public void addGate(TileType t, int col, int row) {
		Gate g = null;
		if(t == TileType.EMPTYTILE)
			g = null;
		else if(t == TileType.TRUE)
			g = new TRUEgate();
		else if(t == TileType.FALSE)
			g = new FALSEgate();
		else if(t == TileType.AND)
			g = new ANDgate();
		else if(t == TileType.NAND)
			g = new NANDgate();
		else if(t == TileType.NOR)
			g = new NORgate();
		else if(t == TileType.NOT)
			g = new NOTgate();
		else if(t == TileType.OR)
			g = new ORgate();
		else if(t == TileType.XOR)
			g = new XORgate();
		else {
			System.out.println("ERROR: No matching gate! --> " + t);
		}
		if(g == null)
			removeGate(col, row);
		else
			addGate(g, col, row);
	}
	
	/**
	 * This method adds a gate to the nested ArrayList at a specified position.
	 * First, it is also added to output gates list.
	 * @param g Gate to be added
	 * @param pos1 Position 1
	 * @param pos2 Position 2
	 */
	public void addGate(Gate g, int pos1, int pos2) {
		if (g == null) return;
		if (!valid(pos1, pos2)) return;
		if (pos1 >= board.size()) {
			for (int i = board.size(); i <= pos1; i++) {
				board.add(i, new ArrayList<Gate>());
				for (int j = 0; j < board.get(0).size(); j++) {
					board.get(i).add(j, null);
				}
			}
		}
		
		if (pos2 >= board.get(pos1).size()) {
			for (int i = 0; i < board.size(); i++) {
				for (int j = board.get(pos1).size(); j <= pos2; j++) {
					board.get(i).add(j, null);
				}
			}
		}
		
		this.board.get(pos1).set(pos2, g);
		output_gates.add(g);
		for(Gate gate : output_gates) {
			if(gate != null) {
				System.out.println(gate);
			}
		}
	}
	
	/**
	 * Removes a gate from the specified position.
	 * @param pos1 Position 1
	 * @param pos2 Position 2
	 */
	public void removeGate(int pos1, int pos2) {
		if (!valid(pos1, pos2)) return;
		try { 
			output_gates.remove(this.board.get(pos1).get(pos2));
			this.board.get(pos1).remove(pos2);
		} catch (IndexOutOfBoundsException e) { /*do nothing*/ }
	}
	
	/**
	 * This method connects the output of a specified gate to the specified input port
	 * of a second, specified, input gate.
	 * @param outputgate_pos1 Position 1 of Gate whose output we use
	 * @param outputgate_pos2 Position 2 of Gate whose output we use
	 * @param inputgate_pos1 Position 1 of Gate whose input we set
	 * @param inputgate_pos2 Position 2 of Gate whose input we set
	 * @param input_pos Position in the input-array where we set the input
	 */
	public void connectGates(int outputgate_pos1, int outputgate_pos2, int inputgate_pos1, int inputgate_pos2, int input_pos) {
		if (!valid(inputgate_pos1, inputgate_pos2)) return;
		if (!valid(outputgate_pos1, outputgate_pos2)) return;
		Gate gate_in = board.get(inputgate_pos1).get(inputgate_pos2);
		Gate gate_out = board.get(outputgate_pos1).get(outputgate_pos2);
		gate_in.setInput(gate_out, input_pos);
		output_gates.remove(gate_out);
		connections.add(new ConnectionInfo(outputgate_pos1, outputgate_pos2, inputgate_pos1, inputgate_pos2, input_pos));
	}
	
	public void unconnectGate(ConnectionInfo c) {
		Gate outGate = board.get(c.target_col).get(c.target_row);
		outGate.setInput(null, c.id - 1);		
		connections.remove(c);
	}
	
	/**
	 * Unconnects the input at input-index num from the gate at specified position
	 * @param pos1 Position 1
	 * @param pos2 Position 2
	 * @param num Input-index to be unconnected
	 */
	public void unconnectGate(int pos1, int pos2, int num) {
		if (!valid(pos1, pos2)) return;
		Gate gate = board.get(pos1).get(pos2);
		Gate input = gate.getInput(num);
		output_gates.add(input);
		gate.setInput(null, num);	
	}
	
	/**
	 * returns an array with all the output values of gates which are not used as inputs
	 * @return array of boolean variables
	 */
	public boolean[] get_circuit_output() {
		boolean[] circuit_output = new boolean[number_of_output_gates()]; 
		
		for (int i = 0; i < number_of_output_gates(); i++) {
			circuit_output[i] = output_gates.get(i).output();
		}
		return circuit_output;
	}
	
	/**
	 * helper method, used above
	 * @return integer the number of output gates
	 */
	private int number_of_output_gates() {
		while (output_gates.remove(null));
		return output_gates.size();
	}
	
	/**
	 * helper method, checks if two position parameters are valid
	 * @param pos1 position 1
	 * @param pos2 position 2
	 * @return boolean true iff they are valid, false else
	 */
	private boolean valid(int pos1, int pos2) {
		return (0 <= pos1) && (0 <= pos2);
	}
	
	/**
	 * Used for evaluating the states of all intermediate gate and colouring them properly
	 * @return Evaluation Info for colouring the gates
	 */
	public ArrayList<EvaluationInfo> evaluateAndVisualize() {
		while (output_gates.remove(null));
		ArrayList<Gate> gatesToEvaluate = new ArrayList<Gate>();
		ArrayList<Gate> evaluatedGates = new ArrayList<Gate>();
		ArrayList<EvaluationInfo> info = new ArrayList<EvaluationInfo>();
		
//		for(Gate g : output_gates) {
//			System.out.println(g);
//			gatesToEvaluate.add(g);
//		}
		Gate g;
		for(int row = 0; row < board.size(); row++) {
			for(int col = 0; col < board.get(row).size(); col++) {
				if((g = board.get(row).get(col)) != null && !(g instanceof TRUEgate || g instanceof FALSEgate)) {
					EvaluationInfo e = new EvaluationInfo(null, col, row, g.output());
					if(g instanceof ANDgate) { e.type = TileType.AND; }
					else if(g instanceof NANDgate) { e.type = TileType.NAND; }
					else if(g instanceof NORgate) { e.type = TileType.NOR; }
					else if(g instanceof NOTgate) { e.type = TileType.NOT; }
					else if(g instanceof ORgate) { e.type = TileType.OR; }
					else if(g instanceof XORgate) { e.type = TileType.XOR; }
					else {
						System.out.println("ERROR: Unknown type of gate!");
					}
					info.add(e);
				}
			}
		}
		return info;
		
	}
}