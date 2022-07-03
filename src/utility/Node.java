/**
 * 
 */
package utility;

/**
 * @author domin
 *
 */
public class Node {
	public Vector2Int c;
	public int id; // 0 == output, 1 top, 2 bottom
	
	public Node(Vector2Int v, int id_) { c = v; id = id_; }
	
	public boolean equals(Node n) {
		return n.c.equals(c) && id == n.id;
	}
	
	public String toString() {
		return c + ": " + id;
	}
}