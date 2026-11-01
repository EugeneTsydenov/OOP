package ru.nsu.tsydenov.hashtable;

import java.util.Objects;

/**
 * Stores a key-value pair and a link to the next node.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
class Node<K, V> {

    private final K key;
    private V value;
    private Node<K, V> next;

    /**
     * Creates a node with a key, value and next node.
     *
     * @param key the node key
     * @param value the node value
     * @param next the next node
     */
    Node(K key, V value, Node<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    boolean hasKey(K key) {
        return Objects.equals(this.key, key);
    }

    boolean hasValue(V value) {
        return Objects.equals(this.value, value);
    }

    void updateValue(V value) {
        this.value = value;
    }

    K getKey() {
        return key;
    }

    V getValue() {
        return value;
    }

    Node<K, V> getNext() {
        return next;
    }

    void setNext(Node<K, V> next) {
        this.next = next;
    }
}
