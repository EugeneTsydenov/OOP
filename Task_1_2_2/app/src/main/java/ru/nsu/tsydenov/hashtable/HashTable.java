package ru.nsu.tsydenov.hashtable;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Stores values by unique keys.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public interface HashTable<K, V> extends Iterable<Map.Entry<K, V>> {

    /**
     * Adds a pair or replaces the value of an existing key.
     *
     * @param key the key to add
     * @param value the value to store
     * @throws NullPointerException if the key is null
     */
    void put(K key, V value);

    /**
     * Finds a value by its key.
     *
     * @param key the key to find
     * @return the value, or null if the key is missing
     * @throws NullPointerException if the key is null
     */
    V get(K key);

    /**
     * Updates the value of an existing key.
     *
     * @param key the key to update
     * @param value the new value
     * @throws NullPointerException if the key is null
     * @throws NoSuchElementException if the key is missing
     */
    void update(K key, V value);

    /**
     * Removes a pair by its key.
     *
     * @param key the key to remove
     * @return the removed value, or null if the key is missing
     * @throws NullPointerException if the key is null
     */
    V remove(K key);

    /**
     * Removes a pair if its key and value match.
     *
     * @param key the key to remove
     * @param value the value to match
     * @return true if the pair was removed
     * @throws NullPointerException if the key is null
     */
    boolean remove(K key, V value);

    /**
     * Checks if a key exists.
     *
     * @param key the key to find
     * @return true if the key exists
     * @throws NullPointerException if the key is null
     */
    boolean containsKey(K key);

    /**
     * Returns the number of stored pairs.
     *
     * @return the number of pairs
     */
    int size();

    /**
     * Checks if the table is empty.
     *
     * @return true if the table is empty
     */
    boolean isEmpty();

    /**
     * Returns an iterator over the stored pairs.
     *
     * @return an iterator over the entries
     */
    @Override
    Iterator<Map.Entry<K, V>> iterator();
}
