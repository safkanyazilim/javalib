package org.safkan.graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Path {
	protected List<Node> nodes;
	
	public Path() {
		this.nodes = new LinkedList<Node>();
	}
	
	public Path(Node startingNode) {
		this.nodes = new LinkedList<Node>();
		this.nodes.add(startingNode);
	}
	
	public Path(Node... node) {
		this.nodes = new LinkedList<Node>(Arrays.asList(node));
	}
	
	public List<Node> getNodes() {
		return Collections.unmodifiableList(this.nodes);
	}

	public boolean endsWith(Node node) {
		if (this.nodes.isEmpty()) {
			return false;
		}
		
		Node lastNode = this.nodes.get(this.nodes.size() - 1);
		
		return lastNode.equals(node);
	}
	
	void addNodeToBeginning(Node node) {
		this.nodes.add(0, node);
	}
	

	
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		
		for (Node node : this.nodes) {
			buf.append(node);
		}
		
		return buf.toString();
	}
}
