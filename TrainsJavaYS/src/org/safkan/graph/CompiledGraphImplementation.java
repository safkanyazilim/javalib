package org.safkan.graph;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Implementation of {@link CompiledGraph}
 * </p>
 * 
 * @author Dr. Y. Safkan &lt;safkan@gmail.com&gt;
 *
 */
class CompiledGraphImplementation implements CompiledGraph {

	
	private static class CompiledNode {
		public String id;
		public Map<String, Double> links;
		
		public CompiledNode(Node node) {
			this.id = node.getId();
			this.links = new HashMap<String, Double>();
		}
	}
	
	private Graph graph;
	private Map<String, CompiledNode> compiledNodeMap;
	
	
	CompiledGraphImplementation(Graph graph) {
		this.graph = graph;
		this.compiledNodeMap = new HashMap<String, CompiledGraphImplementation.CompiledNode>();
	}
	
	void compile() {
		for (Node node : this.graph.getNodes()) {
			CompiledNode compiledNode = new CompiledNode(node);
			this.compiledNodeMap.put(compiledNode.id, compiledNode);
		}
		
		for (Edge edge : this.graph.getEdges()) {
			CompiledNode from = this.compiledNodeMap.get(edge.getFrom().getId());
			CompiledNode to = this.compiledNodeMap.get(edge.getTo().getId());
			
			from.links.put(to.id, edge.getDistance());
		}
	}
	
	
	public Tree expandTreeFromNode(Node node, int depth) {
		CompiledNode compiledNode = this.compiledNodeMap.get(node.getId());
		
		if (compiledNode == null) {
			return null;
		}
		
		Tree tree = new Tree(new TreeElement(node.getId()));
		
		
		return null;
		
	}
	
	public TraversedPath traverse(Path path) {
		TraversedPath traversedPath = null;
		
		CompiledNode previousCompiledNode = null;
		
		for(Node node : path.nodes) {
			CompiledNode compiledNode = this.compiledNodeMap.get(node.getId());
			
			if (compiledNode == null) {
				return null;
			}
			
			if (previousCompiledNode == null) {
				traversedPath = new TraversedPath(node);
			} else {
				
				Double distance = previousCompiledNode.links.get(compiledNode.id);
				
				if (distance == null) {
					return null;
				}
				
				traversedPath.addNode(distance.doubleValue(), node);
			}
		
			previousCompiledNode = compiledNode;
		}
		
		
		return traversedPath;
	}

	
}
