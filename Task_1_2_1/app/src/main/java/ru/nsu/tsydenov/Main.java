package ru.nsu.tsydenov;

import ru.nsu.tsydenov.graph.AdjacencyListGraph;
import ru.nsu.tsydenov.graph.AdjacencyMatrixGraph;
import ru.nsu.tsydenov.graph.Graph;
import ru.nsu.tsydenov.graph.GraphSorter;
import ru.nsu.tsydenov.graph.IncidenceMatrixGraph;
import ru.nsu.tsydenov.graph.TopologicalSort;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Graph> graphs = List.of(
                new AdjacencyListGraph(),
                new AdjacencyMatrixGraph(),
                new IncidenceMatrixGraph()
        );

        GraphSorter sorter = new TopologicalSort();

        for (Graph graph : graphs) {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");

            graph.addEdge("A", "B");
            graph.addEdge("A", "C");
            graph.addEdge("B", "D");
            graph.addEdge("C", "D");

            System.out.printf("%s\n", graph.getClass().getSimpleName());
            System.out.printf("%s\n", graph);

            System.out.printf("Vertices: %s\n", graph.vertexCount());
            System.out.printf("Edges: %s\n", graph.edgeCount());
            System.out.printf("A -> B exists: %s\n", graph.hasEdge("A", "B"));
            System.out.printf("Neighbors of A: %s\n", graph.getNeighbors("A"));

            System.out.printf("Topological sort: %s\n", sorter.sort(graph));
        }

        Graph first = graphs.get(0);
        Graph second = graphs.get(1);
        Graph third = graphs.get(2);

        System.out.printf("List equals matrix: %s\n", first.equals(second));
        System.out.printf("List equals incidence matrix: %s", first.equals(third));
    }
}
