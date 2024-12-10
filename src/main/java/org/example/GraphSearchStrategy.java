package org.example;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public interface GraphSearchStrategy {
    GraphParser.Path search(DefaultDirectedGraph<String, DefaultEdge> graph, String src, String dst);
}
