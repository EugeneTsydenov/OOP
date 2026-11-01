package ru.nsu.tsydenov.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class TopologicalSortTest {

    private final TopologicalSort topologicalSort = new TopologicalSort();

    @Test
    void testSort_AcyclicGraph() {
        Graph graph = new AdjacencyListGraph();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        List<String> result = topologicalSort.sort(graph);

        assertEquals(4, result.size());
        assertEquals(0, result.indexOf("A"));
        assertTrue(result.indexOf("B") < result.indexOf("D"));
        assertTrue(result.indexOf("C") < result.indexOf("D"));
    }

    @Test
    void testSort_EmptyGraph() {
        Graph graph = new AdjacencyListGraph();

        assertTrue(topologicalSort.sort(graph).isEmpty());
    }

    @Test
    void testSort_Cycle_ThrowsException() {
        Graph graph = new AdjacencyListGraph();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        assertThrows(IllegalArgumentException.class, () ->
            topologicalSort.sort(graph)
        );
    }

    @Test
    void testSort_SelfLoop_ThrowsException() {
        Graph graph = new AdjacencyListGraph();

        graph.addVertex("A");
        graph.addEdge("A", "A");

        assertThrows(IllegalArgumentException.class, () ->
            topologicalSort.sort(graph)
        );
    }
}
