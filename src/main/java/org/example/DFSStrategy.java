package org.example;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

public class DFSStrategy implements GraphSearchStrategy {

    @Override
    public GraphParser.Path search(DefaultDirectedGraph<String, DefaultEdge> graph, String src, String dst) {
        Stack<String> stack = new Stack<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        stack.push(src);
        visited.add(src);

        while (!stack.isEmpty()) {
            String current = stack.pop();

            for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
                String neighbor = graph.getEdgeTarget(edge);

                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);

                    if (neighbor.equals(dst)) {
                        return buildPath(src, dst, parentMap);
                    }
                }
            }
        }

        return null; // Return null if no path is found
    }

    private GraphParser.Path buildPath(String src, String dst, Map<String, String> parentMap) {
        GraphParser.Path path = new GraphParser.Path();
        String current = dst;

        while (current != null) {
            path.addNode(current);
            current = parentMap.get(current);
        }

        Collections.reverse(path.getNodes());
        return path;
    }
}
