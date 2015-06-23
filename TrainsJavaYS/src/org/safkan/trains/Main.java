package org.safkan.trains;


import org.safkan.graph.CompiledGraph;
import org.safkan.graph.Edge;
import org.safkan.graph.Graph;
import org.safkan.graph.Node;
import org.safkan.graph.Path;
import org.safkan.graph.TraversedPath;

public class Main {

	public static void main(String[] args) {
		Graph graph = new Graph(true, true);
		
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(C);
		graph.addNode(D);
		graph.addNode(E);

		graph.addEdge(new Edge(A, B, 5));
		graph.addEdge(new Edge(B, C, 4));
		graph.addEdge(new Edge(C, D, 8));
		graph.addEdge(new Edge(D, C, 8));
		graph.addEdge(new Edge(D, E, 6));
		graph.addEdge(new Edge(A, D, 5));
		graph.addEdge(new Edge(C, E, 2));
		graph.addEdge(new Edge(E, B, 3));
		graph.addEdge(new Edge(A, E, 7));
		
		
		CompiledGraph compiledGraph = graph.compile();
		
		Path path = new Path(A, E, B, C, D);
		
		TraversedPath traversedPath = compiledGraph.traverse(path);
		
		if (traversedPath == null) {
			System.out.println("No path");
		} else {
			System.out.println("Path length: " + traversedPath.getTotalDistance()); 
		}
		
	}

}
