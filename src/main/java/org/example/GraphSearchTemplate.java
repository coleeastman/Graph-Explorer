// NO LONGER NEEDED AFTER STRATEGY IMPLEMENTATION

package org.example;

import java.util.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public abstract class GraphSearchTemplate {
    protected DefaultDirectedGraph<String, DefaultEdge> graph;
    protected Map<String, String> parentMap;
    protected Set<String> visited;

    public GraphSearchTemplate(DefaultDirectedGraph<String, DefaultEdge> graph) {
        this.graph = graph;
        this.parentMap = new HashMap<>();
        this.visited = new HashSet<>();
    }

    public GraphParser.Path search(String src, String dst) {
        initializeStructure(src);
        visited.add(src);

        while (!isStructureEmpty()) {
            String current = getNextNode();

            if (current.equals(dst)) {
                return buildPath(src, dst);
            }

            for (DefaultEdge edge : graph.edgesOf(current)) {
                String neighbor = graph.getEdgeTarget(edge);
                if (!visited.contains(neighbor)) {
                    addNodeToStructure(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return null;
    }

    protected abstract void initializeStructure(String startNode);
    protected abstract void addNodeToStructure(String node);
    protected abstract String getNextNode();
    protected abstract boolean isStructureEmpty();

    protected GraphParser.Path buildPath(String src, String dst) {
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
