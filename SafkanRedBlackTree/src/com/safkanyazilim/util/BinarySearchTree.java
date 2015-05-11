package com.safkanyazilim.util;

import java.util.AbstractCollection;
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
 * <p>Note that this tree is not balanced in any way. This means the order
 * in which elements are inserted into the tree do matter for performance.
 * For instance, inserting elements in perfect increasing order will
 * make the most unbalanced tree possible, and performance will suffer.
 * </p>
 * 
 * @author Dr. Y. Safkan
 *
 * @param <E> The type to be stored in the binary search tree. 
 */
public class BinarySearchTree<E extends Comparable<E>> extends AbstractCollection<E> implements SearchTree<E> {
	
	/**
	 * Internal Node class, representing a node of a binary search tree.
	 * 
	 * @author Dr. Y. Safkan
	 */
	protected class Node {
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
		
		Node() {
			this(null, null, null);
		}
		
	}

	protected class TreeIterator implements Iterator<E> {
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
				this.next = BinarySearchTree.this.successor(this.next);
				return this.prev.element;
			}
		}

		/*
		 * Implementation note: Supporting this is not simple, because even if
		 * a node is "in the past", its removal may involve its successor (if
		 * it actually has two children). Here, we carefully navigate around
		 * that issue by seeing if the contained elements are identical (which
		 * can only happen on such a two-child-node removal); in which case
		 * we jump forward one more element (which we calculate beforehand since
		 * if the broken case has happened, navigation is no longer safe) to
		 * avoid iterating over the same element right after its removal.
		 */
		@Override
		public void remove() {
			if (this.prev == null) {
				throw new IllegalStateException();
			}
			
			Node successor = BinarySearchTree.this.successor(this.next);
			
			BinarySearchTree.this.delete(this.prev);
			
			if (this.next != null && this.next.element == this.prev.element) {
				this.next = successor;
			}
			
			this.prev = null;
			BinarySearchTree.this.size--;
			BinarySearchTree.this.modificationCount++;
			this.modificationCount++;
		}
		
	}

	/**
	 * The root node of the tree.
	 */
	protected Node root;
	
	/**
	 * The number of nodes in this tree.
	 */
	protected int size;
	
	/**
	 * The height of this tree.
	 */
	protected int height;
	
	/**
	 * The count of successful modification operations on this tree. This is used to keep track
	 * of modifications being done while an iterator is active.
	 */
	protected int modificationCount;
	
	/**
	 * The modification count at which the height was calculated last. If this
	 * is not equal to the modification count, the height is outdated and must
	 * be calculated.
	 */
	protected int heightCalculatedModificationCount;
	
	/**
	 * Construct a new BinarySearchTree, which is initially empty.
	 */
	public BinarySearchTree() {
		this.root = null;
		this.size = 0;
		this.height = 0;
		this.modificationCount = 0;
		this.heightCalculatedModificationCount = 0;
	}
	
	/**
	 * Construct a new BinarySearchTree, from an existing BinarySearchTree.
	 * 
	 * The resulting BinarySearchTree will be functionally equivalent, however
	 * it will be a balanced tree, and can be expected to perform better.
	 * 
	 * @param binarySearchTree the search tree whose content will be copied.
	 */
	public BinarySearchTree(BinarySearchTree<E> binarySearchTree) {
		// This works because a BinarySearchTree will spit out its elements
		// in the correct order with an iterator, and toArray uses that.
		Object[] elements = binarySearchTree.toArray();
		
		this.root = this.generateTreeFromSortedArrayRange(elements, 0, elements.length - 1);
		this.size = elements.length;
		this.modificationCount = 0;
		this.height = 0;
		this.heightCalculatedModificationCount = -1;
	}
	
	@SuppressWarnings("unchecked")
	private Node generateTreeFromSortedArrayRange(Object[] elements, int start, int end) {
		if (start > end) {
			return null;
		}
		
		Node node = new Node();
		
		int middle = (start + end)/2;
		node.element = (E)elements[middle];

		node.left = generateTreeFromSortedArrayRange(elements, start, middle - 1);
		node.right = generateTreeFromSortedArrayRange(elements, middle + 1, end);
		
		if (node.left != null) {
			node.left.parent = node;
		}
		
		if (node.right != null) {
			node.right.parent = node;
		}
		
		return node;
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
		
			return this.find(element) != null;
		} else {
			return false;
		}
	}

	/*
	 * This returns an iterator, which supports removing of elements. 
	 * 
	 * (non-Javadoc)
	 * @see java.util.AbstractCollection#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return new TreeIterator();
	}

	/*
	 * The default implementation throws UnsupportedOperationException!
	 * 
	 * So it is a case of must-implement.
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

	
	/*
	 * Remove an object. 
	 * 
	 * (non-Javadoc)
	 * @see java.util.AbstractCollection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		if (o instanceof Comparable<?>) {
			@SuppressWarnings("unchecked")
			Node node = this.find((E)o);
			
			if (node != null) {
				this.delete(node);
				this.size--;
				this.modificationCount++;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
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

	@Override
	public int height() {
		if (this.heightCalculatedModificationCount != this.modificationCount) {
			this.updateHeight();
			this.heightCalculatedModificationCount = this.modificationCount;
		}
		
		return this.height;
	}
	
	// =============== Protected Methods =============== 

	protected void updateHeight() {
		if (this.root == null) {
			this.height = 0;
		} else {
			this.updateHeightRecursively(this.root, 0);
		}
	}
	
	protected void updateHeightRecursively(Node node, int currentHeight) {
		if (currentHeight > this.height) {
			this.height = currentHeight;
		}
		
		if (node.left != null) {
			this.updateHeightRecursively(node.left, currentHeight + 1);
		}
		
		if (node.right != null) {
			this.updateHeightRecursively(node.right, currentHeight + 1);
		}
	}
	
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
	protected Node find(E element) {
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

	/**
	 * Delete a node from the tree. This method will not touch
	 * anything other than the tree structure.
	 * @param node the node to be deleted from the tree.
	 */
	
	protected void delete(Node node) {
		if (node.right == null && node.left == null) {
			this.deleteNodeWithNoChild(node);
		} else if (node.right == null ^ node.left == null) {
			this.deleteNodeWithOneChild(node);
		} else {
			this.deleteNodeWithTwoChildren(node); 
		}
	}
	
	/**
	 * Delete a node from the tree. The node must have no
	 * children.
	 * @param node the node with no children to be deleted.
	 */
	protected void deleteNodeWithNoChild(Node node) {
		if (node.parent == null) {
			this.root = null;
		} else if (node == node.parent.right) {
			node.parent.right = null;
		} else {
			node.parent.left = null;
		}
	}

	/**
	 * Delete a node from the tree. The node must have one
	 * and only one child.
	 * @param node the node with one child to be deleted.
	 */
	protected void deleteNodeWithOneChild(Node node) {
		Node child = node.right == null ? node.left : node.right;
		
		if (node.parent == null) {
			this.root = child;
			child.parent = null;
		} else if (node == node.parent.right) {
			node.parent.right = child;
			child.parent = node.parent;
		} else {
			node.parent.left = child;
			child.parent = node.parent;
		}
	}
	
	/**
	 * This handles the "hard case" of deleting a node, which is
	 * when the node has two children. The algorithm is to choose 
	 * either the predecessor or successor, swap values, and delete 
	 * that instead. Here, to avoid bias and due to lack of any 
	 * other information, predecessor-successor choice is done
	 * by a coin-flip.
	 * @param node the node with two children to be deleted.
	 */
	protected void deleteNodeWithTwoChildren(Node node) {
		Node replacement;
		
		replacement = Math.random() < 0.5 ? this.predecessor(node) : this.successor(node);
		
		node.element = replacement.element;

		/* Implementation note: This call looks recursive, but it is only so
		 * for one level; this can not come back here again.
		 * 
		 * The reason is that, if a node has two children, its successor or
		 * predecessor can not have two children since they are the right-subtree-minimum
		 * and left-subtree-maximum elements, respectively.
		 * 
		 * They could though, potentially have no children.
		 * 
		 * Putting in another method just for the removal of such a node does
		 * not make much sense. Hence the below call.
		 */
		
		this.delete(replacement); 
	}
	
	protected boolean insert(E element) {
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

	/**
	 * Find the minimum node in the subtree whose head is the
	 * given node.
	 * @param node the root of the subtree
	 * @return the minimal element in the subtree
	 */
	protected Node min(Node node) {
		while (node.left != null) {
			node = node.left;
		}
		
		return node;
	}

	/**
	 * Find the maximum node in the subtree whose head is the
	 * given node.
	 * @param node the root of the subtree
	 * @return the minimal element in the subtree.
	 */
	protected Node max(Node node) {
		while (node.right != null) {
			node = node.right;
		}
		
		return node;
	}

	
	/**
	 * <p>Given a node, find the successor node; meaning the node that is 
	 * one larger in value than the one given in the tree.
	 * </p>
	 * 
	 * <p>This is essentially used by the iterator to iterate 
	 * over the whole tree. Any "successor" operation is O(h), where
	 * h is the height of the tree. 
	 * </p> 
	 * 
	 * @param node
	 * @return
	 */
	protected Node successor(Node node) {
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

	/**
	 * <p>Given a node, find the predecessor node; meaning the node that is 
	 * one smaller in value than the one given in the tree.
	 * </p>
	 * 
	 * @param node
	 * @return
	 */
	protected Node predecessor(Node node) {
		if (node  == null) {
			return null;
		} else {
			if (node.left != null) {
				return this.max(node.left);
			} else {
				while (node.parent != null && node.equals(node.parent.left)) {
					node = node.parent;
				}
				
				return node.parent;
				
			}
		}
	}

	
}
