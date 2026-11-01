package ru.nsu.tsydenov.HashTable;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ChainedHashTable<K, V> implements HashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] buckets;
    private int size;
    private int modCount;

    @SuppressWarnings("unchecked")
    public ChainedHashTable() {
        buckets = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
    }

    @Override
    public void put(K key, V value) {
        Node<K, V> node = findNode(key);

        if (node != null) {
            node.updateValue(value);
            return;
        }

        int idx = getBucketIndex(key);
        buckets[idx] = new Node<>(key, value, buckets[idx]);

        size++;
        modCount++;

        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }
    }

    @Override
    public V get(K key) {
        Node<K, V> node = findNode(key);
        return node == null ? null : node.getValue();
    }

    @Override
    public void update(K key, V value) {
        Node<K, V> node = findNode(key);

        if (node == null) {
            throw new NoSuchElementException("Key not found: " + key);
        }

        node.updateValue(value);
    }

    @Override
    public V remove(K key) {
        int idx = getBucketIndex(key);
        Node<K, V> curr = buckets[idx];
        Node<K, V> prev = null;

        while (curr != null) {
            if (curr.hasKey(key)) {
                if (prev == null) {
                    buckets[idx] = curr.getNext();
                } else {
                    prev.setNext(curr.getNext());
                }

                size--;
                modCount++;

                return curr.getValue();
            }

            prev = curr;
            curr = curr.getNext();
        }

        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        int idx = getBucketIndex(key);
        Node<K, V> curr = buckets[idx];
        Node<K, V> prev = null;

        while (curr != null) {
            if (curr.hasKey(key) && curr.hasValue(value)) {
                if (prev == null) {
                    buckets[idx] = curr.getNext();
                } else {
                    prev.setNext(curr.getNext());
                }

                size--;
                modCount++;

                return true;
            }

            prev = curr;
            curr = curr.getNext();
        }

        return false;
    }

    @Override
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    int getModCount() {
        return modCount;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashTableIterator<>(this, buckets, modCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ChainedHashTable<?, ?> other)) {
            return false;
        }

        if (size != other.size) {
            return false;
        }

        for (Node<K, V> head : buckets) {
            Node<K, V> curr = head;

            while (curr != null) {
                Object key = curr.getKey();
                int hash = key.hashCode();
                int idx = (hash & 0x7fffffff) % other.buckets.length;

                Node<?, ?> otherNode = other.buckets[idx];

                while (otherNode != null
                        && !Objects.equals(key, otherNode.getKey())) {
                    otherNode = otherNode.getNext();
                }

                if (otherNode == null
                        || !Objects.equals(curr.getValue(), otherNode.getValue())) {
                    return false;
                }

                curr = curr.getNext();
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (Node<K, V> head : buckets) {
            Node<K, V> curr = head;

            while (curr != null) {
                hash += Objects.hashCode(curr.getKey())
                        ^ Objects.hashCode(curr.getValue());

                curr = curr.getNext();
            }
        }

        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");

        for (Node<K, V> head : buckets) {
            Node<K, V> curr = head;

            while (curr != null) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }

                sb.append(curr.getKey())
                        .append("=")
                        .append(curr.getValue());

                curr = curr.getNext();
            }
        }

        return sb.append("}").toString();
    }

    private Node<K, V> findNode(K key) {
        int idx = getBucketIndex(key);
        Node<K, V> curr = buckets[idx];

        while (curr != null) {
            if (curr.hasKey(key)) {
                return curr;
            }

            curr = curr.getNext();
        }

        return null;
    }

    private int getBucketIndex(K key) {
        int hash = Objects.requireNonNull(key).hashCode();
        return (hash & 0x7fffffff) % buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = buckets.length * 2;
        Node<K, V>[] newBuckets =
                (Node<K, V>[]) new Node[newCapacity];

        for (Node<K, V> head : buckets) {
            Node<K, V> curr = head;

            while (curr != null) {
                Node<K, V> next = curr.getNext();

                int hash = curr.getKey().hashCode();
                int newIdx = (hash & 0x7fffffff) % newCapacity;

                curr.setNext(newBuckets[newIdx]);
                newBuckets[newIdx] = curr;

                curr = next;
            }
        }

        buckets = newBuckets;
    }
}
