package org.safkan.graph;

/**
 * <p>
 * External immutable representation of an edge. 
 * </p>
 * 
 * <p>
 * This edge will not accept negative distances;
 * although this is not strictly required, it makes physical sense. (For instance Dijkstra's 
 * algorithm will work fine as long as there are no loops with total negative distance. But
 * having everything non-negative is really on the safe side.
 * </p>
 * 
 * @author Dr. Y. Safkan &lt;safkan@gmail.com&gt;
 *
 */

public class Edge {
	private Node from;
	private Node to;
	private double distance;
	
	/**
	 * Create a new edge from Node <code>from</code> to Node <code>to</code>.
	 * @param from the starting node for this edge. May not be null.
	 * @param to the ending node for this edge. May not be null.
	 * @param distance the distance of this edge. May not be negative.
	 * @throws IllegalArgumentException if from or to is null, or distance is negative.
	 */
	public Edge(Node from, Node to, double distance) {
		if (from == null) {
			throw new IllegalArgumentException("From node may not be null.");
		}
		
		if (to == null) {
			throw new IllegalArgumentException("To node may not be null.");
		}
		
		if (distance < 0.0) {
			throw new IllegalArgumentException("Negative distance is not admissiable for a graph edge. We have distance: " + distance);
		}
		
		this.from = from;
		this.to = to;
		this.distance = distance;
	}
	
	/**
	 * Getter for the starting node, <code>from</code>
	 * @return the starting node.
	 */
	public Node getFrom() {
		return this.from;
	}
	
	/**
	 * Getter for the ending node, <code>to</code>
	 * @return the ending node.
	 */
	public Node getTo() {
		return this.to;
	}
	
	/**
	 * Getter for the distance of this edge.
	 * @return the distance.
	 */
	public double getDistance() {
		return this.distance;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge) {
			Edge other = (Edge)obj;
			
			return this.from.equals(other.from) && this.to.equals(other.to);
		} else {
			return false;
		}
	
	}
	
	@Override
	public int hashCode() {
		return this.from.hashCode() * 19 + this.to.hashCode() * 41;
	}
	
	@Override
	public String toString() {
		return this.from + "-> (" + this.distance + ")->" + this.to;
	}
	
}
