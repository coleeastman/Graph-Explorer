package org.example;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class BFSStrategy implements GraphSearchStrategy {
    @Override
    public GraphParser.Path search(DefaultDirectedGraph<String, DefaultEdge> graph, String src, String dst) {
        BFSAlgorithm bfsAlgorithm = new BFSAlgorithm(graph);
        return bfsAlgorithm.search(src, dst);
    }
}
