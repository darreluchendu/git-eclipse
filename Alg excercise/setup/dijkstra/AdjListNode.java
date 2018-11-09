public class AdjListNode {
	
	private int vertexIndex;
	private int weight;

	public AdjListNode(int n, int weight){
		this.vertexIndex = n;
		this.weight = weight;
	}
	
	public int getVertexIndex(){
		return vertexIndex;
	}
	
	@Override
	public String toString() {
		return "AdjListNode [vertexIndex=" + vertexIndex +", weight="+weight+ "]";
	}

	public void setVertexIndex(int n){
		vertexIndex = n;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public void setWeight(int w){
		weight = w;
	}
	
}