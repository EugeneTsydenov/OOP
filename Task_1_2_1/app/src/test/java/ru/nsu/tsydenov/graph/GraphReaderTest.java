package ru.nsu.tsydenov.graph;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class GraphReaderTest {
    private final GraphReader graphReader = new GraphReader();

    @Test
    void testReadGraph_Success() throws IOException {
        Graph graph = new AdjacencyListGraph();
        String input = """
        A
        B
        C
        ---
        A B
        A C
        B C
        """;

        graphReader.read(graph, new BufferedReader(new StringReader(input)));

        assertEquals(3, graph.vertexCount());
        assertEquals(3, graph.edgeCount());

        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));
        assertTrue(graph.hasVertex("C"));

        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("A", "C"));
        assertTrue(graph.hasEdge("B", "C"));
    }

    @Test
    void testReadGraph_WithoutEdges() throws IOException {
        Graph graph = new AdjacencyListGraph();
        String input = """
        A
        B
        ---
        """;

        graphReader.read(graph, new BufferedReader(new StringReader(input)));

        assertEquals(2, graph.vertexCount());
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void testReadGraph_EmptyLines() throws IOException {
        Graph graph = new AdjacencyListGraph();
        String input = """
        A

        B
        ---

        A B

        """;

        graphReader.read(graph, new BufferedReader(new StringReader(input)));

        assertEquals(2, graph.vertexCount());
        assertEquals(1, graph.edgeCount());
        assertTrue(graph.hasEdge("A", "B"));
    }

    @Test
    void testReadGraph_SelfLoop() throws IOException {
        Graph graph = new AdjacencyListGraph();
        String input = """
        A
        ---
        A A
        """;

        graphReader.read(graph, new BufferedReader(new StringReader(input)));

        assertEquals(1, graph.vertexCount());
        assertEquals(1, graph.edgeCount());
        assertTrue(graph.hasEdge("A", "A"));
    }

    @Test
    void testReadGraph_DuplicateVertex_ThrowsException() {
        Graph graph = new AdjacencyListGraph();
        String input = """
        A
        A
        ---
        """;

        assertThrows(IllegalArgumentException.class, () ->
            graphReader.read(graph, new BufferedReader(new StringReader(input)))
        );
    }

    @Test
    void testReadGraph_DuplicateEdge_ThrowsException() {
        Graph graph = new AdjacencyListGraph();
        String input = """
        A
        B
        ---
        A B
        A B
        """;

        assertThrows(IllegalArgumentException.class, () ->
            graphReader.read(graph, new BufferedReader(new StringReader(input)))
        );
    }

    @Test
    void testReadGraph_UnknownVertex_ThrowsException() {
        Graph graph = new AdjacencyListGraph();
        String input = """
        A
        ---
        A B
        """;

        assertThrows(IllegalArgumentException.class, () ->
            graphReader.read(graph, new BufferedReader(new StringReader(input)))
        );
    }

    @Test
    void testReadGraph_InvalidEdgeFormat_ThrowsException() {
        Graph graph = new AdjacencyListGraph();
        String input = """
        A
        B
        C
        ---
        A B C
        """;

        assertThrows(IllegalArgumentException.class, () ->
            graphReader.read(graph, new BufferedReader(new StringReader(input)))
        );
    }

    @Test
    void testReadGraph_MissingSeparator_ThrowsException() {
        Graph graph = new AdjacencyListGraph();
        String input = """
        A
        B
        """;

        assertThrows(IllegalArgumentException.class, () ->
            graphReader.read(graph, new BufferedReader(new StringReader(input)))
        );
    }

    @Test
    void testReadGraph_DuplicateSeparator_ThrowsException() {
        Graph graph = new AdjacencyListGraph();
        String input = """
        A
        ---
        ---
        """;

        assertThrows(IllegalArgumentException.class, () ->
            graphReader.read(graph, new BufferedReader(new StringReader(input)))
        );
    }
}
