package ru.nsu.tsydenov.graph;

import java.util.Set;

public interface Graph {
   /**
    * Returns all vertices of a graph
    *
    * @return list of unique vertices
    */
   Set<String> getVertices();

   /**
    * Returns list of unique neighbors of the target vertex
    *
    * @param vertex target vertex
    * @throws IllegalArgumentException if the vertex not exist in the graph
    * @return list of unique neighbors
    */
   Set<String> getNeighbors(String vertex);


   /**
    * Add vertex to a graph
    *
    * @param vertex that need to be added
    * @return true if the vertex added, false if the vertex exists in a graph
    * */
   boolean addVertex(String vertex);

   /**
    * Remove vertex from a graph
    *
    * @param vertex that need to be removed
    * @return true if the vertex removed, false if the vertex not exists in a graph
    */
   boolean removeVertex(String vertex);

   /**
    * Add edge to a graph
    *
    * @param from vertex from which an edge originates
    * @param to vertex into which an edge enters
    * @return true if the edge added, false if the edge cannot be added
    */
   boolean addEdge(String from, String to);

   /**
    * Remove edge from a graph
    *
    * @param from vertex from which an edge originates
    * @param to vertex into which an edge enters
    * @return true if an edge removed, false if the edge not exists in a graph
    */
   boolean removeEdge(String from, String to);

   /**
    * Determines whether a vertex exists in the graph
    *
    * @param vertex that needs to be checked
    * @return true if the grapth has vertex, false if the graph hasnt vertex
    */
   boolean hasVertex(String vertex);


   /**
    * Determines whether an edge exists in the graph
    *
    * @param from vertex from which an edge originates
    * @param to vertex into which an edge enters
    * @return true if the grapth has edge, false if the graph hasnt edge
    */
   boolean hasEdge(String from, String to);

   /**
    * Calculates the number of vertices in a graph
    *
    * @return count vertices in the grapth
    */
   int vertexCount();

   /**
    * Calculates the number of edges in a graph
    *
    * @return count edges in the grapth
    */
   int edgeCount();

   /**
    * clear grapth
    */
   void clear();
}
