package ru.nsu.tsydenov;

import java.io.IOException;

/**
 * Runs the substring search example.
 */
public class Main {

    /**
     * Runs the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.printf("%s%n",
                    SubstringSearch.find("input.txt", "бра"));
        } catch (IOException e) {
            System.out.printf("Error: %s%n", e.getMessage());
        }
    }
}
