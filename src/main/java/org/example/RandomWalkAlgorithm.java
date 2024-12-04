package org.example;

import java.util.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class RandomWalkAlgorithm extends GraphSearchTemplate {

    private Random random;
    private String currentNode;

    public RandomWalkAlgorithm(DefaultDirectedGraph<String, DefaultEdge> graph) {
        super(graph);
        this.random = new Random();
    }

    @Override
    protected void initializeStructure(String startNode) {
        this.currentNode = startNode;
    }

    @Override
    protected void addNodeToStructure(String node) {
        // No structure to maintain
    }

    @Override
    protected String getNextNode() {

        if (currentNode == null) return null;

        // Choose random neighbor
        List<String> neighbors = new ArrayList<>();
        for (DefaultEdge edge : graph.outgoingEdgesOf(currentNode)) {
            neighbors.add(graph.getEdgeTarget(edge));
        }
        if (neighbors.isEmpty()) return null;

        currentNode = neighbors.get(random.nextInt(neighbors.size()));
        return currentNode;
    }

    @Override
    protected boolean isStructureEmpty() {
        return false;
    }

    @Override
    public GraphParser.Path search(String src, String dst) {
        GraphParser.Path path = new GraphParser.Path();
        currentNode = src;
        path.addNode(currentNode);

        System.out.println("Random testing");

        while (!currentNode.equals(dst)) {
            String nextNode = getNextNode();

            if (nextNode == null) {
                System.out.println("No more neighbors to visit. Ending search.");
                System.out.println("Destination not reached. Path incomplete.");
                return null;
            }

            System.out.println("Visiting: " + nextNode);
            path.addNode(nextNode);
        }

        return path;
    }
}
