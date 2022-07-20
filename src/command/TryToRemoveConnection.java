package command;

import boardModel.LogicBoard;
import gates.GateIndex;
import utility.Vector2Int;
import utility.Vector3Int;

/** This command wraps the task of removing a
 * connection between two gates in a
 * {@link boardModel.LogicBoard model of a logic circuits board}.
 * @author Dominik Baumann
 * @version 2, July 2022
 */
public class TryToRemoveConnection implements Command {

	/** The model in which to remove a connection. */
	LogicBoard model;
	
	/** A position which is to be investigated
	 * for being close to a connection in the model.
	 */
	Vector3Int pos;
	
	/** If the removal was successful, store the
	 * grid position of the Gate that the
	 * connection was exiting from. 
	 */
	Vector2Int providesInput;

	/** If the removal was successful, store the
	 * grid position of the Gate that the
	 * connection was leading to.
	 */
	Vector2Int getsInput;

	/** If the removal was successful, store the
	 * index of the input that was removed.
	 */
	GateIndex index;
	

	/** Constructs a command to attempt to remove a
	 * connection between two gates, based on
	 * a right mouse click. 
	 * <p>
	 * The command only leads to the removal of a connection
	 * if the right mouse click was close enough to a connection.
	 * @param model_  The model in which to remove a connection.
	 * @param clickPos The position of the right mouse click,
	 * which needs to be close to a connection for it to be actually removed.
	 */
	public TryToRemoveConnection(LogicBoard model_, Vector3Int clickPos) {
		model = model_;
		pos = clickPos;
	}

	@Override
	public boolean execute() {
		return model.attemptConnectionRemoval(pos, this);
	}

	@Override
	public void undo() {
		model.addConnection(providesInput, getsInput, index);
	}
	
	/** This command actually is passed on by the controller to the model,
	 * to facilitate the storage of the positions of the gates
	 * between which a connection was removed (the input's index also 
	 * needs to be stored).
	 * <p>
	 * This becomes necessary as at the time of creating this command,
	 * it is unclear, whether the right click leading to the generation
	 * of this command was actually close to a connection in
	 * the visualization of the board.
	 * @param receivesInput_ Position of the Gate to which the connection is leading.
	 * @param providesInput_ Position of the Gate from which the connection is exiting.
	 * @param index_ The index of the input that the connection is leading to.
	 */
	public void setInfo(Vector2Int receivesInput_, Vector2Int providesInput_, GateIndex index_) {
		getsInput = receivesInput_;
		providesInput = providesInput_;
		index = index_;
	}
}