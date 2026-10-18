package ru.nsu.tsydenov.graph;

import java.util.HashSet;
import java.util.Set;

public class AdjacencyMatrixGraph implements Graph {
    private boolean[][] adjacencyMatrix = new boolean[5][5];
    private String[] vertices = new String[5];
    private int vertexCount;
    private int edgeCount;

    @Override
    public Set<String> getVertices() {
        Set<String> result = new HashSet<>();

        for (int i = 0; i < vertexCount; i++) {
            result.add(vertices[i]);
        }

        return result;
    }

    @Override
    public Set<String> getNeighbors(String vertex) {
        int idx = getIdx(vertex);

        if (idx == -1) {
            throw new IllegalArgumentException("Vertex not exists");
        }

        boolean[] a = adjacencyMatrix[idx];
        Set<String> neighbors = new HashSet<>();
        for (int i = 0; i < vertexCount; i++) {
            if (a[i]) {
                neighbors.add(vertices[i]);
            }
        }

        return neighbors;
    }

    private int getIdx(String vertex) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].equals(vertex)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean addVertex(String vertex) {
        if (vertex == null) {
            return false;
        }

        int idx = getIdx(vertex);
        if (idx != -1) {
            return false;
        }

        if (vertices.length == vertexCount) {
            expandGrapth();
        }

        vertices[vertexCount] = vertex;
        vertexCount++;
        return true;
    }

    private void expandGrapth() {
        expandVertices();
        expandMatrix();
    }

    private void expandVertices() {
        String[] newVertices = new String[vertices.length*2];
        for (int i = 0; i < vertexCount; i++) {
            newVertices[i] = vertices[i];
        }
        vertices = newVertices;
    }

    private void expandMatrix() {
        boolean[][] newMatrix = new boolean[adjacencyMatrix.length*2][adjacencyMatrix.length*2];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                newMatrix[i][j] = adjacencyMatrix[i][j];
            }
        }
        adjacencyMatrix = newMatrix;
    }

    @Override
    public boolean removeVertex(String vertex) {
        return false;
    }

    @Override
    public boolean addEdge(String from, String to) {
        int fromIdx = getIdx(from);
        int toIdx = getIdx(to);

        if (fromIdx == -1 || toIdx == -1) {
            return false;
        }

        if (adjacencyMatrix[fromIdx][toIdx]) {
            return false;
        }

        adjacencyMatrix[fromIdx][toIdx] = true;
        edgeCount++;

        return true;
    }

    @Override
    public boolean removeEdge(String from, String to) {
        int fromIdx = getIdx(from);
        int toIdx = getIdx(to);

        if (fromIdx == -1 || toIdx == -1) {
            return false;
        }

        if (!adjacencyMatrix[fromIdx][toIdx]) {
            return false;
        }

        adjacencyMatrix[fromIdx][toIdx] = false;
        edgeCount--;

        return true;
    }

    @Override
    public boolean hasVertex(String vertex) {
        return getIdx(vertex) != -1;
    }

    @Override
    public boolean hasEdge(String from, String to) {
        int fromIdx = getIdx(from);
        int toIdx = getIdx(to);

        if (fromIdx == -1 || toIdx == -1) {
            return false;
        }

        return adjacencyMatrix[fromIdx][toIdx];
    }

    @Override
    public int vertexCount() {
        return vertexCount;
    }

    @Override
    public int edgeCount() {
        return edgeCount;
    }

    @Override
    public void clear() {
        vertexCount = 0;
        edgeCount = 0;
        vertices = new String[5];
        adjacencyMatrix = new boolean[5][5];
    }
}
