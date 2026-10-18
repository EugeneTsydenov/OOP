package ru.nsu.tsydenov.graph;

import java.util.List;

/**
 * Defines an algorithm for sorting graph vertices.
 */
public interface GraphSorter {
    /**
     * Returns the vertices of a graph in the order defined by the algorithm.
     *
     * @param graph graph to sort
     * @return list of vertices in the resulting order
     */
    List<String> sort(Graph graph);
}
