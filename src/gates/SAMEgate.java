/**
 * 
 */
package gates;

/**
 * @author Dominik Baumann
 * TODO: class might be deprecated in the near future (replaced by FALSEgate and TRUEgate)
 * This gate returns its input value UNCHANGED!
 * Use this internally for user input.
 */
public class SAMEgate extends Gate {

	private boolean b = false;
	public SAMEgate() {
		inputs = new Gate[0];
	}
	
	public SAMEgate(boolean a) { b = a;	}
	
	@Override
	public boolean output() { return b;	}

	// do nothing
	// TODO: suggestion to change input after construction:
	// new suggestion: create two dummy-classes FALSEgate and TRUEgate which always output false/true
	// old suggestion: public void setInput(Gate g, int i) { b = i != 0; } // false iff i = 0, true otherwise => call as e.g. SAMEgate.setInput(null, 1) 
	@Override
	public void setInput(Gate g, int i) { }

}
