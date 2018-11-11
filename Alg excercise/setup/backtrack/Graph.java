import java.util.Comparator;

public class Graph {
	private Vertex[] vertices;
	String path;
	Path currentPath = new Path();
	Path bestPath = new Path();
	Vertex begin;
	Vertex dest;
	double inf = Double.POSITIVE_INFINITY;
	int list_count=0;

	public Graph() {
		this.path = "";
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public void setVertices(Vertex[] vertexList) {
		this.vertices = vertexList;
	}

	public void addToList(Vertex v) {
		this.vertices[list_count]=v;
		list_count++;
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

	public Path backtrack(int start, int end) {
		begin = vertices[start];
		dest = vertices[end];

		currentPath.addToPath(new AdjListNode(begin.getIndex(), 0));
		begin.visited = true;
		recursive(1);
		for (AdjListNode node : bestPath.getPath()) {
			path = path + " " + Integer.toString(node.getVertexIndex());
		}
		
		return bestPath;
	}

	public void recursive(int n) {
		Vertex peek = vertices[currentPath.peek().getVertexIndex()];
		for (AdjListNode node : peek.getAdjList()) {
			Vertex v = vertices[node.getVertexIndex()];
			if (!v.isVisited()) {
				currentPath.addToPath(node);
				v.visited = true;

				if (currentPath.getPathDistance() < bestPath.getPathDistance()) {
					if (v.getIndex() == dest.getIndex()) {
						bestPath.copyPath(currentPath);
					} else {
						recursive(n+1);
					}
				}
				currentPath.removeFromPath();
				v.visited = false;
			}

		}
	}

}