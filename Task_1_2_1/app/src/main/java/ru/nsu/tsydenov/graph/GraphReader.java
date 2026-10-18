package ru.nsu.tsydenov.graph;

import java.io.BufferedReader;
import java.io.IOException;

public class GraphReader {
    public void read(Graph graph, BufferedReader reader) throws IOException {
        boolean readingEdges = false;
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.equals("---")) {
                if (readingEdges) {
                    throw new IllegalArgumentException(
                            "Duplicate section separator"
                    );
                }

                readingEdges = true;
                continue;
            }

            if (readingEdges) {
                String[] vertices = line.split("\\s+");

                if (vertices.length != 2) {
                    throw new IllegalArgumentException(
                            "Invalid edge format: " + line
                    );
                }

                String from = vertices[0];
                String to = vertices[1];

                if (!graph.addEdge(from, to)) {
                    throw new IllegalArgumentException(
                            "Duplicate edge: " + line
                    );
                }
            } else {
                if (line.contains(" ")) {
                    throw new IllegalArgumentException(
                            "Invalid vertex format: " + line
                    );
                }

                if (!graph.addVertex(line)) {
                    throw new IllegalArgumentException(
                            "Duplicate vertex: " + line
                    );
                }
            }
        }

        if (!readingEdges) {
            throw new IllegalArgumentException(
                    "Missing section separator ---"
            );
        }
    }
}
