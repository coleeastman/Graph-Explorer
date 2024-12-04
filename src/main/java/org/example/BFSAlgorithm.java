// NO LONGER NEEDED AFTER STRATEGY IMPLEMENTATION

package org.example;

import java.util.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class BFSAlgorithm extends GraphSearchTemplate {
    private Queue<String> queue;

    public BFSAlgorithm(DefaultDirectedGraph<String, DefaultEdge> graph) {
        super(graph);
    }

    @Override
    protected void initializeStructure(String startNode) {
        queue = new LinkedList<>();
        queue.add(startNode);
    }

    @Override
    protected void addNodeToStructure(String node) {
        queue.add(node);
    }

    @Override
    protected String getNextNode() {
        return queue.poll();
    }

    @Override
    protected boolean isStructureEmpty() {
        return queue.isEmpty();
    }
}
