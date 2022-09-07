package command;

/** A command wraps a task - like placing a gate in the model -
 * into an object, storing the necessary information to
 * undo and redo the task.
 * 
 * @author Dominik Baumann, Philipp Grzywaczyk
 * @version 2, July 2022
 * @see app.Controller
 */
public interface Command {

	/** Carry out the task.
	 * <p>
	 * Iff the execution of a command returns "true", it will be stored
	 * on a stack so that it can potentially be undone.
	 * @return "true" iff the execution of the task changed the model.
	 */
	public boolean execute();
	
	/** Reverses the effect of the task carried out
	 * by this command.
	 */
	public void undo();
}