/**
 * 
 */
package logicCircuits;

import gates.Gate;
import java.util.List;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 * A logic circuit / logic board is an assortment of connected gates.
 * @see Gate
 */
public class logicCircuit {

	Gate[][] board;
	List<Gate> output_gates;
	
	public logicCircuit(int n, int m){
		board = new Gate[n][m];
		
		
	}
	
	public void addGate(Gate g, int pos1, int pos2) {
		this.board[pos1][pos2] = g;
		output_gates.add(g);		
	}
	
	public void removeGate(int pos1, int pos2) {
		output_gates.remove(this.board[pos1][pos2]);
		this.board[pos1][pos2] = null;
	}
	
	public void connectGates(int inputgate_pos1, int inputgate_pos2, int outputgate_pos1, int outputgate_pos2, int input_pos) {
		Gate gate_in = board[inputgate_pos1][inputgate_pos2];
		Gate gate_out = board[outputgate_pos1][outputgate_pos2];
		gate_in.setInput(gate_out, input_pos);
		output_gates.remove(gate_out);
	}
	
	public void unconnectGate(int pos1, int pos2, int num) {
		Gate gate = board[pos1][pos2];
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
		int out = 0;
		while (output_gates.remove(null));
		out = output_gates.size();
		return out;
	}
}