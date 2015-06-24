package org.safkan.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.safkan.graph.util.PriorityQueue;

/**
 * <p>
 * Implementation of {@link CompiledGraph}
 * </p>
 * 
 * @author Dr. Y. Safkan &lt;safkan@gmail.com&gt;
 *
 */
class CompiledGraphImplementation implements CompiledGraph {
	private Graph graph;
	private Map<String, CompiledNode> compiledNodeMap;
	
	CompiledNode  getCompiledNode(String nodeId) {
		return this.compiledNodeMap.get(nodeId);
	}
	
	CompiledGraphImplementation(Graph graph) {
		this.graph = graph;
		this.compiledNodeMap = new HashMap<String, CompiledNode>();
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
	
	@Override
	public List<Path> generatePaths(Node startingNode, Node targetNode, Integer minDepth, Integer maxDepth, Double maxDistance) {
		Tree tree = new Tree(startingNode.getId());
		
		tree.expandTree(this, maxDepth, maxDistance);
		
		return tree.generatePaths(minDepth, targetNode);
	}
	
	@Override
	public int countPaths(Node startingNode, Node targetNode, Integer minDepth, Integer maxDepth, Double maxDistance) {
		Tree tree = new Tree(startingNode.getId());
		
		tree.expandTree(this, maxDepth, maxDistance);

		return tree.countPaths(minDepth, targetNode);
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
	
	public Path findShortestPath(Node startingNode, Node targetNode) {
		CompiledNode compiledNode = this.runAStar(startingNode, targetNode);
		
		if (compiledNode == null) {
			return null;
		} else {
			return this.constructPath(compiledNode);
		}
	}

	public Double findLengthOfShortestPath(Node startingNode, Node targetNode) {
		CompiledNode compiledNode = this.runAStar(startingNode, targetNode);
		
		if (compiledNode == null) {
			return null;
		} else {
			return compiledNode.g;
		}
	}
	
	private CompiledNode runAStar(Node startingNode, Node targetNode) {

        CompiledNode start = this.compiledNodeMap.get(startingNode.getId());
        
        if (start == null) {
        	throw new IllegalArgumentException("Graph does not contain node (startingNode) with id: " + startingNode.getId());
        }
        
        CompiledNode goal = this.compiledNodeMap.get(targetNode.getId());

        if (goal == null) {
        	throw new IllegalArgumentException("Graph does not contain node (targetNode) with id: " + targetNode.getId());
        }
        
        PriorityQueue<CompiledNode> openQueue = new PriorityQueue<CompiledNode>();

        for (CompiledNode node : this.compiledNodeMap.values()) {
            node.f = Double.MAX_VALUE;
            node.g = Double.MAX_VALUE;
            node.h = 0.0F;
            node.open = false;
            node.closed = false;
            node.cameFrom = null;
        }

        start.g = 0.0;
        start.f = start.g + start.h;
        start.open = true;

        openQueue.insertObjectWithPriority(start, start.f);
        CompiledNode current;
        while ((current = openQueue.popLowestPriority()) != null) {

            current.open = false;
            current.closed = true;

        	
            if (current == goal) {
            	if (current.g > 0.0) {
            		return goal;
            	} else {
            		current.closed = false;
            	}
            }


            for (String neighborKey : current.links.keySet()) {
            	CompiledNode neighbor = this.compiledNodeMap.get(neighborKey);
            	
                if (neighbor.closed) {
                    continue;
                }

                double tentative_g = current.g + current.links.get(neighborKey) ;

                if (!neighbor.open || tentative_g < neighbor.g) {
                    neighbor.cameFrom = current;
                    neighbor.g  = tentative_g;
                    
                    neighbor.f = neighbor.g + neighbor.h;

                    if (!neighbor.open) {
                        openQueue.insertObjectWithPriority(neighbor, neighbor.f);
                        neighbor.open  = true;
                        
                    }
                }
            }
        }


        return null;
    }
	
	private Path constructPath(CompiledNode goal) {

        Path path = new Path();

        CompiledNode compiledNode;

        for (compiledNode = goal; compiledNode != null; compiledNode = compiledNode.cameFrom) {
        	path.addNodeToBeginning(new Node(compiledNode.id));
        }
        return path;
    }

	
	
}
