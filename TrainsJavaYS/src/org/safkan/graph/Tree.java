package org.safkan.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree {
	private TreeElement root;
	private Queue<TreeElement> openQueue;
	private List<TreeElement> allTreeElements;
	
	Tree(String rootNodeId) {
		this.root = new TreeElement(rootNodeId);
		this.allTreeElements = new ArrayList<TreeElement>();
		this.openQueue = new LinkedList<TreeElement>();
	}
	
	void expandTree(CompiledGraphImplementation compiledGraph, Integer maxDepth, Double maxDistance) {
		if (maxDepth == null && maxDistance == null) {
			throw new IllegalArgumentException("Both maxDepth and maxDistance may not be null.");
		}
		
		this.openQueue.add(this.root);
		
		while (!this.openQueue.isEmpty()) {
			TreeElement current = this.openQueue.poll();
			this.allTreeElements.add(current);
			
			CompiledNode compiledNode = compiledGraph.getCompiledNode(current.nodeId);
			
			if (compiledNode == null) {
				throw new IllegalArgumentException("Node with id " + current.nodeId + " is not present in the graph.");
			}
			
			for (String targetNodeId : compiledNode.links.keySet()) {
				double distanceToNode = compiledNode.links.get(targetNodeId);
				
				int targetDepth = current.depth + 1;
				double targetDistance = current.distanceFromRoot + distanceToNode;
				
				if (maxDepth != null && targetDepth > maxDepth) {
					break;
				}
				
				if (maxDistance != null && targetDistance >= maxDistance) {
					continue;
				}
				
				TreeElement child = new TreeElement(current, targetNodeId, targetDistance);
				
				current.addChild(distanceToNode, child);
				
				openQueue.add(child);
			}
			
		}
	}
	
	List<Path> generatePaths(Integer minDepth, Node targetNode) {
		List<Path> paths = new ArrayList<Path>();
		
		for (TreeElement element : this.allTreeElements) {
			if (element.depth == 0 || (minDepth != null && element.depth < minDepth)) {
				continue;
			}
			
			if (targetNode != null && !element.nodeId.equals(targetNode.getId())) {
				continue;
			}
			
			Path path = new Path();
			
			TreeElement current = element;
			
			while (current != null) {
				path.addNodeToBeginning(new Node(current.nodeId));
				current = current.parent;
			}
			
			paths.add(path);
		}
		
		return paths;
	}
	
	int countPaths(Integer minDepth, Node targetNode) {
		int count = 0;
		
		for (TreeElement element : this.allTreeElements) {
			if (element.depth == 0 || (minDepth != null && element.depth < minDepth)) {
				continue;
			}
			
			if (targetNode != null && !element.nodeId.equals(targetNode.getId())) {
				continue;
			}
			
			count++;
		}
		
		return count;
	}

	
}
