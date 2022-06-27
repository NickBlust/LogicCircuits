/**
 * 
 */
package logicCircuits;

import gates.Gate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk
 * A logic circuit / logic board is an assortment of connected gates.
 * @see Gate
 */
public class logicCircuit {

	//Gate[][] board;
	List<Gate> output_gates;
	ArrayList<ArrayList<Gate> > board;
	
	public logicCircuit(int n, int m){
		if (n <= 18 || m <= 25) {
			n = 18;
			m = 25;
		}
		board = new ArrayList<ArrayList<Gate>>(m);
		for (int i = 0; i < m; i++) {
			board.set(i, new ArrayList<Gate>(n));
		}
		output_gates = new ArrayList<Gate>();
	}
	
	public void addGate(Gate g, int pos1, int pos2) {
		if (g == null) return;
		if (!valid(pos1, pos2)) return;
		this.board.get(pos2).set(pos1, g);
		output_gates.add(g);		
	}
	
	public void removeGate(int pos1, int pos2) {
		if (!valid(pos1, pos2)) return;
		output_gates.remove(this.board.get(pos2).get(pos1));
		this.board.get(pos2).remove(pos1);
	}
	
	public void connectGates(int inputgate_pos1, int inputgate_pos2, int outputgate_pos1, int outputgate_pos2, int input_pos) {
		if (!valid(inputgate_pos1, inputgate_pos2)) return;
		if (!valid(outputgate_pos1, outputgate_pos2)) return;
		Gate gate_in = board.get(inputgate_pos2).get(inputgate_pos1);
		Gate gate_out = board.get(outputgate_pos2).get(outputgate_pos1);
		gate_in.setInput(gate_out, input_pos);
		output_gates.remove(gate_out);
	}
	
	public void unconnectGate(int pos1, int pos2, int num) {
		if (!valid(pos1, pos2)) return;
		Gate gate = board.get(pos2).get(pos1);
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
		return (0 <= pos1 && pos1 < board.get(0).size()) && (0 <= pos2 && pos2 < board.size());
	}
}