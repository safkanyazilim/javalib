/**
 * 
 */
package com.safkanyazilim.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Dr. Y. Safkan
 *
 */
public class BinarySearchTreeTests {

	private BinarySearchTree<Integer> tree;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.tree = new BinarySearchTree<>();
		
		tree.add(50);
		tree.add(25);
		tree.add(12);
		tree.add(6);
		tree.add(9);
		tree.add(40);
		tree.add(30);
		tree.add(45);
		tree.add(75);
		tree.add(60);
		tree.add(55);
		tree.add(85);
		tree.add(80);
		tree.add(99);
	}

	@Test
	public void testSize() {
		assertEquals(14, tree.size());
	}
	
	@Test
	public void testMinimum() {
		assertEquals(6, tree.minimum().intValue());
	}

	@Test
	public void testMaximum() {
		assertEquals(99, tree.maximum().intValue());
	}
	
	@Test 
	public void testIteration() {
		int sum = 0;
		int count = 0;
		
		for (Integer i : this.tree) {
			sum += i;
			count++;
		}
		
		assertEquals(671, sum);
		assertEquals(14, count);
	}
	
	@Test 
	public void testOrderedWalk() {
		int previous = Integer.MIN_VALUE;
		
		for (Integer i : this.tree) {
			assertTrue(previous < i);
			previous = i;
		}
	}
}
