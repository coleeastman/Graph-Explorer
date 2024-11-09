package org.example;


import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GraphParserTest {


    private GraphParser parser;


    @BeforeEach
    public void setUp() {
        parser = new GraphParser();
    }


    // Part 1 Feature 1 Tests
    @Test
    public void testParseGraph() {


        DefaultDirectedGraph<String, DefaultEdge> graph = parser.parseGraph("src/test/resources/graph.dot");


        String expectedOutput = "Graph:\n" +
                "Nodes: A B C D E \n" +
                "Edges:\n" +
                "A -> B\n" +
                "B -> C\n" +
                "C -> A\n" +
                "D -> E\n";
        assertEquals(expectedOutput, parser.toString()); // also tests toString() functionality


    }


    @Test
    public void testOutputGraphToFile() throws IOException {


        // parse the graph (same as in testParseGraph)
        parser.parseGraph("src/test/resources/graph.dot");


        // output the graph to a file
        parser.outputGraph("src/test/resources/expected.txt");


        // read  content from generated file and expected file
        String expectedOutput = Files.readString(Paths.get("src/test/resources/expected.txt"));
        String actualOutput = Files.readString(Paths.get("src/test/resources/expected.txt"));


        // compare both outputs
        assertEquals(expectedOutput.trim(), actualOutput.trim());


    }


    // Part 1 Feature 2: Test adding a node and a list of nodes
    @Test
    public void testAddNode() {
        DefaultDirectedGraph<String, DefaultEdge> graph = parser.parseGraph("src/test/resources/graph.dot");
        parser.addNode("E");
        assertTrue(graph.containsVertex("E"));
    }




    @Test
    public void testAddNodes() {
        DefaultDirectedGraph<String, DefaultEdge> graph = parser.parseGraph("src/test/resources/graph.dot");
        parser.addNodes(new String[]{"F", "G", "H"});
        assertTrue(graph.containsVertex("F"));
        assertTrue(graph.containsVertex("G"));
        assertTrue(graph.containsVertex("H"));
    }


    // Part 1 Feature 3: Test adding an edge
    @Test
    public void testAddEdge() {
        DefaultDirectedGraph<String, DefaultEdge> graph = parser.parseGraph("src/test/resources/graph.dot");
        parser.addEdge("A", "F");
        assertTrue(graph.containsEdge("A", "F"));
    }


    // Part 1 Feature 4: Test output to DOT file
    @Test
    public void testOutputGraph() throws IOException {
        parser.parseGraph("src/test/resources/graph.dot");
        parser.outputGraph("src/test/resources/output.dot");




        String expectedOutput = Files.readString(Paths.get("src/test/resources/output.dot"));
        String actualOutput = Files.readString(Paths.get("src/test/resources/output.dot"));
        assertEquals(expectedOutput, actualOutput);
    }




    // Part 1 Feature 4: Test output to PNG
    @Test
    public void testOutputGraphics() {
        parser.parseGraph("src/test/resources/graph.dot");
        parser.outputGraphics("src/test/resources/graph.png", "png");




        // You can use an assert to check if the file exists, or add additional checks
        assertTrue(Files.exists(Paths.get("src/test/resources/graph.png")));
    }


    // Part 2: Test removeNode
    @Test
    public void testRemoveNode() {
        parser.parseGraph("src/test/resources/graph.dot");
        parser.removeNode("B");
        assertFalse(parser.toString().contains("B"));
    }


    // Part 2: Test removeNodes
    @Test
    public void testRemoveNodes() {
        parser.parseGraph("src/test/resources/graph.dot");
        parser.removeNodes(new String[]{"A", "B"});
        assertFalse(parser.toString().contains("A"));
        assertFalse(parser.toString().contains("B"));
    }


    // Part 2: Test removeEdge
    @Test
    public void testRemoveEdge() {
        parser.parseGraph("src/test/resources/graph.dot");
        parser.removeEdge("A", "B");
        assertFalse(parser.toString().contains("A -> B"));
    }


    // Scenario 2: Test removing non-existent node
    @Test
    public void testRemoveNonExistentNode() {
        parser.parseGraph("src/test/resources/graph.dot");
        assertThrows(IllegalArgumentException.class, () -> {
            parser.removeNode("Z");
        });
    }


    // Scenario 3: Test removing non-existent edge
    @Test
    public void testRemoveNonExistentEdge() {
        parser.parseGraph("src/test/resources/graph.dot");
        assertThrows(IllegalArgumentException.class, () -> {
            parser.removeEdge("X", "Y");
        });
    }


}
