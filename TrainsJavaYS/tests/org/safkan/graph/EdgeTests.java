package org.safkan.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class EdgeTests {

	@Test
	public void testEdgeEquality() {
		Node A = new Node("A");
		Node B = new Node("B");
		
		Edge e1 = new Edge(A, B, 5.0);
		Edge e2 = new Edge(A, B, 7.0);
		
		assertTrue("Edges with same start-end nodes must be equal.", e1.equals(e2));
	}

}
