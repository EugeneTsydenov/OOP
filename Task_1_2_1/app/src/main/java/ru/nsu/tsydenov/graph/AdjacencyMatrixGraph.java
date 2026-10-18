package ru.nsu.tsydenov.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdjacencyMatrixGraph implements Graph {
    private final List<List<Boolean>> adjacencyMatrix = new ArrayList<>();
    private final List<String> vertices = new ArrayList<>();
    private final Map<String, Integer> vertexIndices = new HashMap<>();
    private int edgeCount;

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

        Set<String> neighbors = new HashSet<>();
        List<Boolean> row = adjacencyMatrix.get(idx);
        for (int i = 0; i < vertices.size(); i++) {
            if (row.get(i)) {
                neighbors.add(vertices.get(i));
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

        for (List<Boolean> row : adjacencyMatrix) {
            row.add(false);
        }

        List<Boolean> newRow = new ArrayList<>(Collections.nCopies(vertices.size(), false));
        adjacencyMatrix.add(newRow);

        return true;
    }

    @Override
    public boolean removeVertex(String vertex) {
        if (vertex == null) return false;

        Integer idx = vertexIndices.get(vertex);
        if (idx == null) return false;

        decrementEdgeCountForVertex(idx);
        vertexIndices.remove(vertex);
        physicalRemoveRowAndColumn(idx);
        updateShiftedVertexIndices(idx);

        return true;
    }

    private void decrementEdgeCountForVertex(int idx) {
        List<Boolean> targetRow = adjacencyMatrix.get(idx);
        int size = vertices.size();

        for (int i = 0; i < size; i++) {
            if (targetRow.get(i)) edgeCount--;
            if (adjacencyMatrix.get(i).get(idx)) edgeCount--;
        }

        if (targetRow.get(idx)) {
            edgeCount++;
        }
    }

    private void physicalRemoveRowAndColumn(int idx) {
        vertices.remove(idx);

        for (List<Boolean> row : adjacencyMatrix) {
            row.remove(idx);
        }

        adjacencyMatrix.remove(idx);
    }

    private void updateShiftedVertexIndices(int startIdx) {
        for (int i = startIdx; i < vertices.size(); i++) {
            vertexIndices.put(vertices.get(i), i);
        }
    }


    @Override
    public boolean addEdge(String from, String to) {
        if (from == null || to == null) {
            return false;
        }
        Integer fromIdx = vertexIndices.get(from);
        Integer toIdx = vertexIndices.get(to);

        if (fromIdx == null || toIdx == null) {
            return false;
        }

        if (adjacencyMatrix.get(fromIdx).get(toIdx)) {
            return false;
        }

        adjacencyMatrix.get(fromIdx).set(toIdx, true);
        edgeCount++;

        return true;
    }

    @Override
    public boolean removeEdge(String from, String to) {
        if (from == null || to == null) {
            return false;
        }
        Integer fromIdx = vertexIndices.get(from);
        Integer toIdx = vertexIndices.get(to);

        if (fromIdx == null || toIdx == null || !adjacencyMatrix.get(fromIdx).get(toIdx)) {
            return false;
        }

        adjacencyMatrix.get(fromIdx).set(toIdx, false);
        edgeCount--;

        return true;
    }

    @Override
    public boolean hasVertex(String vertex) {
        if (vertex == null) return false;
        return vertexIndices.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(String from, String to) {
        if (from == null || to == null) return false;
        Integer fromIdx = vertexIndices.get(from);
        Integer toIdx = vertexIndices.get(to);

        return (fromIdx != null && toIdx != null && adjacencyMatrix.get(fromIdx).get(toIdx));
    }

    @Override
    public int vertexCount() {
        return vertices.size();
    }

    @Override
    public int edgeCount() {
        return edgeCount;
    }

    @Override
    public void clear() {
        edgeCount = 0;
        vertices.clear();
        adjacencyMatrix.clear();
        vertexIndices.clear();
    }
}
