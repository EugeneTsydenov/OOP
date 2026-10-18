package ru.nsu.tsydenov.graph;

import java.util.Objects;

/**
 * Provides utility methods for graph comparison and representation
 */
public final class GraphUtils {
    private GraphUtils() {
    }

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

    public static int hashCode(Graph graph) {
        return Objects.hash(
                graph.getVertices(),
                graph.getEdges()
        );
    }

    public static String toString(Graph graph) {
        StringBuilder result = new StringBuilder();

        result.append("Vertices: ")
                .append(graph.getVertices())
                .append("\nEdges: ")
                .append(graph.getEdges());

        return result.toString();
    }
}
