public class AdjListNode {

	private int vertexIndex;
	private int weight;

	public AdjListNode(int n, int weight) {
		this.vertexIndex = n;
		this.weight = weight;
	}

	public int getVertexIndex() {
		return vertexIndex;
	}

	public void setVertexIndex(int n) {
		vertexIndex = n;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int w) {
		weight = w;
	}

}