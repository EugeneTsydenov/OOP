package ru.nsu.tsydenov.HashTable;

import java.util.Iterator;
import java.util.Map;

/**
 * A hash table that stores values by their keys.
 * Each key is unique and is used to find its value.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public interface HashTable<K, V> extends Iterable<Map.Entry<K, V>> {

    /**
     * Adds a new key-value pair to the table.
     * If the key already exists, its value is replaced.
     *
     * @param key the key to add
     * @param value the value to store
     */
    void put(K key, V value);

    /**
     * Finds a value by its key.
     *
     * @param key the key to search for
     * @return the value, or null if the key does not exist
     */
    V get(K key);

    /**
     * Updates the value of an existing key.
     *
     * @param key the key to update
     * @param value the new value
     * @throws java.util.NoSuchElementException if the key does not exist
     */
    void update(K key, V value);

    /**
     * Removes a key and its value from the table.
     *
     * @param key the key to remove
     * @return the removed value, or null if the key does not exist
     */
    V remove(K key);

    /**
     * Removes a pair if both the key and value match.
     *
     * @param key the key to remove
     * @param value the value to match
     * @return true if the pair was removed, false otherwise
     */
    boolean remove(K key, V value);

    /**
     * Checks if the table contains a given key.
     *
     * @param key the key to search for
     * @return true if the key exists, false otherwise
     */
    boolean containsKey(K key);

    /**
     * Returns the number of pairs stored in the table.
     *
     * @return the number of pairs
     */
    int size();

    /**
     * Checks if the table has no elements.
     *
     * @return true if the table is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns an iterator over the key-value pairs.
     * The iterator detects changes to the table during iteration.
     *
     * @return an iterator over the pairs
     * @throws java.util.ConcurrentModificationException if the table
     *         is structurally changed during iteration
     */
    @Override
    Iterator<Map.Entry<K, V>> iterator();
}
