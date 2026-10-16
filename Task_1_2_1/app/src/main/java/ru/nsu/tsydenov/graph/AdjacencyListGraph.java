package ru.nsu.tsydenov.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AdjacencyListGraph implements Graph {
    private final Map<String, Set<String>> adjacencyList = new HashMap<String, Set<String>>();


    @Override
    public Set<String> getVertices() {
        return adjacencyList.keySet();
    }

    @Override
    public Set<String> getNeighbors(String vertex) {
        return null;
    }

    @Override
    public boolean addVertex(String vertex) {
        return false;
    }

    @Override
    public boolean removeVertex(String vertex) {
        return false;
    }

    @Override
    public boolean addEdge(String from, String to) {
        return false;
    }

    @Override
    public boolean removeEdge(String from, String to) {
        return false;
    }

    @Override
    public boolean hasVertex(String vertex) {
        return false;
    }

    @Override
    public boolean hasEdge(String from, String to) {
        return false;
    }

    @Override
    public int vertexCount() {
        return 0;
    }

    @Override
    public int edgeCount() {
        return 0;
    }

    @Override
    public void clear() {
    }
}
