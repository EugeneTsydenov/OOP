package ru.nsu.tsydenov.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdjacencyListGraphTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph();
    }

    @Test
    void testAddVertex_NewVertex() {
        assertTrue(graph.addVertex("A"));
        assertTrue(graph.hasVertex("A"));
        assertEquals(1, graph.vertexCount());
    }

    @Test
    void testAddVertex_DuplicateVertex() {
        graph.addVertex("A");
        assertFalse(graph.addVertex("A"));
        assertEquals(1, graph.vertexCount());
    }

    @Test
    void testRemoveVertex_Existing() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertTrue(graph.removeVertex("A"));
        assertFalse(graph.hasVertex("A"));
        assertEquals(1, graph.vertexCount());
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void testRemoveVertex_NotExisting() {
        assertFalse(graph.removeVertex("X"));
    }

    @Test
    void testGetNeighbors_NotExistingVertex_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            graph.getNeighbors("X");
        });
    }

    @Test
    void testAddEdge_Success() {
        graph.addVertex("A");
        graph.addVertex("B");

        assertTrue(graph.addEdge("A", "B"));
        assertTrue(graph.hasEdge("A", "B"));
        assertEquals(1, graph.edgeCount());
    }

    @Test
    void testAddEdge_DuplicateEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertFalse(graph.addEdge("A", "B"));
        assertEquals(1, graph.edgeCount());
    }

    @Test
    void testAddEdge_MissingVertices() {
        graph.addVertex("A");
        assertFalse(graph.addEdge("A", "B"));
        assertFalse(graph.addEdge("B", "A"));
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void testRemoveEdge_Existing() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertTrue(graph.removeEdge("A", "B"));
        assertFalse(graph.hasEdge("A", "B"));
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void testRemoveEdge_NotExisting() {
        graph.addVertex("A");
        graph.addVertex("B");

        assertFalse(graph.removeEdge("A", "B"));
    }

    @Test
    void testRemoveEdge_MissingVertices() {
        assertFalse(graph.removeEdge("X", "Y"));
    }

    @Test
    void testHasEdge_MissingSourceVertex() {
        graph.addVertex("B");
        assertFalse(graph.hasEdge("A", "B"));
    }

    @Test
    void testRemoveVertex_CleansUpEdgesInNeighbors() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        graph.removeVertex("B");

        Set<String> neighborsOfA = graph.getNeighbors("A");
        assertFalse(neighborsOfA.contains("B"));
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void testClear() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        graph.clear();

        assertEquals(0, graph.vertexCount());
        assertEquals(0, graph.edgeCount());
        assertFalse(graph.hasVertex("A"));
    }
}
