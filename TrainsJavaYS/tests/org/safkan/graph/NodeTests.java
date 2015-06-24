package org.safkan.graph;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class NodeTests {

	@Test
	public void testNodeEquality() {
		Node A1 = new Node("A");
		Node A2 = new Node("A");
		
		assertTrue("Two nodes with same identifier should test equal.", A1.equals(A2));
	}

	@Test
	public void testNodeHashCode() {
		Node A1 = new Node("A");
		Node A2 = new Node("A");

		assertTrue("Two nodes with same identifier should generate same hashCode", A1.hashCode() == A2.hashCode());
	}
	
	@Test
	public void testSetPresence() {
		Node A1 = new Node("A");
		Node A2 = new Node("A");

		Set<Node> set = new HashSet<Node>();
		
		set.add(A1);
		
		assertTrue(set.contains(A1));
		assertTrue(set.contains(A2));
	}
	
	@Test
	public void testNullIdentifier() {
		try {
			Node A = new Node(null);
			fail("Should have thrown IllegalArgumentException: " + A); 
		} catch (IllegalArgumentException e) {
			
		}
	}
}
