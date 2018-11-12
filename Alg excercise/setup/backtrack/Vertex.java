import java.util.LinkedList;

public class Vertex {
	
	private LinkedList<AdjListNode> adjList; //list with adjacent vertices
	private int index; // the index of the vertex in the graph

	boolean visited; 
    int distance; 

	public Vertex(int n){
		this.adjList = new LinkedList<AdjListNode>();
		this.index = n;
		this.visited = false;

		this.distance = -1;
	}
	
	public Vertex (Vertex v){
		this.distance = v.getDistance();
		this.adjList = v.getAdjList();
    	this.index = v.getIndex();
    	this.visited = v.isVisited();
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public LinkedList<AdjListNode> getAdjList() {
		return adjList;
	}

	public void setAdjList(LinkedList<AdjListNode> adjList) {
		this.adjList = adjList;
	}

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
        adjList.addLast(new AdjListNode(n, weight));
    }
	
}