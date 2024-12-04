package org.example;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class RandomWalkStrategy implements GraphSearchStrategy {
    @Override
    public GraphParser.Path search(DefaultDirectedGraph<String, DefaultEdge> graph, String src, String dst) {
        RandomWalkAlgorithm randomWalkAlgorithm = new RandomWalkAlgorithm(graph);
        return randomWalkAlgorithm.search(src, dst);
    }
}

