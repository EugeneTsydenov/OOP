package ru.nsu.tsydenov.graph;

import java.util.Objects;

/**
 * Provides utility methods for graph comparison and representation.
 */
public final class GraphUtils {
    private GraphUtils() {
    }

    /**
     * Compares a graph with another object.
     *
     * @param graph graph to compare
     * @param object object to compare with
     * @return true if the objects represent equal graphs, false otherwise
     */
    public static boolean equals(Graph graph, Object object) {
        if (graph == object) {
            return true;
        }

        if (!(object instanceof Graph other)) {
            return false;
        }

        return graph.getVertices().equals(other.getVertices())
                && graph.getEdges().equals(other.getEdges());
    }

    /**
     * Calculates the hash code of a graph.
     *
     * @param graph graph to calculate the hash code for
     * @return hash code based on the graph's vertices and edges
     */
    public static int hashCode(Graph graph) {
        return Objects.hash(
                graph.getVertices(),
                graph.getEdges()
        );
    }

    /**
     * Returns a string representation of a graph.
     *
     * @param graph graph to represent
     * @return string containing the graph's vertices and edges
     */
    public static String toString(Graph graph) {
        StringBuilder result = new StringBuilder();

        result.append("Vertices: ")
                .append(graph.getVertices())
                .append("\nEdges: ")
                .append(graph.getEdges());

        return result.toString();
    }
}
