package com.safkanyazilim.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * <p> This is an implementation of a Binary Search Tree. The objects are
 * directly stored in a binary search tree, so there are no keys or values;
 * the objects themselves must implement Comparable, and as such have
 * a well-defined ordering. 
 * </p>
 * 
 * <p> If this is used for a class which does not obey the equals() and Comparable
 * semantics, the results are undefined. 
 * </p>
 * 
 * <p> Null elements are not allowed, trying to add a null value will result
 * in a NullPointerException. Duplicate elements are also not allowed;
 * trying to add an existing element into the tree will fail (however not
 * result in an exception).
 * </p>
 * 
 * <p> Note that this follows the semantics of a set, and can be used as a 
 * Set implementation. The additional bonus is that the elements also have
 * a well-defined order.
 * </p>
 * 
 * 
 * @author Dr. Y. Safkan
 *
 * @param <E> The type to be stored in the binary search tree. 
 */
public class BinarySearchTree<E extends Comparable<E>> extends AbstractCollection<E> implements SearchTree<E> {
	
	/**
	 * 
	 * Internal Node class, representing a node of a binary search tree.
	 * 
	 * @author Dr. Y. Safkan
	 */
	
	private class Node {

		/**
		 * This is the actual external element stored in this node.
		 */
		private E element;
		/**
		 * The left child of this node.
		 */
		private Node left;
		/**
		 * The right child of this node.
		 */
		private Node right;
		/**
		 * The parent node of this node.
		 */
		private Node parent;
		
		Node(Node parent, Node left, Node right) {
			this.parent = parent;
			this.left = left;
			this.right = right;
		}
		
		Node(Node parent) {
			this(parent, null, null);
		}
		
	}

	private class TreeIterator implements Iterator<E> {
		
		private Node next;
		private Node prev;
		private int modificationCount;
		
		public TreeIterator() {
			this.next = BinarySearchTree.this.min(BinarySearchTree.this.root);
			this.prev = null;
			this.modificationCount = BinarySearchTree.this.modificationCount;
		}
		
		@Override
		public boolean hasNext() {
			return this.next != null;
		}
		
		@Override
		public E next() {
			if (this.next == null) {
				throw new NoSuchElementException();
			} else if (this.modificationCount != BinarySearchTree.this.modificationCount) {
				throw new ConcurrentModificationException();
			} else {
				this.prev = this.next;
				this.next = BinarySearchTree.this.next(this.next);
				return this.prev.element;
			}
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

	/**
	 * The root node of the tree.
	 */
	private Node root;
	/**
	 * The number of nodes in this tree.
	 */
	private int size;
	/**
	 * The count of successful modification operations on this tree. This is used to keep track
	 * of modifications being done while an iterator is active.
	 */
	private int modificationCount;
	
	/**
	 * Construct a new BinarySearchTree, which is initially empty.
	 */
	
	public BinarySearchTree() {
		this.root = null;
		this.size = 0;
		this.modificationCount = 0;
	}
	
	@Override
	public int size() {
		return this.size;
	}

	/*
	 * True and necessary override. The default implementation in AbstractCollection
	 * iterates over the whole collection, taking O(N) time, while the implementation
	 * below takes O(h) time where h is the height of tree.
	 * 
	 * (non-Javadoc)
	 * @see java.util.AbstractCollection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		if (o instanceof Comparable<?>) {
			@SuppressWarnings("unchecked")
			E element = (E)o;
		
			return this.find(element) == null;
		} else {
			return false;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new TreeIterator();
	}

	/*
	 * The default implementation throws UnsupportedOperationException!
	 * 
	 * (non-Javadoc)
	 * @see java.util.AbstractCollection#add(java.lang.Object)
	 */

	@Override
	public boolean add(E e) {
		Objects.requireNonNull(e, "Null elements not allowed.");
		
		boolean success = this.insert(e);
		
		if (success) {
			this.modificationCount++;
			this.size++;
		}
		
		return success;
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean modified = super.addAll(c);
		
		if (modified) {
			this.modificationCount++;
		}
		
		return modified;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		this.root = null;
		this.size = 0;
		this.modificationCount++;
	}

	@Override
	public E minimum() {
		if (this.root == null) {
			return null;
		} else {
			return this.min(this.root).element;
		}
	}

	@Override
	public E maximum() {
		if (this.root == null) {
			return null;
		} else {
			return this.max(this.root).element;
		}
	}

	
	// =============== Private Methods =============== 
	



	/**
	 * Find a node whose contained element is equal to
	 * the one we are looking for.
	 * 
	 * This works iteratively, so should not be using
	 * stack space, and should function even with very deep
	 * trees.
	 * 
	 * @param node The node to start the search at.
	 * @param element the element we are seeking.
	 * @return The node which contains given element, or null.
	 */
	private Node find(E element) {
		Node current = this.root;
		
		while (current != null) {
			int comparison = current.element.compareTo(element);

			if (comparison == 0) {
				return current;
			} else if (comparison > 0) {
				// The comparison value being positive means
				// the element in the tree is greater than the
				// one we are looking for. So, the value we 
				// are seeking is less than the value of the
				// current node. So, we must go left.
				current = current.left;
			} else {
				// Only three states possible, so we must go right.
				current = current.right;
			}
		}

		return null;
	}

	private boolean insert(E element) {
		if (this.root == null) {
			this.root = new Node(null);
			this.root.element = element;
			return true;
		} else {
			Node current = this.root;
			
			while (true) {
				int comparison = current.element.compareTo(element);
			
				if (comparison == 0) {
					return false;
				} else if (comparison > 0) {
					if (current.left == null) {
						current.left = new Node(current);
						current.left.element = element;
						return true;
					} else {
						current = current.left;
					}
				} else {
					if (current.right == null) {
						current.right = new Node(current);
						current.right.element = element;
						return true;
					} else {
						current = current.right;
					}
				}
			}
		}
			
			
			
		
	}
	
	private Node min(Node node) {
		while (node.left != null) {
			node = node.left;
		}
		
		return node;
	}

	private Node max(Node node) {
		while (node.right != null) {
			node = node.right;
		}
		
		return node;
	}

	
	private Node next(Node node) {
		if (node  == null) {
			return null;
		} else {
			if (node.right != null) {
				return this.min(node.right);
			} else {
				while (node.parent != null && node.equals(node.parent.right)) {
					node = node.parent;
				}
				
				return node.parent;
				
			}
		}
	}
	
}
