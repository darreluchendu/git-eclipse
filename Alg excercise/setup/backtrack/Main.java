import java.io.*;
import java.util.*;

/**
 * program to find shortest path using Dijkstra's algorithm
 */
public class Main {

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();

		String inputFileName = args[0]; // input file name

		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);

		// read in the data here and create graph here
		int num_v = 0;
		int row_num = 0;
		String line = in.nextLine();
		int begin = 0;
		int dest = 0;
		ArrayList<Vertex> vertexList=new ArrayList<Vertex>();
		while (line != "") {
			
			Vertex vert = new Vertex(row_num - 1);
			String string_arr[] = line.split(" ");
			
			if (row_num == 0) {
				num_v = Integer.parseInt(line);
				row_num++;
				line = in.nextLine();
				continue;
				
			} else if (row_num == num_v + 1) {
				// converts char to int
				begin = Integer.parseInt(string_arr[0]);
				dest = Integer.parseInt(string_arr[1]);
				break;

			} else {

				for (int i = 0; i < num_v; i++) {

					if (vert.getIndex() != i && Integer.parseInt(string_arr[i]) != 0) {
						vert.addToAdjList(i, Integer.parseInt(string_arr[i]));
					}
				}
			}
			vertexList.add(vert);
			row_num++;
			line = in.nextLine();
		}

		reader.close();
		in.close();

		// do the work here
		Graph g = new Graph();
		g.setVertices(vertexList);
		Path p = g.backtrack(begin, dest);
		String path = g.path;
		if (p.getPathDistance() == Integer.MAX_VALUE) {
			System.out.printf("There is no path from vertex %d to %d", begin, dest);
		} else {
			System.out.printf("Shortest distance from vertex %d to vertex %d is %d", begin, dest, p.getPathDistance());
			System.out.println("\nShortest path:" + path);
		}
		// end timer and print total time
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");

	}

}
