package com.safkanyazilim.util;

import java.util.Set;

/**
 * <p> This is the interface definition for a binary search tree.</p>
 * 
 * <p> As binary search trees do not allow duplicate elements in the 
 * tree, they do obey set semantics. Also, since elements placed in
 * the tree must have a well-defined ordering, they must also implement
 * Comparable<>. 
 * </p>
 * 
 * <p> In addition, there are a few binary search tree functions that
 * are defined here.</p>
 * 
 * @author Dr. Y. Safkan
 *
 * @param <E> the element type of the tree.
 */

public interface SearchTree<E extends Comparable<E>> extends Set<E> {
	
	/**
	 * Returns the minimum element in the tree. This takes O(h) time, where
	 * h is the height of the tree.
	 * @return the minimum element in the tree.
	 */
	public E minimum();
	
	/**
	 * Returns the maximum element in the tree. This takes O(h) time, where
	 * h is the height of the tree.
	 * @return the maximum element in the tree.
	 */
	public E maximum();
	
	/**
	 * Calculates and returns the current height of the tree. This, can potentially
	 * take O(N) time, where N is the number of elements in the tree. However,
	 * this is implementation dependent.
	 * @return the height of the tree.
	 */
	public int height();
}
