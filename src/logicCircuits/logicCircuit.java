/**
 * 
 */
package logicCircuits;

import gates.*;
import gui.BoardEditor;
import gui.BoardEditor.TileType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 * A logic circuit / logic board is an assortment of connected gates.
 * @see Gate
 */
public class LogicCircuit {

	//Gate[][] board;
	List<Gate> output_gates;
	ArrayList<ArrayList<Gate> > board;
	
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
	}
	
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
		addGate(g, col, row);
	}
	
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
	}
	
	public void removeGate(int pos1, int pos2) {
		if (!valid(pos1, pos2)) return;
		output_gates.remove(this.board.get(pos1).get(pos2));
		this.board.get(pos1).remove(pos2);
	}
	
	public void connectGates(int inputgate_pos1, int inputgate_pos2, int outputgate_pos1, int outputgate_pos2, int input_pos) {
		if (!valid(inputgate_pos1, inputgate_pos2)) return;
		if (!valid(outputgate_pos1, outputgate_pos2)) return;
		Gate gate_in = board.get(inputgate_pos1).get(inputgate_pos2);
		Gate gate_out = board.get(outputgate_pos1).get(outputgate_pos2);
		gate_in.setInput(gate_out, input_pos);
		output_gates.remove(gate_out);
	}
	
	public void unconnectGate(int pos1, int pos2, int num) {
		if (!valid(pos1, pos2)) return;
		Gate gate = board.get(pos1).get(pos2);
		Gate input = gate.getInput(num);
		output_gates.add(input);
		gate.setInput(null, num);
		
	}
	
	public boolean[] get_circuit_output() {
		boolean[] circuit_output = new boolean[number_of_output_gates()]; 
		
		for (int i = 0; i < number_of_output_gates(); i++) {
			circuit_output[i] = output_gates.get(i).output();
		}
		return circuit_output;
	}
	
	private int number_of_output_gates() {
		while (output_gates.remove(null));
		return output_gates.size();
	}
	
	private boolean valid(int pos1, int pos2) {
		return (0 <= pos1) && (0 <= pos2);
	}
}