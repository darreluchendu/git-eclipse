import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Graph {
	private ArrayList<Vertex> vertices;
	String path;
	double inf = Double.POSITIVE_INFINITY;

	public Graph() {
		this.vertices = new ArrayList<Vertex>();
		this.path = "";
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}

	public void addToList(Vertex v) {
		this.vertices.add(v);
	}

	public class vertCompare implements Comparator<Vertex> {
		@Override
		public int compare(Vertex x, Vertex y) {
			if (x.getDistance() < y.getDistance()) {
				return -1;
			}
			if (x.getDistance() > y.getDistance()) {
				return 1;
			}
			return 0;
		}
	}

	public int dijkstra(int start, int end) {
		for (Vertex v : vertices) {
			v.setDistance((int) inf);
		}
		Comparator<Vertex> comparator = new vertCompare();

		Stack<Vertex> settled = new Stack<Vertex>();
		PriorityQueue<Vertex> unsettled = new PriorityQueue<Vertex>(128, comparator);

		Vertex begin = vertices.get(start);
		Vertex dest = vertices.get(end);

		if (begin == null || dest == null)
			return 0;

		unsettled.add(begin);
		begin.setDistance(0);
		begin.setPredecessor(-1);

		while (!unsettled.isEmpty()) { // while vertices to process
			Vertex current = unsettled.remove();
			settled.push(current);
			if (current.getIndex() == dest.getIndex()) {
				int currentPath = current.getIndex();
				while (currentPath != -1) {
					int predecessor = vertices.get(currentPath).getPredecessor();
					path = " " + Integer.toString(currentPath) + path;
					currentPath = predecessor;
				}
				return current.getDistance();
			}
			LinkedList<AdjListNode> list = current.getAdjList();
			for (AdjListNode node : list) { // go through the adjacency list...

				Vertex w = vertices.get(node.getVertexIndex());
				if (!settled.contains(w)) {
					int newDist = node.getWeight() + current.getDistance();
					if (newDist < w.getDistance()) {
						w.setDistance(newDist);
						unsettled.add(w);
						w.setPredecessor(current.getIndex());
					}

				}

			}
		}
		return -1;
	}
}