
package ru.nsu.tsydenov.HashTable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChainedHashTableTest {
    private HashTable<String, Integer> table;

    @BeforeEach
    void setUp() {
        table = new ChainedHashTable<>();
    }

    @Test
    void testNewTable_IsEmpty() {
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        assertEquals("{}", table.toString());
    }

    @Test
    void testPut_NewKey() {
        table.put("one", 1);

        assertEquals(1, table.get("one"));
        assertEquals(1, table.size());
        assertFalse(table.isEmpty());
    }

    @Test
    void testPut_ExistingKey() {
        table.put("one", 1);
        table.put("one", 2);

        assertEquals(2, table.get("one"));
        assertEquals(1, table.size());
    }

    @Test
    void testPut_NullKey() {
        assertThrows(NullPointerException.class, () -> {
            table.put(null, 1);
        });
    }

    @Test
    void testPut_NullValue() {
        table.put("one", null);

        assertTrue(table.containsKey("one"));
        assertNull(table.get("one"));
        assertEquals(1, table.size());
    }

    @Test
    void testGet_ExistingKey() {
        table.put("one", 1);
        table.put("two", 2);

        assertEquals(1, table.get("one"));
        assertEquals(2, table.get("two"));
    }

    @Test
    void testGet_MissingKey() {
        assertNull(table.get("missing"));
    }

    @Test
    void testGet_NullKey() {
        assertThrows(NullPointerException.class, () -> {
            table.get(null);
        });
    }

    @Test
    void testUpdate_ExistingKey() {
        table.put("one", 1);
        table.update("one", 10);

        assertEquals(10, table.get("one"));
        assertEquals(1, table.size());
    }

    @Test
    void testUpdate_MissingKey() {
        assertThrows(NoSuchElementException.class, () -> {
            table.update("missing", 10);
        });
    }

    @Test
    void testUpdate_NullKey() {
        assertThrows(NullPointerException.class, () -> {
            table.update(null, 10);
        });
    }

    @Test
    void testRemove_ExistingKey() {
        table.put("one", 1);

        assertEquals(1, table.remove("one"));
        assertFalse(table.containsKey("one"));
        assertTrue(table.isEmpty());
    }

    @Test
    void testRemove_MissingKey() {
        assertNull(table.remove("missing"));
        assertEquals(0, table.size());
    }

    @Test
    void testRemove_NullKey() {
        assertThrows(NullPointerException.class, () -> {
            table.remove(null);
        });
    }

    @Test
    void testRemove_MatchingPair() {
        table.put("one", 1);

        assertTrue(table.remove("one", 1));
        assertFalse(table.containsKey("one"));
        assertEquals(0, table.size());
    }

    @Test
    void testRemove_WrongValue() {
        table.put("one", 1);

        assertFalse(table.remove("one", 2));
        assertEquals(1, table.get("one"));
        assertEquals(1, table.size());
    }

    @Test
    void testRemove_MissingPair() {
        assertFalse(table.remove("missing", 1));
    }

    @Test
    void testRemove_NullPairKey() {
        assertThrows(NullPointerException.class, () -> {
            table.remove(null, 1);
        });
    }

    @Test
    void testRemove_NullValue() {
        table.put("one", null);

        assertTrue(table.remove("one", null));
        assertTrue(table.isEmpty());
    }

    @Test
    void testContainsKey_ExistingKey() {
        table.put("one", 1);

        assertTrue(table.containsKey("one"));
    }

    @Test
    void testContainsKey_MissingKey() {
        assertFalse(table.containsKey("missing"));
    }

    @Test
    void testContainsKey_NullKey() {
        assertThrows(NullPointerException.class, () -> {
            table.containsKey(null);
        });
    }

    @Test
    void testCollisions_AddAndFind() {
        HashTable<Integer, String> hashTable = new ChainedHashTable<>();

        hashTable.put(1, "one");
        hashTable.put(17, "seventeen");
        hashTable.put(33, "thirty-three");

        assertEquals("one", hashTable.get(1));
        assertEquals("seventeen", hashTable.get(17));
        assertEquals("thirty-three", hashTable.get(33));
        assertEquals(3, hashTable.size());
    }

    @Test
    void testCollisions_UpdateExistingKey() {
        HashTable<Integer, String> hashTable = new ChainedHashTable<>();

        hashTable.put(1, "one");
        hashTable.put(17, "seventeen");
        hashTable.put(33, "thirty-three");

        hashTable.put(17, "updated");

        assertEquals("updated", hashTable.get(17));
        assertEquals("one", hashTable.get(1));
        assertEquals(3, hashTable.size());
    }

    @Test
    void testCollisions_RemoveFirstNode() {
        HashTable<Integer, String> hashTable = new ChainedHashTable<>();

        hashTable.put(1, "one");
        hashTable.put(17, "seventeen");
        hashTable.put(33, "thirty-three");

        assertEquals("thirty-three", hashTable.remove(33));
        assertEquals("one", hashTable.get(1));
        assertEquals("seventeen", hashTable.get(17));
    }

    @Test
    void testCollisions_RemoveMiddleNode() {
        HashTable<Integer, String> hashTable = new ChainedHashTable<>();

        hashTable.put(1, "one");
        hashTable.put(17, "seventeen");
        hashTable.put(33, "thirty-three");

        assertEquals("seventeen", hashTable.remove(17));
        assertEquals("one", hashTable.get(1));
        assertEquals("thirty-three", hashTable.get(33));
    }

    @Test
    void testCollisions_RemoveLastNode() {
        HashTable<Integer, String> hashTable = new ChainedHashTable<>();

        hashTable.put(1, "one");
        hashTable.put(17, "seventeen");
        hashTable.put(33, "thirty-three");

        assertEquals("one", hashTable.remove(1));
        assertEquals("seventeen", hashTable.get(17));
        assertEquals("thirty-three", hashTable.get(33));
    }

    @Test
    void testCollisions_RemoveMatchingPair() {
        HashTable<Integer, String> hashTable = new ChainedHashTable<>();

        hashTable.put(1, "one");
        hashTable.put(17, "seventeen");
        hashTable.put(33, "thirty-three");

        assertTrue(hashTable.remove(17, "seventeen"));
        assertEquals("one", hashTable.get(1));
        assertEquals("thirty-three", hashTable.get(33));
        assertEquals(2, hashTable.size());
    }

    @Test
    void testResize_KeepsAllEntries() {
        HashTable<Integer, Integer> hashTable = new ChainedHashTable<>();

        for (int i = 0; i < 100; i++) {
            hashTable.put(i, i * 10);
        }

        assertEquals(100, hashTable.size());

        for (int i = 0; i < 100; i++) {
            assertEquals(i * 10, hashTable.get(i));
        }
    }

    @Test
    void testResize_RemoveEntries() {
        HashTable<Integer, Integer> hashTable = new ChainedHashTable<>();

        for (int i = 0; i < 50; i++) {
            hashTable.put(i, i);
        }

        for (int i = 0; i < 25; i++) {
            assertEquals(i, hashTable.remove(i));
        }

        assertEquals(25, hashTable.size());

        for (int i = 25; i < 50; i++) {
            assertEquals(i, hashTable.get(i));
        }
    }

    @Test
    void testIterator_EmptyTable() {
        Iterator<Map.Entry<String, Integer>> iterator = table.iterator();

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testIterator_AllEntries() {
        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);

        Map<String, Integer> entries = new HashMap<>();

        for (Map.Entry<String, Integer> entry : table) {
            entries.put(entry.getKey(), entry.getValue());
        }

        assertEquals(3, entries.size());
        assertEquals(1, entries.get("one"));
        assertEquals(2, entries.get("two"));
        assertEquals(3, entries.get("three"));
    }

    @Test
    void testIterator_Collisions() {
        HashTable<Integer, Integer> hashTable = new ChainedHashTable<>();

        hashTable.put(1, 10);
        hashTable.put(17, 20);
        hashTable.put(33, 30);

        int count = 0;
        int sum = 0;

        for (Map.Entry<Integer, Integer> entry : hashTable) {
            count++;
            sum += entry.getValue();
        }

        assertEquals(3, count);
        assertEquals(60, sum);
    }

    @Test
    void testIterator_NextAfterEnd() {
        table.put("one", 1);

        Iterator<Map.Entry<String, Integer>> iterator = table.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next().getValue());
        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testIterator_AddDuringIteration() {
        table.put("one", 1);

        Iterator<Map.Entry<String, Integer>> iterator = table.iterator();
        table.put("two", 2);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testIterator_RemoveDuringIteration() {
        table.put("one", 1);
        table.put("two", 2);

        Iterator<Map.Entry<String, Integer>> iterator = table.iterator();
        table.remove("one");

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    @Test
    void testIterator_UpdateDuringIteration() {
        table.put("one", 1);

        Iterator<Map.Entry<String, Integer>> iterator = table.iterator();
        table.update("one", 10);

        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next().getValue());
    }

    @Test
    void testIterator_ReplaceDuringIteration() {
        table.put("one", 1);

        Iterator<Map.Entry<String, Integer>> iterator = table.iterator();
        table.put("one", 10);

        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next().getValue());
    }

    @Test
    void testIterator_RemoveUnsupported() {
        table.put("one", 1);

        Iterator<Map.Entry<String, Integer>> iterator = table.iterator();

        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    void testIterator_EntryIsImmutable() {
        table.put("one", 1);

        Map.Entry<String, Integer> entry = table.iterator().next();

        assertThrows(UnsupportedOperationException.class, () -> {
            entry.setValue(10);
        });

        assertEquals(1, table.get("one"));
    }

    @Test
    void testEquals_SameTable() {
        table.put("one", 1);

        assertEquals(table, table);
    }

    @Test
    void testEquals_SameEntries() {
        HashTable<String, Integer> other = new ChainedHashTable<>();

        table.put("one", 1);
        table.put("two", 2);

        other.put("two", 2);
        other.put("one", 1);

        assertEquals(table, other);
        assertEquals(table.hashCode(), other.hashCode());
    }

    @Test
    void testEquals_DifferentValues() {
        HashTable<String, Integer> other = new ChainedHashTable<>();

        table.put("one", 1);
        other.put("one", 2);

        assertNotEquals(table, other);
    }

    @Test
    void testEquals_DifferentKeys() {
        HashTable<String, Integer> other = new ChainedHashTable<>();

        table.put("one", 1);
        other.put("two", 1);

        assertNotEquals(table, other);
    }

    @Test
    void testEquals_DifferentSizes() {
        HashTable<String, Integer> other = new ChainedHashTable<>();

        table.put("one", 1);
        table.put("two", 2);
        other.put("one", 1);

        assertNotEquals(table, other);
    }

    @Test
    void testEquals_NullAndOtherType() {
        assertNotEquals(null, table);
        assertNotEquals("text", table);
    }

    @Test
    void testEquals_EmptyTables() {
        HashTable<String, Integer> other = new ChainedHashTable<>();

        assertEquals(table, other);
        assertEquals(table.hashCode(), other.hashCode());
    }

    @Test
    void testEquals_NullValues() {
        HashTable<String, Integer> other = new ChainedHashTable<>();

        table.put("one", null);
        other.put("one", null);

        assertEquals(table, other);

        other.update("one", 1);

        assertNotEquals(table, other);
    }

    @Test
    void testEquals_AfterResize() {
        HashTable<Integer, Integer> first = new ChainedHashTable<>();
        HashTable<Integer, Integer> second = new ChainedHashTable<>();

        for (int i = 0; i < 50; i++) {
            first.put(i, i);
        }

        for (int i = 49; i >= 0; i--) {
            second.put(i, i);
        }

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void testHashCode_DifferentValues() {
        table.put("one", 1);
        int oldHash = table.hashCode();

        table.update("one", 2);

        assertNotEquals(oldHash, table.hashCode());
    }

    @Test
    void testToString_EmptyTable() {
        assertEquals("{}", table.toString());
    }

    @Test
    void testToString_SingleEntry() {
        table.put("one", 1);

        assertEquals("{one=1}", table.toString());
    }

    @Test
    void testToString_MultipleEntries() {
        table.put("one", 1);
        table.put("two", 2);

        String result = table.toString();

        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
        assertTrue(result.contains("one=1"));
        assertTrue(result.contains("two=2"));
    }
}
