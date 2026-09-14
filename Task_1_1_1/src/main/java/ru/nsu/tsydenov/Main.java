package ru.nsu.tsydenov;

/**
 * Entry point for testing and running the HeapSort demonstration.
 */
public class Main {

    /**
     * Main method that runs the sorting example.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        int[] arr = {5, 2, 3, 1, 4};
        HeapSort.execute(arr);
    }
}