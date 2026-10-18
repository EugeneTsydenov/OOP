package ru.nsu.tsydenov.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GraphEqualityTest {
    @Test
    void testEquals_DifferentRepresentations() {
        final Graph listGraph = new AdjacencyListGraph();
        final Graph matrixGraph = new AdjacencyMatrixGraph();

        listGraph.addVertex("A");
        listGraph.addVertex("B");
        listGraph.addEdge("A", "B");

        matrixGraph.addVertex("A");
        matrixGraph.addVertex("B");
        matrixGraph.addEdge("A", "B");

        assertEquals(listGraph, matrixGraph);
        assertEquals(listGraph.hashCode(), matrixGraph.hashCode());
    }

    @Test
    void testEquals_DifferentEdges() {
        Graph first = new AdjacencyListGraph();
        Graph second = new AdjacencyListGraph();

        first.addVertex("A");
        first.addVertex("B");
        first.addEdge("A", "B");

        second.addVertex("A");
        second.addVertex("B");

        assertNotEquals(first, second);
    }

    @Test
    void testEquals_SameObject() {
        Graph graph = new AdjacencyListGraph();

        assertEquals(graph, graph);
    }

    @Test
    void testToString() {
        Graph graph = new AdjacencyListGraph();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        String result = graph.toString();

        assertTrue(result.contains("Vertices:"));
        assertTrue(result.contains("Edges:"));
        assertTrue(result.contains("A -> B"));
    }
}
