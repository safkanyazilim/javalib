package org.safkan.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TraversedPath extends Path {
	private List<Double> distances;
	private double totalDistance;
	
	TraversedPath(Node startingNode) {
		super(startingNode);
		this.distances = new ArrayList<Double>();
		this.totalDistance = 0.0;
	}
	
	public List<Double> getDistances() {
		return Collections.unmodifiableList(this.distances);
	}
	
	public double getTotalDistance() {
		return this.totalDistance;
	}
	
	void addNode(double distance, Node node) {
		this.distances.add(distance);
		this.nodes.add(node);
		this.totalDistance += distance;
	}
	
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		
		for (int i = 0; i < this.nodes.size(); i++) {

			if (i > 0) {
				buf.append(">");
				buf.append(this.distances.get(i - 1));
				buf.append(">");
			}
			
			buf.append(this.nodes.get(i).toString());
		}
		
		return buf.toString();
	}
}
