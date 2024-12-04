package org.example;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

public class RandomWalkStrategy implements GraphSearchStrategy {

    @Override
    public GraphParser.Path search(DefaultDirectedGraph<String, DefaultEdge> graph, String src, String dst) {
        GraphParser.Path path = new GraphParser.Path();
        Set<String> visited = new HashSet<>();
        Random random = new Random();

        String currentNode = src;
        path.addNode(currentNode);
        visited.add(currentNode);

        System.out.println("Random testing");

        while (!currentNode.equals(dst)) {
            List<String> neighbors = new ArrayList<>();
            for (DefaultEdge edge : graph.outgoingEdgesOf(currentNode)) {
                neighbors.add(graph.getEdgeTarget(edge));
            }

            if (neighbors.isEmpty()) {
                System.out.println("No more neighbors to visit. Ending search.");
                break;
            }

            // Choose a random neighbor
            String nextNode = neighbors.get(random.nextInt(neighbors.size()));
            System.out.println("visiting " + path);
            path.addNode(nextNode);
            visited.add(nextNode);

            currentNode = nextNode;
        }

        return path;
    }
}
