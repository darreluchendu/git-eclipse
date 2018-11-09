import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;


public class Graph {
	private ArrayList<Vertex> vertices;
	double inf = Double.POSITIVE_INFINITY;

	// private int size;

	public Graph() {
		// this.size = 0;
		this.vertices = new ArrayList<Vertex>();
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

//	public static boolean checkAdjacency(String s1, String s2) {
//		int countDiff = 0;
//		for (int i = 0; i < s1.length(); i++) {
//			if (s1.charAt(i) != s2.charAt(i))
//				countDiff++;
//			if (countDiff > 1)
//				return false;
//		}
//		return true;
//	}

//	public void doDijkstra(String start, String end) {
//		//Vertex end = dijkstra(start, end);
//		if (end == null){
//			System.out.println(start + " " + end + "\nNo ladder is possible!");
//			return;
//		}
//
//		Stack<String> path = new Stack<String>();
//		
//		int currIndex = end.getIndex();
//		int predecessorIndex = end.getPredecessor();
//		String output = start + " " + end + "\nTotal weight " + end.getDistance();
//
//		while (currIndex != predecessorIndex)
//		{
//			Vertex current = vertices.get(currIndex);
//			path.push(current.getWord());
//			currIndex = predecessorIndex;
//			predecessorIndex = vertices.get(currIndex).getPredecessor();
//		}
//		
//		Vertex current = vertices.get(currIndex);
//		path.push(current.getWord());
//		
//		String pathTrase = "";
//		while (path.size() != 0){
//			if (path.size() > 1)
//				pathTrase += path.pop() + "->";
//			else
//				pathTrase += path.pop();
//		}
//		System.out.println(output + "\n" + pathTrase);
//		
//	}
//	
	public class vertCompare implements Comparator<Vertex>
	{
	    @Override
	    public int compare(Vertex x, Vertex y)
	    {
	        if (x.getDistance() < y.getDistance())
	        {
	            return -1;
	        }
	        if (x.getDistance() > y.getDistance())
	        {
	            return 1;
	        }
	        return 0;
	    }
	}
	public void updateDistances(PriorityQueue<Vertex> queue1,LinkedList<AdjListNode> list1){
	for (AdjListNode node : list1) {
		Vertex w = vertices.get(node.getVertexIndex());	
		if (node.getWeight()==0) {
			w.setDistance((int) inf);
		}else {
			w.setDistance(node.getWeight());
		}
		queue1.add(w);
	}
	public PriorityQueue<Vertex> dijkstra(int start, int end) {
		Vertex begin = vertices.get(start);
		Vertex dest = vertices.get(end);
		
		if (begin ==null || dest ==null)
			return null;
		
		Comparator<Vertex> comparator = new vertCompare();
		
		
		
		begin.setVisited(true);
		begin.setDistance(0); // initialize distance
		begin.setPredecessor(begin.getIndex()); // v was initial/starting
													// vertex
	
		Stack<Vertex> S = new Stack<Vertex>();
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>(128, comparator);
		
		
		LinkedList<AdjListNode> list =begin.getAdjList();
		updateDistances(queue, list);
	
		}
		while (!queue.isEmpty()) { // while vertices to process
			
			S.push(queue.remove());
			for (AdjListNode node : list) { // go through the adjacency list...
				Vertex w = vertices.get(node.getVertexIndex());				
				
					w.setDistance(u.getDistance() + node.getWeight());
					queue.add(w); 
				
				if (w.equals(dest)) {
					return queue;
				}
				
				}
			S.push(queue.remove());
			}
		
		
		return queue;
	}

}
//while (!queue.isEmpty()) { // while vertices to process
//	Vertex u = queue.remove(); // get next vertex to process
//	LinkedList<AdjListNode> list = u.getAdjList(); // get adjacency list of the vertex
//	
//	for (AdjListNode node : list) { // go through the adjacency list...
//		if (node.getWeight()!=0) {
//		Vertex w = vertices.get(node.getVertexIndex());				
//		if (!w.isVisited()) { // ...for vertices that have not been
//								// visited
//			w.setVisited(true); // they have now been visited
//			w.setPredecessor(u.getIndex());
//			w.setDistance(u.getDistance() + node.getWeight());
//			queue.add(w); 
//		}
//		if (w.equals(dest))
//			return queue;