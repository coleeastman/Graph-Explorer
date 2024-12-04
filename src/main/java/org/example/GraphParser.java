package org.example;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.*;
import guru.nidi.graphviz.model.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import java.io.*;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.*;

// GraphParser class to manage graph operations
public class GraphParser {

    private DefaultDirectedGraph<String, DefaultEdge> graph;

    // Refactor 3: constants for frequently used paths

    private static final String GRAPH_DOT_PATH = "src/main/resources/graph.dot";
    private static final String GRAPH_OUTPUT_PATH = "src/main/resources/output.dot";
    private static final String GRAPH_PNG_PATH = "src/main/resources/graph.png";


    // Enum to specify the algorithm type (BFS or DFS)
    public enum Algorithm {
        BFS,
        DFS
    }

    // Main method demonstrating graph operations
    public static void main(String[] args) {

        GraphParser parser = new GraphParser();
        DefaultDirectedGraph<String, DefaultEdge> graph = parser.parseGraph(GRAPH_DOT_PATH);

        // Part 1 Feature 1: Print graph demonstration
        System.out.println(parser.toString());

        // Part 1 Feature 2: Add nodes and edges demonstration
        parser.addNode("E");
        parser.addNodes(new String[]{"F", "G", "H"});
        parser.addEdge("A", "F");
        parser.addEdge("F", "G");

        // Part 2 Feature 1: Demonstrate node removal
        System.out.println("Demonstrating node removal:");
        parser.removeNode("E");
        System.out.println(parser.toString());

        // Part 2 Feature 2: Demonstrate multiple node removal
        System.out.println("Demonstrating multiple node removal:");
        parser.removeNodes(new String[]{"F", "G"});
        System.out.println(parser.toString());

        // Part 2 Feature 3: Demonstrate edge removal
        System.out.println("Demonstrating edge removal:");
        parser.removeEdge("A", "B");  // Example edge
        System.out.println(parser.toString());

        // Part 3 Feature 1: Graph Search (BFS/DFS) demonstration
        System.out.println("Searching path between A and E using BFS:");
        Path path = parser.graphSearch("B", "C", Algorithm.BFS);  // Use BFS
        if (path != null) {
            System.out.println("Path found: " + path);
        } else {
            System.out.println("No path found.");
        }

        // Demonstrating DFS search
        System.out.println("Searching path between A and E using DFS:");
        path = parser.graphSearch("B", "C", Algorithm.DFS);  // Use DFS
        if (path != null) {
            System.out.println("Path found: " + path);
        } else {
            System.out.println("No path found.");
        }

        // Part 1 Feature 3: Output graph as DOT and PNG
        // Refactor 5: Relocate graph output and png output as last execution so all functions are reflected
        parser.outputGraph(GRAPH_OUTPUT_PATH);
        parser.outputGraphics(GRAPH_PNG_PATH, "png");
    }

    // Part 1 Feature 1: Parse the DOT file and create a directed graph
    public DefaultDirectedGraph<String, DefaultEdge> parseGraph(String filepath) {
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace

                // Refactor 4: Handle standalone nodes
                if (line.endsWith(";") && !line.contains("->")) {
                    String node = line.replace(";", "").trim();
                    graph.addVertex(node);
                }

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
        sb.append("Nodes: ");
        for (String vertex : graph.vertexSet()) {
            sb.append(vertex).append(" ");
        }
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
            for (String vertex : graph.vertexSet()) {
                writer.write("  " + vertex + ";\n");
            }
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

    // Part 1 Feature 2: Add a single node
    public void addNode(String label) {
        if (!graph.containsVertex(label)) {
            graph.addVertex(label);
            System.out.println("Node added: " + label);
        } else {
            System.out.println("Node already exists: " + label);
        }
    }

    // Part 1 Feature 2: Add multiple nodes
    public void addNodes(String[] labels) {
        for (String label : labels) {
            addNode(label);
        }
    }

    // Part 1 Feature 3: Add an edge between nodes
    public void addEdge(String sourceLabel, String destinationLabel) { // Refactor 1: made variable names more specific
        if (!graph.containsEdge(sourceLabel, destinationLabel)) {
            graph.addVertex(sourceLabel);
            graph.addVertex(destinationLabel);
            graph.addEdge(sourceLabel, destinationLabel);
            System.out.println("Edge added: " + sourceLabel + " -> " + destinationLabel);
        } else {
            System.out.println("Edge already exists: " + sourceLabel + " -> " + destinationLabel);
        }
    }

    // Part 1 Feature 4: Output the graph as a PNG image
    public void outputGraphics(String filepath, String format) {
        Graph g = graph("example").directed();
        for (String vertex : graph.vertexSet()) {
            g = g.with(node(vertex));
        }
        for (DefaultEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);
            g = g.with(node(source).link(to(node(target)).with(Label.of(source + " -> " + target))));
        }
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
    public void removeEdge(String sourceLabel, String destinationLabel) {
        if (graph.containsEdge(sourceLabel, destinationLabel)) {
            graph.removeEdge(sourceLabel, destinationLabel);
            System.out.println("Edge removed: " + sourceLabel + " -> " + destinationLabel);
        } else {
            throw new IllegalArgumentException("Edge not found: " + sourceLabel + " -> " + destinationLabel);
        }
    }

    // Part 3: Graph Search (BFS or DFS based on enum), changed due to template pattern and again due to search strategy
    public Path graphSearch(String src, String dst, Algorithm algo) {
        GraphSearchStrategy strategy;

        switch (algo) {
            case BFS:
                strategy = new BFSStrategy();
                break;
            case DFS:
                strategy = new DFSStrategy();
                break;
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algo);
        }

        return strategy.search(graph, src, dst);
    }

//    Original BFS and DFS
//
//    private Path bfsSearch(String src, String dst) {
//        Queue<String> queue = new LinkedList<>();
//        Map<String, String> parentMap = new HashMap<>();
//        Set<String> visited = new HashSet<>();
//
//        try {
//            queue.add(src);
//            visited.add(src);
//
//            while (!queue.isEmpty()) {
//                String current = queue.poll();
//
//                for (DefaultEdge edge : graph.edgesOf(current)) {
//                    String neighbor = graph.getEdgeTarget(edge);
//                    if (!visited.contains(neighbor)) {
//                        queue.add(neighbor);
//                        visited.add(neighbor);
//                        parentMap.put(neighbor, current);
//
//                        if (neighbor.equals(dst)) {
//                            return buildPath(src, dst, parentMap);
//                        }
//                    }
//                }
//            }
//        } catch (IllegalArgumentException e) {
//            System.out.println("Error: " + e.getMessage()); // Print the error message
//        }
//
//        return null; // Return null if no path is found
//    }
//
//    private Path dfsSearch(String src, String dst) {
//        Stack<String> stack = new Stack<>();
//        Map<String, String> parentMap = new HashMap<>();
//        Set<String> visited = new HashSet<>();
//
//        try {
//            stack.push(src);
//            visited.add(src);
//
//            while (!stack.isEmpty()) {
//                String current = stack.pop();
//
//                for (DefaultEdge edge : graph.edgesOf(current)) {
//                    String neighbor = graph.getEdgeTarget(edge);
//                    if (!visited.contains(neighbor)) {
//                        stack.push(neighbor);
//                        visited.add(neighbor);
//                        parentMap.put(neighbor, current);
//
//                        if (neighbor.equals(dst)) {
//                            return buildPath(src, dst, parentMap);
//                        }
//                    }
//                }
//            }
//        } catch (IllegalArgumentException e) {
//            System.out.println("Error: " + e.getMessage()); // Print the error message
//        }
//
//        return null; // Return null if no path is found
//    }
//
//    // Helper method to build the path from source to destination
//    private Path buildPath(String src, String dst, Map<String, String> parentMap) {
//        Path path = new Path();
//        String current = dst;
//        while (current != null) {
//            path.addNode(current);
//            current = parentMap.get(current);
//        }
//        Collections.reverse(path.getNodes());
//        return path;
//    }

    // Part 3: Path class to represent a path from src to dst
    public static class Path { // Refactor 2: made static so does not depend on an instance of the outer class
        private List<String> nodes = new ArrayList<>();

        public void addNode(String node) {
            nodes.add(node);
        }

        public List<String> getNodes() {
            return nodes;
        }

        @Override
        public String toString() {
            return String.join(" -> ", nodes);
        }
    }
}
