package ru.nsu.tsydenov.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

abstract class GraphTest {
    protected Graph graph;

    protected abstract Graph createGraph();

    @BeforeEach
    protected void setUp() {
        graph = createGraph();
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

    @Test
    void testRemoveVertex_RemovesAllIncidentEdges() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "A");
        graph.addEdge("B", "C");
        graph.addEdge("B", "B");

        assertEquals(4, graph.edgeCount());

        assertTrue(graph.removeVertex("B"));

        assertEquals(2, graph.vertexCount());
        assertEquals(0, graph.edgeCount());

        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "A"));
        assertFalse(graph.hasEdge("B", "C"));
        assertFalse(graph.hasVertex("B"));
    }

    @Test
    void testRemoveVertex_UpdatesIndices() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("A", "C");

        assertTrue(graph.removeVertex("B"));

        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("C"));
        assertTrue(graph.hasEdge("A", "C"));

        assertEquals(Set.of("C"), graph.getNeighbors("A"));
    }

    @Test
    void testEdges_AreDirected() {
        graph.addVertex("A");
        graph.addVertex("B");

        assertTrue(graph.addEdge("A", "B"));

        assertTrue(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "A"));

        assertEquals(Set.of("B"), graph.getNeighbors("A"));
        assertEquals(Set.of(), graph.getNeighbors("B"));
    }

    @Test
    void testSelfLoop() {
        graph.addVertex("A");

        assertTrue(graph.addEdge("A", "A"));
        assertEquals(1, graph.edgeCount());
        assertTrue(graph.hasEdge("A", "A"));

        assertTrue(graph.removeEdge("A", "A"));
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void testGetVertices_ReturnsIndependentSet() {
        graph.addVertex("A");

        Set<String> vertices = graph.getVertices();
        vertices.add("B");

        assertFalse(graph.hasVertex("B"));
        assertEquals(1, graph.vertexCount());
    }

    @Test
    void testGetNeighbors_ReturnsIndependentSet() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        Set<String> neighbors = graph.getNeighbors("A");
        neighbors.clear();

        assertTrue(graph.hasEdge("A", "B"));
        assertEquals(Set.of("B"), graph.getNeighbors("A"));
    }

    @Test
    void testClear_AllowsReuse() {
        graph.addVertex("A");
        graph.addEdge("A", "A");

        graph.clear();

        assertTrue(graph.addVertex("B"));
        assertTrue(graph.addVertex("C"));
        assertTrue(graph.addEdge("B", "C"));

        assertEquals(2, graph.vertexCount());
        assertEquals(1, graph.edgeCount());
        assertTrue(graph.hasEdge("B", "C"));
    }
}
