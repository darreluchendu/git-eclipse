import java.util.LinkedHashSet;

public class Vertex {
	
	private LinkedHashSet<AdjListNode> adjList; //list with adjacent vertices
	private int index; // the index of the vertex in the graph

	boolean visited; 
    int distance; 

	public Vertex(int n){
		this.adjList = new LinkedHashSet<AdjListNode>();
		this.index = n;
		this.visited = false;

		this.distance = -1;
	}
	
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public LinkedHashSet<AdjListNode> getAdjList() {
		return adjList;
	}

	public void setAdjList(LinkedHashSet<AdjListNode> adjList) {
		this.adjList = adjList;
	}
	
//	public void getAdjList(LinkedHashSet<AdjListNode> adjList) {
//		this.adjList = adjList;
//	}


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean b) {
		this.visited = b;
	}

	public void addToAdjList(int n, int weight){
        adjList.add(new AdjListNode(n, weight));
    }
	
}