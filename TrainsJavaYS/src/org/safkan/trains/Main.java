package org.safkan.trains;

import org.safkan.graph.CompiledGraph;
import org.safkan.graph.Graph;
import org.safkan.graph.Node;
import org.safkan.graph.Path;
import org.safkan.graph.TraversedPath;

public class Main {

	public static void main(String[] args) throws Exception {
		
		if (args.length < 1) {
			System.out.println("You must supply an input filename for the graph.");
			System.exit(1);
		}
		
		Graph graph = GraphLoader.loadGraph(args[0]);
		
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		
		CompiledGraph compiledGraph = graph.compile();
		
		Path path = new Path(A, E, B, C, D);
		
		TraversedPath traversedPath = compiledGraph.traverse(path);
		
		if (traversedPath == null) {
			System.out.println("No path");
		} else {
			System.out.println("Path length: " + traversedPath.getTotalDistance()); 
			System.out.println("Path: " + traversedPath);
		}
		
	}

}
