package ru.nsu.tsydenov;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RankTest {
    @Test
    @DisplayName("Test: get rank by index 0 to 12")
    void testFromIndexValid() {
        assertAll(
                () -> assertEquals(Rank.TWO, Rank.fromIndex(0)),
                () -> assertEquals(Rank.THREE, Rank.fromIndex(1)),
                () -> assertEquals(Rank.FOUR, Rank.fromIndex(2)),
                () -> assertEquals(Rank.FIVE, Rank.fromIndex(3)),
                () -> assertEquals(Rank.SIX, Rank.fromIndex(4)),
                () -> assertEquals(Rank.SEVEN, Rank.fromIndex(5)),
                () -> assertEquals(Rank.EIGHT, Rank.fromIndex(6)),
                () -> assertEquals(Rank.NINE, Rank.fromIndex(7)),
                () -> assertEquals(Rank.TEN, Rank.fromIndex(8)),
                () -> assertEquals(Rank.JACK, Rank.fromIndex(9)),
                () -> assertEquals(Rank.QUEEN, Rank.fromIndex(10)),
                () -> assertEquals(Rank.KING, Rank.fromIndex(11)),
                () -> assertEquals(Rank.ACE, Rank.fromIndex(12))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, 13, 14, 100})
    @DisplayName("Test: wrong index throws exception")
    void testFromIndexInvalid(int invalidIndex) {
        assertThrows(IllegalArgumentException.class, () -> Rank.fromIndex(invalidIndex));
    }

    @Test
    @DisplayName("Test: check card point values")
    void testGetNominal() {
        assertAll(
                () -> assertEquals(2, Rank.TWO.getNominal()),
                () -> assertEquals(3, Rank.THREE.getNominal()),
                () -> assertEquals(4, Rank.FOUR.getNominal()),
                () -> assertEquals(5, Rank.FIVE.getNominal()),
                () -> assertEquals(6, Rank.SIX.getNominal()),
                () -> assertEquals(7, Rank.SEVEN.getNominal()),
                () -> assertEquals(8, Rank.EIGHT.getNominal()),
                () -> assertEquals(9, Rank.NINE.getNominal()),
                () -> assertEquals(10, Rank.TEN.getNominal()),
                () -> assertEquals(10, Rank.JACK.getNominal()),
                () -> assertEquals(10, Rank.QUEEN.getNominal()),
                () -> assertEquals(10, Rank.KING.getNominal()),
                () -> assertEquals(11, Rank.ACE.getNominal())
        );
    }

    @Test
    @DisplayName("Test: check display names")
    void testGetDisplayName() {
        assertAll(
                () -> assertEquals("Two", Rank.TWO.getDisplayName()),
                () -> assertEquals("Jack", Rank.JACK.getDisplayName()),
                () -> assertEquals("Ace", Rank.ACE.getDisplayName())
        );
    }

    @Test
    @DisplayName("Test: rank count is 13")
    void testEnumCount() {
        assertEquals(13, Rank.values().length);
    }
}