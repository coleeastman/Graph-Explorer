package org.example;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.*;
import guru.nidi.graphviz.model.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import java.io.*;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphParser {


    private DefaultDirectedGraph<String, DefaultEdge> graph;

    // Main
    public static void main(String[] args) {

        // Feature 1: Graph Parser demonstration
        GraphParser parser = new GraphParser();
        DefaultDirectedGraph<String, DefaultEdge> graph = parser.parseGraph("src/main/resources/graph.dot");

        // Feature 1: Convert to String demonstration
        System.out.println(parser.toString());

        // Feature 2: Add Node(s) demonstration
        parser.addNode("E");
        parser.addNodes(new String[]{"F", "G", "H"});

        // Feature 1: Output DOT file demonstration
        parser.outputGraph("src/main/resources/output.dot");

    }

    // Feature 1: Parse the DOT file and create a directed graph

    public DefaultDirectedGraph<String, DefaultEdge> parseGraph(String filepath) {

        graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {

            String line;

            while ((line = br.readLine()) != null) {
                // Parsing logic
                if (line.contains("->")) {
                    String[] parts = line.split("->");
                    String source = parts[0].trim();
                    String target = parts[1].replace(";", "").trim();
                    graph.addVertex(source);
                    graph.addVertex(target);
                    graph.addEdge(source, target);
                }
            }
        } catch (IOException e) {

            e.printStackTrace();

        }

        return graph;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Graph:\n");

        // vertices
        sb.append("Nodes: ");
        for (String vertex : graph.vertexSet()) {
            sb.append(vertex).append(" ");
        }

        // edges
        sb.append("\nEdges:\n");
        for (DefaultEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);
            sb.append(source).append(" -> ").append(target).append("\n");
        }

        return sb.toString();

    }

    public void outputGraph(String filepath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {

            writer.write("digraph G {\n");

            // vertices
            for (String vertex : graph.vertexSet()) {
                writer.write("  " + vertex + ";\n");
            }

            // edges
            for (DefaultEdge edge : graph.edgeSet()) {
                String source = graph.getEdgeSource(edge);
                String target = graph.getEdgeTarget(edge);
                writer.write("  " + source + " -> " + target + ";\n");
            }

            writer.write("}");

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    // Feature 2: Add Node(s)
    public void addNode(String label) {
        if (!graph.containsVertex(label)) {
            graph.addVertex(label);
            System.out.println("Node added: " + label);
        } else {
            System.out.println("Node already exists: " + label);
        }
    }


    public void addNodes(String[] labels) {
        for (String label : labels) {
            addNode(label);
        }
    }


}
