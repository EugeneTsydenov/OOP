package ru.nsu.tsydenov;

import java.util.Map;

import ru.nsu.tsydenov.HashTable.ChainedHashTable;
import ru.nsu.tsydenov.HashTable.HashTable;

public class Main {
    /**
    * Runs the application.
    *
    * @param args command-line arguments
    */
    public static void main(String[] args) {
        try {
            HashTable<String, Number> hashTable = new ChainedHashTable<>();

            hashTable.put("one", 1);
            hashTable.put("two", 2);
            hashTable.put("three", 3);

            hashTable.update("one", 1.0);
            System.out.println(hashTable.get("one"));

            for (Map.Entry<String, Number> entry : hashTable) {
                System.out.printf("\n%s = %s%n", entry.getKey(), entry.getValue());
            }

        } catch (Exception e) {
            System.out.printf("something went wront: %w", e);
        }
    }
}
