/**
 * 
 */
package command;

/**
 * @author domin
 *
 */
public interface Command {

	public boolean execute();
	
	public void undo();
}
