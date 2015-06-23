package org.safkan.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Path {
	protected List<Node> nodes;
	
	public Path(Node startingNode) {
		this.nodes = new ArrayList<Node>();
		this.nodes.add(startingNode);
	}
	
	public Path(Node... node) {
		this.nodes = new ArrayList<Node>(Arrays.asList(node));
	}
	
	public List<Node> getNodes() {
		return Collections.unmodifiableList(this.nodes);
	}
}
