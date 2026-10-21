package ru.nsu.tsydenov.parsing;

import java.util.HashMap;
import java.util.Map;

/**
 * Reads variable assignments.
 */
public final class Assignments {
    private Assignments() {
    }

    /**
     * Parses assignments separated by semicolons.
     *
     * @param text assignment text
     * @return parsed values
     */
    public static Map<String, Integer> parse(String text) {
        Map<String, Integer> values = new HashMap<>();
        if (text == null || text.trim().isEmpty()) {
            return values;
        }
        for (String assignment : text.split(";")) {
            String[] parts = assignment.trim().split("=", -1);
            if (parts.length != 2 || parts[0].trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid assignment: " + assignment);
            }
            try {
                values.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Invalid assignment: " + assignment, exception);
            }
        }
        return values;
    }
}
