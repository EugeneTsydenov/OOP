package ru.nsu.tsydenov.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Directed graph implementation based on an adjacency list
 *
 * Each vertex is associated with a set of its outgoing neighbors.
 * Self-loops are supported
 */
public class AdjacencyListGraph implements Graph {
    private final Map<String, Set<String>> adjacencyList = new HashMap<String, Set<String>>();

    @Override
    public Set<String> getVertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

    @Override
    public Set<String> getNeighbors(String vertex) {
        if (!hasVertex(vertex)) {
            throw new IllegalArgumentException("Vertex not exists");
        }

        return new HashSet<>(adjacencyList.get(vertex));
    }

    @Override
    public boolean addVertex(String vertex) {
        if(hasVertex(vertex)) return false;
        adjacencyList.put(vertex, new HashSet<>());
        return true;
    }

    @Override
    public boolean removeVertex(String vertex) {
        if (!hasVertex(vertex)) return false;


        for (Set<String> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }

        adjacencyList.remove(vertex);
        return true;
    }

    @Override
    public Set<String> getEdges() {
        Set<String> edges = new HashSet<>();

        for (String from : getVertices()) {
            for (String to : getNeighbors(from)) {
                edges.add(from + " -> " + to);
            }
        }

        return edges;
    }

    @Override
    public boolean addEdge(String from, String to) {
        if (hasEdge(from, to)) return false;
        return adjacencyList.get(from).add(to);
    }

    @Override
    public boolean removeEdge(String from, String to) {
        if (!hasEdge(from, to)) return false;
        return adjacencyList.get(from).remove(to);
    }

    @Override
    public boolean hasVertex(String vertex) {
        return vertex != null && adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(String from, String to) {
        if (from == null || to == null) return false;

        if (!adjacencyList.containsKey(from)) {
            return false;
        }

        return adjacencyList.get(from).contains(to);
    }

    @Override
    public int vertexCount() {
        return adjacencyList.size();
    }

    @Override
    public int edgeCount() {
        return adjacencyList
            .values()
            .stream()
            .mapToInt(Set::size)
            .sum();
    }

    @Override
    public void clear() {
        adjacencyList.clear();
    }

    @Override
    public boolean equals(Object object) {
        return GraphUtils.equals(this, object);
    }

    @Override
    public int hashCode() {
        return GraphUtils.hashCode(this);
    }

    @Override
    public String toString() {
        return GraphUtils.toString(this);
    }
}
