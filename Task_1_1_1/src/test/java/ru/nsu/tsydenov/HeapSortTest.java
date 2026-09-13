package ru.nsu.tsydenov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("HeapSort Algorithm Tests")
class HeapSortTest {
    @Test
    @DisplayName("Test: Standard case")
    void testStandardArray() {
        int[] actual = {5, 2, 3, 1, 4};
        int[] expected = {1, 2, 3, 4, 5};
        Arrays.sort(expected);
        HeapSort.execute(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Empty array")
    void testEmptyArray() {
        int[] actual = {};
        int[] expected = {};
        HeapSort.execute(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Single element array")
    void testSingleElementArray() {
        int[] actual = {42};
        int[] expected = {42};
        HeapSort.execute(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Already sorted array")
    void testAlreadySortedArray() {
        int[] actual = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        HeapSort.execute(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Reverse sorted array")
    void testReverseSortedArray() {
        int[] actual = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        HeapSort.execute(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Array with all identical elements")
    void testIdenticalElementsArray() {
        int[] actual = {7, 7, 7, 7};
        int[] expected = {7, 7, 7, 7};
        HeapSort.execute(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Array with duplicate elements")
    void testDuplicatesArray() {
        int[] actual = {3, 1, 3, 2, 1};
        int[] expected = {1, 1, 2, 3, 3};
        HeapSort.execute(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Array with negative numbers")
    void testNegativeNumbersArray() {
        int[] actual = {-5, -1, -10, 0, 3};
        int[] expected = {-10, -5, -1, 0, 3};
        HeapSort.execute(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Minimum and maximum Integer values")
    void testMinMaxIntegerValues() {
        int[] actual = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        int[] expected = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        HeapSort.execute(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Null input safety check")
    void testNullArray() {
        assertDoesNotThrow(() -> HeapSort.execute(null));
    }
}