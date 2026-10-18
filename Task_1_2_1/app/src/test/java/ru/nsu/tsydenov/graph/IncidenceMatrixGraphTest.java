package ru.nsu.tsydenov.graph;

class IncidenceMatrixGraphTest extends GraphTest {
    @Override
    protected Graph createGraph() {
        return new IncidenceMatrixGraph();
    }
}
