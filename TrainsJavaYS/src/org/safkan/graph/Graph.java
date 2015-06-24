package org.safkan.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * External representation of a graph.
 * </p>
 * 
 * @author Dr. Y. Safkan &lt;safkan@gmail.com&gt;
 *
 */
public class Graph {
	private Set<Node> nodes;
	private Set<Edge> edges;
	private boolean strictNodes;
	private boolean strictEdges;
	
	/**
	 * <p>
	 * Create a new graph, with desired strictness for adding nodes and edges.
	 * </p>
	 * 
	 * <p>
	 * If <code>strictNodes</code> is <code>true</code>, the nodes of the graph
	 * must be added through the {@link #addNode(Node)} method before edges can
	 * be added. In addition, in this case, trying to add a {@link Node} twice
	 * will result in an exception.
	 * </p>
	 * 
	 * <p>
	 * If <code>strictNodes</code> is <code>false</code>, you may or may not add
	 * the nodes before the edges. You may also repeatedly add nodes, addition
	 * of already existing nodes will be ignored. Nodes will be inferred from the
	 * from and to nodes of the added edges.
	 * </p>
	 * 
	 * <p>
	 * If <code>strictEdges</code> is <code>true</code> an {@link Edge} may be only added once. 
	 * An edge is identified by its from and to nodes, so trying to add an edge twice with
	 * different distances will still throw an exception.
	 * </p>
	 * 
	 * <p>
	 * If <code>strictEdges</code> is <code>false</code> and you do add an {@link Edge}
	 * more than once, only the last one will remain in the graph, replacing the previous 
	 * ones silently. 
	 * </p>
	 * 
	 * @param strictNodes strict node handling.
	 * @param strictEdges strict edge handling.
	 */
	public Graph(boolean strictNodes, boolean strictEdges) {
		this.strictNodes = strictNodes;
		this.strictEdges = strictEdges;
		this.nodes = new HashSet<Node>();
		this.edges = new HashSet<Edge>();
	}
	
	/**
	 * <p>
	 * Create a new graph, with non-strict nodes and non-strict edges.
	 * </p>
	 */
	public Graph() {
		this(false, false);
	}
	
	/**
	 * <p>
	 * Add a node to this graph.
	 * </p>
	 * 
	 * <p>
	 * For a graph with <i>strict nodes</i> if the given node is already in the graph,
	 * this will throw an exception. For a graph with <i>non-strict nodes</i> this will
	 * in effect ignore the call.
	 * </p>
	 * 
	 * @param node the node to be added to the graph.
	 * 
	 * @throws IllegalArgumentException if node already in graph, and <code>strictNodes == true</code>
	 */
	public void addNode(Node node) {
		boolean success = this.nodes.add(node);
		
		if (!success && this.strictNodes) {
			throw new IllegalArgumentException("The graph already contains node with id " 
						                       + node.getId() + ". Perhaps you want non-strict nodes?");
		}
	}

	/**
	 * <p>
	 * Add an edge to this graph.
	 * </p>
	 * 
	 * <p>
	 * For a graph with <i>strict nodes</i>, the <code>from</code> and <code>to</code> nodes
	 * of the given {@link Edge} must already have been added to this graph via the {@link #addNode(Node)} 
	 * method. Otherwise, an {@link IllegalArgumentException} will be thrown.
	 * </p>
	 * 
	 * <p>
	 * For a graph with <i>strict edges</i>, trying to insert an existing edge (edges are identified with their
	 * from and to nodes, so different distances are not sufficient to discriminate edges) will result
	 * in an {@link IllegalArgumentException} being thrown.
	 * </p>
	 * 
	 * <p>
	 * For a graph with <i>non-strict edges</i>, trying to insert an existing edge will result in the replacement
	 * of the existing edge with the given one, in effect updating the distance for that edge.
	 * </p>
	 * 
	 * 
	 * @param edge the edge to be added to the graph.
	 * 
	 * @throws IllegalArgumentException if strictness conditions are not met.
	 */
	public void addEdge(Edge edge) {
		if (this.strictNodes) {
			if (!this.nodes.contains(edge.getFrom())) {
				throw new IllegalArgumentException("The node " + edge.getFrom() + " of edge " + edge + " is not present in the graph.");
			}
			
			if (!this.nodes.contains(edge.getTo())) {
				throw new IllegalArgumentException("The node " + edge.getTo() + " of edge " + edge + " is not present in the graph.");
			}
		} 
		
		boolean edgeAlreadyPresent = this.edges.contains(edge);
		
		if (this.strictEdges && edgeAlreadyPresent) {
			throw new IllegalArgumentException("The edge from node " + edge.getFrom() + 
											   " to node " + edge.getTo() + " is already part of the graph.");
		}
		
		this.nodes.add(edge.getFrom());
		this.nodes.add(edge.getTo());
		
		if (edgeAlreadyPresent) {
			this.nodes.remove(edge);
		}
		
		this.edges.add(edge);
	}
	
	/**
	 * Getter for nodes. It returns an unmodifiable set.
	 * @return the nodes of this graph.
	 */
	public Set<Node> getNodes() {
		return Collections.unmodifiableSet(this.nodes);
	}
	
	
	
	/**
	 * Getter for edges. It returns an unmodifiable set.
	 * @return the edges for this graph.
	 */
	public Set<Edge> getEdges() {
		return Collections.unmodifiableSet(this.edges);
	}
	
	
	/**
	 * <p>
	 * Compile this graph, and return a compiled graph. Note that due to the interface design,
	 * the compilation will not fail. The compiled graph can be used for operations and calculations,
	 * but its structure is immutable. Further changes to this graph is possible after compilation,
	 * however it will not affect the compiled graph (unless you call compile again to obtain another
	 * compiled graph.)
	 * </p> 
	 * @return the compiled graph.
	 */
	public CompiledGraph compile() {
		CompiledGraphImplementation compiledGraphImplementation = new CompiledGraphImplementation(this);
		compiledGraphImplementation.compile();
		return compiledGraphImplementation;
	}
	
}
