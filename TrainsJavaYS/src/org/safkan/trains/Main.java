package org.safkan.trains;

import java.util.List;

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
		//[C][E][B][C][D][E][B][C]
		Path path = new Path(C, D, E, B, C, E, B, C);
		
		TraversedPath traversedPath = compiledGraph.traverse(path);
		
		if (traversedPath == null) {
			System.out.println("No path");
		} else {
			System.out.println("Path length: " + traversedPath.getTotalDistance()); 
			System.out.println("Path: " + traversedPath);
		}
		
		int count = compiledGraph.countPaths(C, C, null, null, 30.0);
		
		System.out.println("Path count:" + count);
		
		Double distance = compiledGraph.findLengthOfShortestPath(D,  D);
		
		System.out.println("Shortest path distance:" + distance);
		
	}

}
