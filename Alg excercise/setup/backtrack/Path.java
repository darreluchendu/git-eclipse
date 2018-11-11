import java.util.Stack;

public class Path {
	private Stack<AdjListNode> path;
	private int distance;

	public Path() {
		this.path = new Stack<AdjListNode>();
		this.distance = 0;
	}

	public void copyPath(Path p) {
		path = (Stack<AdjListNode>) p.path.clone();
		distance = p.distance;
	}

	public void addToPath(AdjListNode n) {
		this.path.push(n);
		distance = getPathDistance();
	}

	public void removeFromPath() {
		this.path.pop();
		distance = getPathDistance();
	}

	public int getDistance() {
		return distance;

	}

	public int getPathDistance() {
		if (!path.isEmpty()) {
			int dist = 0;
			for (AdjListNode node : path) {

				dist += node.getWeight();
				distance = dist;
			}

		} else {
			double inf = Double.POSITIVE_INFINITY;
			return (int) inf;
		}
		return this.distance;
	}

	public AdjListNode peek() {
		return path.peek();
	}

	public Stack<AdjListNode> getPath() {
		return path;
	}

}
