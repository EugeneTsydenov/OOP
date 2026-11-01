
package ru.nsu.tsydenov.HashTable;

import java.util.AbstractMap;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Iterates over the pairs in a chained hash table.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
class HashTableIterator<K, V> implements Iterator<Map.Entry<K, V>> {

    private final ChainedHashTable<K, V> table;
    private final Node<K, V>[] buckets;
    private final int expectedModCount;

    private int bucketIndex;
    private Node<K, V> current;

    /**
     * Creates an iterator for a hash table.
     *
     * @param table the table to iterate over
     * @param buckets the bucket array
     * @param expectedModCount the current modification count
     */
    HashTableIterator(
            ChainedHashTable<K, V> table,
            Node<K, V>[] buckets,
            int expectedModCount) {

        this.table = table;
        this.buckets = buckets;
        this.expectedModCount = expectedModCount;

        moveToNextBucket();
    }

    /**
     * Finds the next non-empty bucket.
     */
    private void moveToNextBucket() {
        while (current == null && bucketIndex < buckets.length) {
            current = buckets[bucketIndex++];
        }
    }

    /**
     * Checks if the table has changed.
     *
     * @throws ConcurrentModificationException if the table was changed
     */
    private void checkForModification() {
        if (expectedModCount != table.getModCount()) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * Checks if another pair is available.
     *
     * @return true if another pair exists
     * @throws ConcurrentModificationException if the table was changed
     */
    @Override
    public boolean hasNext() {
        checkForModification();
        return current != null;
    }

    /**
     * Returns the next pair.
     *
     * @return the next entry
     * @throws NoSuchElementException if no entries are left
     * @throws ConcurrentModificationException if the table was changed
     */
    @Override
    public Map.Entry<K, V> next() {
        checkForModification();

        if (current == null) {
            throw new NoSuchElementException();
        }

        Map.Entry<K, V> entry = new AbstractMap.SimpleImmutableEntry<>(
                current.getKey(),
                current.getValue()
        );

        current = current.getNext();

        if (current == null) {
            moveToNextBucket();
        }

        return entry;
    }
}
