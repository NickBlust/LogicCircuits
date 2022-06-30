/**
 * 
 */
package utility;

import java.util.ArrayList;

/**
 * @author domin
 *
 */
public class GateGraph {
	
	public class Edge {
		public Node start;
		public Node end;
		
		public Edge(Node from, Node to) { start = from; end = to; }
		
		public boolean equals(Edge e) {
			return e.start.equals(start) && e.end.equals(end);
		}
		
		public String toString() {
			return start.toString() + " --> " + end.toString();
			}
	}
	
	
	public ArrayList<Node> nodes;
	public ArrayList<Edge> edges;
	
	public GateGraph() {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
	}
	
	public void addNode(Vector2Int vec, int id_) {
		Node n = new Node(vec, id_);
		for(Node temp : nodes) {
			if(temp.equals(n))
				return;
		}
			nodes.add(n);
	}
	
	public void addNode(Node n) {
		for(Node temp : nodes) {
			if(temp.equals(n))
				return;
		}
		System.out.println("Adding " + n);
			nodes.add(n);
	}
	
	public void removeNode(Node n) {
		Node toDelete = null;
		for(Node temp : nodes) {
			if(temp.equals(n)) {
				toDelete = temp;
			}
		}
		if(toDelete != null)
			nodes.remove(toDelete);
	}
	
//	public void addEdge(Vector2Int from, Vector2Int to, int id_) {
//		Node start = new Node(from, 0);
//		Node end = new Node(to, id_);
//		Edge e = new Edge(start, end);
//		for(Edge edge : edges) {
//			if(edge.equals(e))
//				return;
//		}
//			edges.add(e);
//	}	
	
	public void addEdge(Node from, Node to) {
		Edge e = new Edge(from, to);
		for(Edge edge : edges) {
			if(edge.equals(e))
				return;
		}
			edges.add(e);
	}	
		
	public ArrayList<Node> getNeighbouringNodes(Node n) {
//		System.out.print(this.toString());
		ArrayList<Node> neighbours = new ArrayList<Node>();
		for(Edge e : edges) {
			if(e.start.equals(n))
				if(!neighbours.contains(e.end))
					neighbours.add(e.end);
			else if(e.end.equals(n))
				if(!neighbours.contains(e.start))
					neighbours.add(e.start);
		}
		System.out.println(neighbours.size() + " <--");
		return neighbours;
	}
	
	public String toString() {
		String s = "Nodes: " + nodes.size() + "\n";
		for(Node n : nodes)
			s += n.toString() + "\n";
		s += "\n" + "Edges: " + edges.size() + "\n";
		for(Edge e : edges)
			s += e.toString() + "\n";
		return s;		
	}
}
