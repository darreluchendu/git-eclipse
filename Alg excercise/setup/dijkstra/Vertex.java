import java.util.LinkedList;

public class Vertex {
	
	private LinkedList<AdjListNode> adjList; //list with adjacent vertices
	private int index; // the index of the vertex in the graph

    int predecessor; 
    int distance; 

	
	public Vertex(int n){
		this.adjList = new LinkedList<AdjListNode>();
		this.index = n;
		this.predecessor = -1;
		this.distance = -1;
	}
	
	public Vertex (Vertex v){
		this.predecessor = v.getPredecessor();
		this.distance = v.getDistance();
		this.adjList = v.getAdjList();
    	this.index = v.getIndex();
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

	public int getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(int n) {
		this.predecessor = n;
	}

	public void addToAdjList(int n, int weight){
        adjList.addLast(new AdjListNode(n, weight));
    }
}