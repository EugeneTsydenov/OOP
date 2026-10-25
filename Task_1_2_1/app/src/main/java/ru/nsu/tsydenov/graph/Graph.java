package ru.nsu.tsydenov.graph;

import java.util.Set;

/**
 * Defines operations for a directed graph.
 */
public interface Graph {
    /**
     * Returns all vertices of the graph.
     *
     * @return set of unique vertices
     */
    Set<String> getVertices();

    /**
     * Returns all unique neighbors of the specified vertex.
     *
     * @param vertex target vertex
     * @return set of unique neighbors
     * @throws IllegalArgumentException if the vertex does not exist in the graph
     */
    Set<String> getNeighbors(String vertex);

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex vertex to add
     * @return true if the vertex was added, false if it already exists
     */
    boolean addVertex(String vertex);

    /**
     * Removes a vertex from the graph.
     *
     * @param vertex vertex to remove
     * @return true if the vertex was removed, false if it does not exist
     */
    boolean removeVertex(String vertex);

    /**
     * Returns all edges of the graph.
     *
     * @return set of unique edges
     */
    Set<String> getEdges();

    /**
     * Adds an edge to the graph.
     *
     * @param from source vertex
     * @param to destination vertex
     * @return true if the edge was added, false if it could not be added
     */
    boolean addEdge(String from, String to);

    /**
     * Removes an edge from the graph.
     *
     * @param from source vertex
     * @param to destination vertex
     * @return true if the edge was removed, false if it does not exist
     */
    boolean removeEdge(String from, String to);

    /**
     * Determines whether a vertex exists in the graph.
     *
     * @param vertex vertex to check
     * @return true if the vertex exists, false otherwise
     */
    boolean hasVertex(String vertex);

    /**
     * Determines whether an edge exists in the graph.
     *
     * @param from source vertex
     * @param to destination vertex
     * @return true if the edge exists, false otherwise
     */
    boolean hasEdge(String from, String to);

    /**
     * Returns the number of vertices in the graph.
     *
     * @return number of vertices
     */
    int vertexCount();

    /**
     * Returns the number of edges in the graph.
     *
     * @return number of edges
     */
    int edgeCount();

    /**
     * Clears the graph.
     */
    void clear();
}
