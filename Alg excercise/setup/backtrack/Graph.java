import java.util.ArrayList;
import java.util.Comparator;

public class Graph {
	private ArrayList<Vertex> vertices;
	String path;
	Path currentPath = new Path();
	Path bestPath = new Path();
	Vertex begin;
	Vertex dest;
	double inf = Double.POSITIVE_INFINITY;
	int list_count=0;

	public Graph() {
		this.path = "";
		this.vertices=new ArrayList<Vertex>();
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertex> vertexList) {
		this.vertices = vertexList;
	}

	public void addToList(Vertex v) {
		this.vertices.add(v);
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
		begin = vertices.get(start);
		dest = vertices.get(end);

		currentPath.addToPath(new AdjListNode(begin.getIndex(), 0));
		begin.visited = true;
		recursive(1);
		for (AdjListNode node : bestPath.getPath()) {
			path = path + " " + Integer.toString(node.getVertexIndex());
		}
		
		return bestPath;
	}

	public void recursive(int n) {
		Vertex peek = vertices.get(currentPath.peek().getVertexIndex());
		for (AdjListNode node : peek.adjList()) {
			Vertex v = vertices.get(node.getVertexIndex());
			if (!v.visited) {
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
