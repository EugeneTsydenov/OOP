package ru.nsu.tsydenov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class CardTest {

    @Test
    @DisplayName("Test: get nominal value of card")
    void testGetNominal() {
        Card aceOfSpades = new Card(Suit.SPADES, Rank.ACE);
        Card tenOfClubs = new Card(Suit.CLUBS, Rank.TEN);

        assertEquals(11, aceOfSpades.getNominal());
        assertEquals(10, tenOfClubs.getNominal());
    }

    @Test
    @DisplayName("Test: get rank object from card")
    void testGetName() {
        Card kingOfHearts = new Card(Suit.HEARTS, Rank.KING);

        assertEquals(Rank.KING, kingOfHearts.getName());
    }

    @Test
    @DisplayName("Test: toString formatted string output")
    void testToStringFormat() {
        Card aceOfSpades = new Card(Suit.SPADES, Rank.ACE);
        Card fiveOfDiamonds = new Card(Suit.DIAMONDS, Rank.FIVE);

        assertEquals("Ace of Spades (11)", aceOfSpades.toString());
        assertEquals("Five of Diamonds (5)", fiveOfDiamonds.toString());
    }
}