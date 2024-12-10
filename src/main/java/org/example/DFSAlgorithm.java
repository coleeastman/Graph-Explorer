package org.example;

import java.util.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class DFSAlgorithm extends GraphSearchTemplate {

    private Stack<String> stack;

    public DFSAlgorithm(DefaultDirectedGraph<String, DefaultEdge> graph) {
        super(graph);
    }

    @Override
    protected void initializeStructure(String startNode) {
        stack = new Stack<>();
        stack.push(startNode);
    }

    @Override
    protected void addNodeToStructure(String node) {
        stack.push(node);
    }

    @Override
    protected String getNextNode() {
        return stack.pop();
    }

    @Override
    protected boolean isStructureEmpty() {
        return stack.isEmpty();
    }

}
