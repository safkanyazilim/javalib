package org.safkan.graph;

import java.util.HashMap;
import java.util.Map;

class CompiledNode {
	public String id;
	public Map<String, Double> links;
	
	// A-star related variables
	public double f;
	public double g;
	public double h;
	public boolean open;
	public boolean closed;
	public CompiledNode cameFrom;
	
	public CompiledNode(Node node) {
		this.id = node.getId();
		this.links = new HashMap<String, Double>();
	}
}