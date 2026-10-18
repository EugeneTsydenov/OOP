package ru.nsu.tsydenov.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Directed graph implementation based on an incidence matrix.
 *
 * Rows represent vertices, and columns represent edges
 */
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
        if (!hasVertex(vertex)) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        Integer idx = vertexIndices.get(vertex);

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
        if (hasVertex(vertex)) {
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
        if (!hasVertex(vertex)) {
            return false;
        }

        Integer idx = vertexIndices.get(vertex);

        List<Integer> row = incidenceMatrix.get(idx);

        for (int i = row.size() - 1; i >= 0; i--) {
            if (row.get(i) == 0) {
                continue;
            }

            for (List<Integer> matrixRow : incidenceMatrix) {
                matrixRow.remove(i);
            }
        }

        incidenceMatrix.remove((int) idx);
        vertices.remove((int) idx);
        vertexIndices.remove(vertex);

        for (int i = idx; i < vertices.size(); i++) {
            vertexIndices.put(vertices.get(i), i);
        }

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
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }

        if (hasEdge(from, to)) {
                return false;
        }

        Integer fromIdx = vertexIndices.get(from);
        Integer toIdx = vertexIndices.get(to);

        for (int i = 0; i < vertices.size(); i++) {
            if (fromIdx == i) {
                incidenceMatrix.get(fromIdx).add(fromIdx == toIdx ? 2 : -1);
                continue;
            }

            if (toIdx == i) {
                incidenceMatrix.get(toIdx).add(1);
                continue;
            }

            incidenceMatrix.get(i).add(0);
        }

        return true;
    }

    @Override
    public boolean removeEdge(String from, String to) {
        if (!hasEdge(from, to)) {
            return false;
        }

        Integer fromIdx = vertexIndices.get(from);
        Integer toIdx = vertexIndices.get(to);

        List<Integer> fromRow = incidenceMatrix.get(fromIdx);
        int found = -1;

        for (int i = 0; i < fromRow.size(); i++) {
            if (fromIdx.equals(toIdx) && fromRow.get(i) == 2) {
                found = i;
                break;
            }

            if (!fromIdx.equals(toIdx)
                    && fromRow.get(i) == -1
                    && incidenceMatrix.get(toIdx).get(i) == 1) {
                found = i;
                break;
            }
        }

        if (found == -1) {
            return false;
        }

        for (List<Integer> row : incidenceMatrix) {
            row.remove(found);
        }

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

        if (fromIdx == null || toIdx == null) {
            return false;
        }

        List<Integer> fromRow = incidenceMatrix.get(fromIdx);

        for (int i = 0; i < fromRow.size(); i++) {
            if (fromRow.get(i) == -1
                    && incidenceMatrix.get(toIdx).get(i) == 1)
                return true;

            if (fromIdx.equals(toIdx) && fromRow.get(i) == 2)
                return true;
        }

        return false;
    }

    @Override
    public int vertexCount() {
        return vertices.size();
    }

    @Override
    public int edgeCount() {
        return incidenceMatrix.isEmpty()
                ? 0
                : incidenceMatrix.get(0).size();
    }

    @Override
    public void clear() {
        vertices.clear();
        incidenceMatrix.clear();
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
