package org.safkan.graph;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class GraphTests {

	private Node A;
	private Node B;
	private Node C;
	private Node D;
	private Node E;
	
	@Before
	public void setUp() throws Exception {
		this.A = new Node("A");
		this.B = new Node("B");
		this.C = new Node("C");
		this.D = new Node("D");
		this.E = new Node("E");
	}

	@Test
	public void testGraphNodeAdd() {
		Graph graph = new Graph();
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(C);
		graph.addNode(D);
		graph.addNode(E);
		
		Set<Node> nodes = graph.getNodes();
		
		assertTrue(nodes.contains(A));
		assertTrue(nodes.contains(B));
		assertTrue(nodes.contains(C));
		assertTrue(nodes.contains(D));
		assertTrue(nodes.contains(E));
		
	}

	@Test
	public void testGraphEdgeAdd() {
		Graph graph = new Graph();
		graph.addEdge(new Edge(A, B, 1.0));
		graph.addEdge(new Edge(A, C, 2.0));
		graph.addEdge(new Edge(D, E, 3.0));
		
		Set<Node> nodes = graph.getNodes();
		
		assertTrue(nodes.contains(A));
		assertTrue(nodes.contains(B));
		assertTrue(nodes.contains(C));
		assertTrue(nodes.contains(D));
		assertTrue(nodes.contains(E));
	}

	
}
