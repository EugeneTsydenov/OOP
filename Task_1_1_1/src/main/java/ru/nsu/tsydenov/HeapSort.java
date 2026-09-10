package ru.nsu.tsydenov;

public class HeapSort {
    public static void execute(int[] arr) {
        buildHeap(arr, arr.length);
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static void buildHeap(int[] arr, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
    }

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

    private static void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }
}

