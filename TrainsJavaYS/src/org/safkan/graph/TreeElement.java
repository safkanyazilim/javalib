package org.safkan.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TreeElement {
	private String nodeId;
	private Map<Double, TreeElement> children;
	private TreeElement parent;
	private int depth;
	private double distance;
	
	public TreeElement(String nodeId) {
		this(null, nodeId);
		this.depth = 0;
	}
	
	public TreeElement(TreeElement parent, String nodeId) {
		this.nodeId = nodeId;
		this.parent = parent;
		this.children = new HashMap<Double, TreeElement>();
		this.depth = this.parent.depth + 1;
		this.distance = 0.0;
	}

	public void addChild(double distance, TreeElement child) {
		this.children.put(distance, child);
		
	}

	public String getNodeId() {
		return nodeId;
	}

	public Map<Double, TreeElement> getChildren() {
		return Collections.unmodifiableMap(children);
	}

	public TreeElement getParent() {
		return parent;
	}
	
}
