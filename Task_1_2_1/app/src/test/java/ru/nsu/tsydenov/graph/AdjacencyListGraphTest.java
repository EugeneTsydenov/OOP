package ru.nsu.tsydenov.graph;

class AdjacencyListGraphTest extends GraphTest {
    @Override
    protected Graph createGraph() {
        return new AdjacencyListGraph();
    }
}
