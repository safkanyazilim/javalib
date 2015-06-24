package org.safkan.graph;

import java.util.List;

public interface CompiledGraph {
	/**
	 * <p>
	 * Attempt to traverse a given {@link Path} on the graph. This will 
	 * return a {@link TraversedPath}, or null if the given path can
	 * not be traversed on this graph.
	 * </p> 
	 * @param path the path to be traversed.
	 * @return the {@link TraversedPath}, or null if that path can not be traversed.
	 */
	public TraversedPath traverse(Path path);

	/**
	 * <p>
	 * Generate and return a list of all paths, according to the given parameters.
	 * </p>
	 * 
	 * <p>
	 * If a target node is specified, only paths ending on that target node will be returned.
	 * It can be null, in which case no such filtering is applied.
	 * </p>
	 * 
	 * <p>
	 * The "depth" of a path is defined as the number of edges that a path contains. If we have
	 * nodes A, B, C, D, E then the path ABC has a depth of 2, while the path E has a depth of 
	 * 0. This method will never report a path of depth zero.
	 * </p>
	 * 
	 * <p>
	 * At least one of maxDepth and maxDistance must be specified. Otherwise this method will
	 * throw an {@link IllegalArgumentException}. (Otherwise, the search tree will have to expand
	 * without limit.)
	 * </p>
	 * 
	 * <p> 
	 * The parameters minDepth and maxDepth are inclusive, while maxDistance is exclusive.
	 * </p>
	 * 
	 * @param startingNode The starting node. May not be null, the only mandatory parameter.
	 * @param targetNode Only paths ending on this node will be generated. If null, no filtering is done on ending nodes.
	 * @param minDepth The minimum depth (inclusive) that a path that gets generated.
	 * @param maxDepth The maximum depth (inclusive) of a path that gets generated.
	 * @param maxDistance The maximum distance (exclusive) of a path that gets generated.
	 * @return list of all paths according to given constraints.
	 */
	public List<Path> generatePaths(Node startingNode, Node targetNode, Integer minDepth, Integer maxDepth, Double maxDistance);

	/**
	 * <p>
	 * Return the count of all paths, according to given parameters.
	 * </p>
	 * 
	 * <p>
	 * Please see {@link #generatePaths(Node, Node, Integer, Integer, Double)}. The functionality
	 * here is exactly the same, except that the count of the paths are returned without
	 * actually generating them.
	 * </p>
	 * @param startingNode The starting node. May not be null, the only mandatory parameter.
	 * @param targetNode Only paths ending on this node will be counted. If null, no filtering is done on ending nodes.
	 * @param minDepth The minimum depth (inclusive) that a path that gets counted.
	 * @param maxDepth The maximum depth (inclusive) of a path that gets counted.
	 * @param maxDistance The maximum distance (exclusive) of a path that gets counted.
	 * @return count of all paths according to given constraints.
	 */
	public int countPaths(Node startingNode, Node targetNode, Integer minDepth, Integer maxDepth, Double maxDistance);
	
	/**
	 * <p>
	 * Finds and returns the shortest path between two nodes.
	 * Will return null if no path exists.
	 * </p>
	 * @param startingNode the starting node
	 * @param targetNode the target node.
	 * @return the shortest path. Null if no path exists.
	 */
	public Path findShortestPath(Node startingNode, Node targetNode);
	
	/**
	 * <p>
	 * Finds the shortest path between two nodes, and returns its length.
	 * Will return null if no such path exists.
	 * </p>
	 * 
	 * @param startingNode the starting node.
	 * @param targetNode the target node.
	 * @return the length of the shortest path. Null if no path exists.
	 */
	public Double findLengthOfShortestPath(Node startingNode, Node targetNode);


}
