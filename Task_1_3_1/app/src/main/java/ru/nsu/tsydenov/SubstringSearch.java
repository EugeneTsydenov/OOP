package ru.nsu.tsydenov;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Finds all occurrences of a substring in a UTF-8 file.
 */
public class SubstringSearch {

    /**
     * Finds the start index of each occurrence.
     *
     * @param fileName the name of the file
     * @param pattern the substring to find
     * @return the list of start indices
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if the pattern is empty
     */
    public static List<Long> find(String fileName, String pattern)
            throws IOException {
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern must not be empty");
        }

        int[] target = pattern.codePoints().toArray();
        int[] prefix = buildPrefix(target);

        List<Long> result = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(fileName), StandardCharsets.UTF_8)) {

            long index = 0;
            int matched = 0;
            int ch;

            while ((ch = readCodePoint(reader)) != -1) {
                while (matched > 0 && ch != target[matched]) {
                    matched = prefix[matched - 1];
                }

                if (ch == target[matched]) {
                    matched++;
                }

                if (matched == target.length) {
                    result.add(index - target.length + 1);
                    matched = prefix[matched - 1];
                }

                index++;
            }
        }

        return result;
    }

    /**
     * Builds the prefix array for the pattern.
     *
     * @param pattern the pattern as Unicode code points
     * @return the prefix array
     */
    private static int[] buildPrefix(int[] pattern) {
        int[] prefix = new int[pattern.length];
        int matched = 0;

        for (int i = 1; i < pattern.length; i++) {
            while (matched > 0 && pattern[i] != pattern[matched]) {
                matched = prefix[matched - 1];
            }

            if (pattern[i] == pattern[matched]) {
                matched++;
            }

            prefix[i] = matched;
        }

        return prefix;
    }

    /**
     * Reads one Unicode code point from the file.
     *
     * @param reader the file reader
     * @return the code point, or -1 at the end of the file
     * @throws IOException if reading fails
     */
    private static int readCodePoint(BufferedReader reader) throws IOException {
        int first = reader.read();

        if (first == -1) {
            return -1;
        }

        if (Character.isHighSurrogate((char) first)) {
            reader.mark(1);
            int second = reader.read();

            if (second != -1 && Character.isLowSurrogate((char) second)) {
                return Character.toCodePoint((char) first, (char) second);
            }

            if (second != -1) {
                reader.reset();
            }
        }

        return first;
    }
}
