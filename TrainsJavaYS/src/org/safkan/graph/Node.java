package org.safkan.graph;

/**
 * <p>
 * External immutable representation of a node. A node in this case 
 * is only a placeholder; it contains only an identity as a string 
 * for reference purposes.
 * </p>
 * 
 * <p>
 * 
 * @author Dr. Y. Safkan &lt;safkan@gmail.com&gt;
 *
 */

public class Node {
	private String id;
	
	/**
	 * Construct a new node with the given id. The id may not be null.
	 * @param id the id of the node to be created.
	 * @throws IllegalArgumentException if id is null.
	 */
	public Node (String id) {
		if (id == null) {
			throw new IllegalArgumentException("The Node id may not be null.");
		}
		
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node other = (Node)obj;
			
			return this.id.equals(other.id);
		} else {
			return false;
		}
		
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
	@Override
	public String toString() {
		return "[" + this.id + "]";
	}
}
