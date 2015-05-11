/**
 * 
 */
package com.safkanyazilim.util;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Dr. Y. Safkan
 *
 */
public class BinarySearchTreeTests {

	private BinarySearchTree<Integer> tree;

	private static BinarySearchTree<Integer> generateTree() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		
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
	
		return tree;
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.tree = generateTree(); 
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
	
	@Test
	public void testDelete() {
		assertEquals(14, this.tree.size());
		
		boolean removed = this.tree.remove(50);
		
		assertTrue(removed);
		
		removed = this.tree.remove(50);
		
		assertFalse(removed);
		
		assertEquals(13, this.tree.size());
	}
	
	@Test
	public void testConcurrentModificationException() {
		try {
			for (Integer i : this.tree) {
				if (i == 50) {
					this.tree.remove(6);
				}
			}
			
			fail();
		} catch (ConcurrentModificationException e) {
			
		}
	}
	
	@Test 
	public void testIteratorRemove() {
		Iterator<Integer> iterator = this.tree.iterator();
		
		while (iterator.hasNext()) {
			Integer element = iterator.next();
			
			if (element == 50) {
				iterator.remove(); 
			}
			
		}
	
		assertEquals(13, this.tree.size());
		
	}

	@Test
	public void testIteratorRemoveAll() {
		Iterator<Integer> iterator = this.tree.iterator();
		
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove(); 
		}
	
		assertEquals(0, this.tree.size());
		
	}

	@Test
	public void testRemove12() {
		
		assertTrue(this.tree.contains(12));
		assertTrue(this.tree.remove(12));
		assertFalse(this.tree.contains(12)); 
	}
	
	@Test
	public void testExhaustiveRemove() {
		BinarySearchTree<Integer> fixedTree = generateTree();
		
		for (Integer i : fixedTree) {
			BinarySearchTree<Integer> tree = generateTree();
			
			assertTrue(tree.contains(i));
			
			assertTrue(tree.remove(i));
			
			assertFalse(tree.contains(i));
			
			for (Integer j : tree) {
				assertNotEquals(i, j);
 			}
		}
	}
	
	@Test
	public void testCopyConstructor() {
		BinarySearchTree<Integer> balanced = new BinarySearchTree<Integer>(this.tree);
		
		assertEquals(14, balanced.size()); 
		
		int count = 0;
		
		for (Integer j : balanced) {
			count++;
		}
		
		assertEquals(14, count);
	}
	
	
}
