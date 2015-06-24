package org.safkan.graph;

import java.util.HashMap;
import java.util.Map;

public class TreeElement {
	String nodeId;
	Map<Double, TreeElement> children;
	TreeElement parent;
	int depth;
	double distanceFromRoot;
	
	TreeElement(String nodeId) {
		this(null, nodeId, 0.0);
	}
	
	TreeElement(TreeElement parent, String nodeId, double distanceFromRoot) {
		this.nodeId = nodeId;
		this.parent = parent;
		this.distanceFromRoot = distanceFromRoot;
		this.children = new HashMap<Double, TreeElement>();
		if (parent != null) {
			this.depth = this.parent.depth + 1;
		} else {
			this.depth = 0;
		}
		
	}

	public void addChild(double edgeDistance, TreeElement child) {
		this.children.put(edgeDistance, child);
		
	}

	
}
