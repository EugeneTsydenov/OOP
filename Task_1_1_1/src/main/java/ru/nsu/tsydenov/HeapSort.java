package ru.nsu.tsydenov;

/**
 * Utility class that provides the HeapSort algorithm to sort integer arrays in ascending order.
 * <p>
 * The algorithm has a time complexity of {@code O(n log n)} and a space complexity of {@code O(1)}.
 *
 * @author TsydenovEugene
 * @version 1.0
 * @since 1.0
 */
public class HeapSort {

    /**
     * Sorts the given integer array in ascending order in place.
     *
     * @param arr the array to be sorted
     */
    public static void execute(int[] arr) {
        buildHeap(arr, arr.length);
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    /**
     * Rearranges the array elements to build an initial max-heap in place.
     *
     * @param arr the input array
     * @param n length of the array
    */
    private static void buildHeap(int[] arr, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
    }

    /**
     * Fixes the heap structure for a subtree rooted at the given index.
     *
     * @param arr the array representing the heap
     * @param n current size of the heap
     * @param root index of the subtree root
     */
    private static void heapify(int[] arr, int n, int root) {
        int largest = root;
        int left = 2*root+1;
        int right = 2*root+2;

        if (left >= n && right >= n) {
            return;
        }

        if (left < n && arr[largest] < arr[left]) {
            largest = left;
        }
        if (right < n && arr[largest] < arr[right]) {
            largest = right;
        }

        if (largest != root) {
            swap(arr, largest, root);
            heapify(arr, n, largest);
        }
    }

    /**
     * Swaps two numbers in the array by their indices.
     *
     * @param arr the target array
     * @param first index of the first element
     * @param second index of the second element
     */
    private static void swap(int[] arr, int first, int second) {
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }
}

