package ru.nsu.tsydenov.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IncidenceMatrixGraph implements Graph {
    private final List<String> vertices = new ArrayList<>();
    private final List<List<Integer>> incidenceMatrix = new ArrayList<>();
    private final Map<String, Integer> vertexIndices = new HashMap<>();

    @Override
    public Set<String> getVertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Set<String> getNeighbors(String vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }

        Integer idx = vertexIndices.get(vertex);
        if (idx == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        List<Integer> row = incidenceMatrix.get(idx);
        Set<String> neighbors = new HashSet<>();

        for (int i = 0; i < row.size(); i++) {
            if (row.get(i) == 2) {
                neighbors.add(vertex);
                continue;
            }

            if (row.get(i) != -1)
                continue;

            for (int j = 0; j < incidenceMatrix.size(); j++) {
                if (incidenceMatrix.get(j).get(i) == 1) {
                    neighbors.add(vertices.get(j));
                }
            }
        }

        return neighbors;
    }

    @Override
    public boolean addVertex(String vertex) {
        if (vertex == null || vertexIndices.containsKey(vertex)) {
            return false;
        }

        int newIdx = vertices.size();
        vertices.add(vertex);
        vertexIndices.put(vertex, newIdx);

        int edgeCount = incidenceMatrix.isEmpty()
                ? 0
                : incidenceMatrix.get(0).size();

        List<Integer> newRow = new ArrayList<>(Collections.nCopies(edgeCount, 0));
        incidenceMatrix.add(newRow);

        return true;
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
