package org.safkan.trains;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import org.safkan.graph.Edge;
import org.safkan.graph.Graph;
import org.safkan.graph.Node;

public class GraphLoader {
	public static Graph loadGraph(String path) throws FileNotFoundException, IOException, InvalidInputException {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			Graph graph = new Graph(false, true);
			
			String line;
			int lineNumber = 0;
			
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				String[] terms = line.split("[, ]");
				
				for (String term : terms) {
					if (term.isEmpty() || term.endsWith(":")) {
						continue;
					}
					
					term = term.toUpperCase(Locale.US);
					
					if (term.length() < 3) {
						throw new InvalidInputException("Bad term in file " + path + " line " + lineNumber + ": " + term + " (shorter than three characters)");
					}
					
					String startNode = term.substring(0, 1);
					String endNode = term.substring(1, 2);
					String distanceString = term.substring(2);
					double distance;
					
					try {
						distance = Double.parseDouble(distanceString);
					} catch (NumberFormatException e) {
						throw new InvalidInputException("Bad term in file " + path + " line " + lineNumber + ": " + term + " ; numeric part can not be parsed:" + distanceString, e);
					}
	
					Edge edge = new Edge(new Node(startNode), new Node(endNode), distance);
					
					graph.addEdge(edge);
					
				}
			
			}
			
			return graph;
			
			
		}
	}
}
