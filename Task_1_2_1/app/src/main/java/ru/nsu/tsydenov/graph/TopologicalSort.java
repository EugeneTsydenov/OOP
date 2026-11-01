package ru.nsu.tsydenov.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Topologicals sorting with Kahn algorithm
 * A topological ordering exists only if the graph is acyclic
 */
public class TopologicalSort implements GraphSorter {
    @Override
    public List<String> sort(Graph graph) {
        Map<String, Integer> inDegree = new HashMap<>();

        for (String vertex : graph.getVertices()) {
            inDegree.put(vertex, 0);
        }

        for (String vertex : graph.getVertices()) {
            for (String neighbor : graph.getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<String> queue = new LinkedList<>();

        for (String vertex : graph.getVertices()) {
            if (inDegree.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        List<String> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            result.add(vertex);

            for (String neighbor : graph.getNeighbors(vertex)) {
                int degree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, degree);

                if (degree == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != graph.vertexCount()) {
            throw new IllegalArgumentException("Graph contains a cycle");
        }

        return result;
    }
}
