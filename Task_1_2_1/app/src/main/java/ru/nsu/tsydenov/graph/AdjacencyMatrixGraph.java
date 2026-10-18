package ru.nsu.tsydenov.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Directed graph implementation based on an adjacency matrix.
 * A matrix cell indicates whether an edge exists between
 * two vertices
 */
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
        if (!hasVertex(vertex)) {
            throw new IllegalArgumentException("Vertex not exists");
        }

        Integer idx = vertexIndices.get(vertex);

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
        if (hasVertex(vertex)) {
            return false;
        }

        int newIdx = vertices.size();
        vertices.add(vertex);
        vertexIndices.put(vertex, newIdx);

        for (List<Boolean> row : adjacencyMatrix) {
            row.add(false);
        }

        List<Boolean> newRow = new ArrayList<>(
            Collections.nCopies(vertices.size(), false)
        );
        adjacencyMatrix.add(newRow);

        return true;
    }

    @Override
    public boolean removeVertex(String vertex) {
        if (!hasVertex(vertex)) {
            return false;
        }

        Integer idx = vertexIndices.get(vertex);

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
            if (targetRow.get(i)) {
                edgeCount--;
            }
            if (adjacencyMatrix.get(i).get(idx)) {
                edgeCount--;
            }
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
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }

        if (hasEdge(from, to)) {
            return false;
        }

        Integer fromIdx = vertexIndices.get(from);
        Integer toIdx = vertexIndices.get(to);

        if (adjacencyMatrix.get(fromIdx).get(toIdx)) {
            return false;
        }

        adjacencyMatrix.get(fromIdx).set(toIdx, true);
        edgeCount++;

        return true;
    }

    @Override
    public boolean removeEdge(String from, String to) {
        if (!hasEdge(from, to)) {
            return false;
        }

        Integer fromIdx = vertexIndices.get(from);
        Integer toIdx = vertexIndices.get(to);

        adjacencyMatrix.get(fromIdx).set(toIdx, false);
        edgeCount--;

        return true;
    }

    @Override
    public boolean hasVertex(String vertex) {
        if (vertex == null) {
            return false;
        }
        return vertexIndices.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(String from, String to) {
        if (from == null || to == null) {
            return false;
        }
        Integer fromIdx = vertexIndices.get(from);
        Integer toIdx = vertexIndices.get(to);

        return (
            fromIdx != null
            && toIdx != null
            && adjacencyMatrix.get(fromIdx).get(toIdx)
        );
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
