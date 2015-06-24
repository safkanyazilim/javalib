package org.safkan.graph;

import java.util.List;

public interface CompiledGraph {
	public TraversedPath traverse(Path path);
	public List<Path> generatePaths(Node startingNode, Node targetNode, Integer minDepth, Integer maxDepth, Double maxDistance);
	public int countPaths(Node startingNode, Node targetNode, Integer minDepth, Integer maxDepth, Double maxDistance);
	
}
