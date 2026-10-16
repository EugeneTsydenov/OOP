package ru.nsu.tsydenov.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdjacencyListGraph implements Graph {
    private final Map<String, Set<String>> adjacencyList = new HashMap<String, Set<String>>();


    @Override
    public Set<String> getVertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

    @Override
    public Set<String> getNeighbors(String vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex not exists");
        }

        return new HashSet<>(adjacencyList.get(vertex));
    }

    @Override
    public boolean addVertex(String vertex) {
        if (adjacencyList.containsKey(vertex)) {
            return false;
        }

        adjacencyList.put(vertex, new HashSet<>());
        return true;
    }

    @Override
    public boolean removeVertex(String vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            return false;
        }

        for (Set<String> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }

        adjacencyList.remove(vertex);
        return true;
    }

    @Override
    public boolean addEdge(String from, String to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            return false;
        }

        return adjacencyList.get(from).add(to);
    }

    @Override
    public boolean removeEdge(String from, String to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            return false;
        }

        return adjacencyList.get(from).remove(to);
    }

    @Override
    public boolean hasVertex(String vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(String from, String to) {
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
}
