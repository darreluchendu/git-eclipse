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
		distance = n.getWeight()+distance;
	}

	public void removeFromPath() {
		distance = distance-(this.path.pop().getWeight());
	}

	public int getPathDistance() {
		if (!path.isEmpty()) {
			return distance;

		} else {
			double inf = Double.POSITIVE_INFINITY;
			return (int) inf;
		}
	}

	public AdjListNode peek() {
		return path.peek();
	}

	public Stack<AdjListNode> getPath() {
		return path;
	}

}