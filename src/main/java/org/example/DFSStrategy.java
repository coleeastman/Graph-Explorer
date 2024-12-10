package org.example;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class DFSStrategy implements GraphSearchStrategy {
    @Override
    public GraphParser.Path search(DefaultDirectedGraph<String, DefaultEdge> graph, String src, String dst) {
        DFSAlgorithm dfsAlgorithm = new DFSAlgorithm(graph);
        return dfsAlgorithm.search(src, dst);
    }
}
