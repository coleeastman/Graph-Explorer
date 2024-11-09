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


        // Part 1 Feature 1: Graph Parser demonstration
        GraphParser parser = new GraphParser();
        DefaultDirectedGraph<String, DefaultEdge> graph = parser.parseGraph("src/main/resources/graph.dot");


        // Part 1 Feature 1: Convert to String demonstration
        System.out.println(parser.toString());


        // Part 1 Feature 2: Add Node(s) demonstration
        parser.addNode("E");
        parser.addNodes(new String[]{"F", "G", "H"});


        // Part 1 Feature 3: Add Edge demonstration
        parser.addEdge("A", "F");
        parser.addEdge("F", "G");


        // Part 1 Feature 1: Output DOT file demonstration
        parser.outputGraph("src/main/resources/output.dot");


        // Part 1 Feature 4: Output as PNG demonstration
        parser.outputGraphics("src/main/resources/graph.png", "png");


        // Part 2 Feature 1: Demonstrate removing a single node
        System.out.println("Demonstrating node removal:");
        parser.removeNode("E");
        System.out.println(parser.toString());


        // Part 2 Feature 2: Demonstrate removing multiple nodes
        System.out.println("Demonstrating multiple node removal:");
        parser.removeNodes(new String[]{"F", "G"});
        System.out.println(parser.toString());


        // Part 2 Feature 3: Demonstrate removing an edge
        System.out.println("Demonstrating edge removal:");
        parser.removeEdge("A", "B");  // Replace with any valid edge present in your graph
        System.out.println(parser.toString());


    }


    // Part 1 Feature 1: Parse the DOT file and create a directed graph


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


    // Part 1 Feature 2: Add Node(s)
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




    // Part 1 Feature 3: Add Edge(s)
    public void addEdge(String srcLabel, String dstLabel) {
        if (!graph.containsEdge(srcLabel, dstLabel)) {
            graph.addVertex(srcLabel);  // Ensure the vertices exist
            graph.addVertex(dstLabel);
            graph.addEdge(srcLabel, dstLabel);
            System.out.println("Edge added: " + srcLabel + " -> " + dstLabel);
        } else {
            System.out.println("Edge already exists: " + srcLabel + " -> " + dstLabel);
        }
    }




    // Part 1 Feature 4: Output the graph as an image (PNG)
    public void outputGraphics(String filepath, String format) {
        Graph g = graph("example").directed();




        // Dynamically create nodes and edges based on the parsed graph
        for (String vertex : graph.vertexSet()) {
            g = g.with(node(vertex)); // Add each vertex as a node
        }




        for (DefaultEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);




            // Add edge between nodes
            g = g.with(node(source).link(to(node(target)).with(Label.of(source + " -> " + target))));
        }




        // Export the graph to the specified image format
        try {
            Graphviz.fromGraph(g).width(500).render(Format.PNG).toFile(new File(filepath));
            System.out.println("Graph exported as " + format + " to " + filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Part 2: Remove a node by its label
    public void removeNode(String label) {
        if (graph.containsVertex(label)) {
            graph.removeVertex(label);
            System.out.println("Node removed: " + label);
        } else {
            throw new IllegalArgumentException("Node not found: " + label);
        }
    }


    // Part 2: Remove multiple nodes by their labels
    public void removeNodes(String[] labels) {
        for (String label : labels) {
            removeNode(label);
        }
    }


    // Part 2: Remove an edge between two nodes
    public void removeEdge(String srcLabel, String dstLabel) {
        if (graph.containsEdge(srcLabel, dstLabel)) {
            graph.removeEdge(srcLabel, dstLabel);
            System.out.println("Edge removed: " + srcLabel + " -> " + dstLabel);
        } else {
            throw new IllegalArgumentException("Edge not found: " + srcLabel + " -> " + dstLabel);
        }
    }


}
