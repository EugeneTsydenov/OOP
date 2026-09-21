package ru.nsu.tsydenov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SuitTest {
    @Test
    @DisplayName("Test: get suit by index 0 to 3")
    void testFromIndexValid() {
        assertEquals(Suit.CLUBS, Suit.fromIndex(0));
        assertEquals(Suit.DIAMONDS, Suit.fromIndex(1));
        assertEquals(Suit.HEARTS, Suit.fromIndex(2));
        assertEquals(Suit.SPADES, Suit.fromIndex(3));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, 4, 5, 100})
    @DisplayName("Test: wrong index throws exception")
    void testFromIndexInvalid(int invalidIndex) {
        assertThrows(IllegalArgumentException.class, () -> Suit.fromIndex(invalidIndex));
    }

    @Test
    @DisplayName("Test: get index from suit")
    void testToIndex() {
        assertEquals(0, Suit.SPADES.toIndex());
        assertEquals(1, Suit.CLUBS.toIndex());
        assertEquals(2, Suit.HEARTS.toIndex());
        assertEquals(3, Suit.DIAMONDS.toIndex());
    }

    @Test
    @DisplayName("Test: check display names")
    void testGetDisplayName() {
        assertEquals("Clubs", Suit.CLUBS.getDisplayName());
        assertEquals("Diamonds", Suit.DIAMONDS.getDisplayName());
        assertEquals("Hearts", Suit.HEARTS.getDisplayName());
        assertEquals("Spades", Suit.SPADES.getDisplayName());
    }

    @Test
    @DisplayName("Test: suit count is 4")
    void testEnumCount() {
        assertEquals(4, Suit.values().length);
    }
}