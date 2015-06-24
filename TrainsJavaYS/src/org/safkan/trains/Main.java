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
		
		TraversedPath path1 = compiledGraph.traverse(new Path(A, B, C));
		System.out.println("Output #1: " +  (path1 == null ? "NO SUCH ROUTE" : path1.getTotalDistance()));

		TraversedPath path2 = compiledGraph.traverse(new Path(A, D));
		System.out.println("Output #2: " +  (path2 == null ? "NO SUCH ROUTE" : path2.getTotalDistance()));

		TraversedPath path3 = compiledGraph.traverse(new Path(A, D, C));
		System.out.println("Output #3: " +  (path3 == null ? "NO SUCH ROUTE" : path3.getTotalDistance()));

		TraversedPath path4 = compiledGraph.traverse(new Path(A, E, B, C, D));
		System.out.println("Output #4: " +  (path4 == null ? "NO SUCH ROUTE" : path4.getTotalDistance()));

		TraversedPath path5 = compiledGraph.traverse(new Path(A, E, D));
		System.out.println("Output #5: " +  (path5 == null ? "NO SUCH ROUTE" : path5.getTotalDistance()));

		int count1 = compiledGraph.countPaths(C, C, null, 3, null);
		System.out.println("Output #6: " + count1);

		int count2 = compiledGraph.countPaths(A,  C, 4	, 4, null);
		System.out.println("Output #7: " + count2);
		
		Double distance1 = compiledGraph.findLengthOfShortestPath(A,  C);
		System.out.println("Output #8: " + (distance1 == null ? "NO SUCH ROUTE" : distance1));
		
		Double distance2 = compiledGraph.findLengthOfShortestPath(C,  C);
		System.out.println("Output #9: " + (distance2 == null ? "NO SUCH ROUTE" : distance2));
	
		int count3 = compiledGraph.countPaths(C, C, null, null, 30.0);
		System.out.println("Output #10: " + count3);
	}

}
