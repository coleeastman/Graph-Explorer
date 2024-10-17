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

public class GraphParserTest {

    private GraphParser parser;

    @BeforeEach
    public void setUp() {
        parser = new GraphParser();
    }

    // Feature 1 Tests
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
}
