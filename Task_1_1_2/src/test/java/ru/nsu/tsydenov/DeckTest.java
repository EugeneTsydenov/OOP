package ru.nsu.tsydenov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    @DisplayName("Test: create deck with valid size")
    void testValidDeckCreation() {
        Deck deck = new Deck(1);
        assertNotNull(deck);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    @DisplayName("Test: invalid deck size throws exception")
    void testInvalidDeckCreationThrowsException(int invalidN) {
        assertThrows(IllegalArgumentException.class, () -> new Deck(invalidN));
    }

    @Test
    @DisplayName("Test: take card returns a non-null card")
    void testTakeCardReturnsCard() {
        Deck deck = new Deck(1);
        Card card = deck.takeCard();
        assertNotNull(card);
    }

    @Test
    @DisplayName("Test: draw all 52 cards without error")
    void testDrawFullSingleDeck() {
        Deck deck = new Deck(1);
        for (int i = 0; i < 52; i++) {
            assertNotNull(deck.takeCard());
        }
    }

    @Test
    @DisplayName("Test: auto reset and shuffle when drawing beyond deck size")
    void testAutoResetAndShuffleWhenEmpty() {
        Deck deck = new Deck(1);
        for (int i = 0; i < 52; i++) {
            deck.takeCard();
        }

        assertDoesNotThrow(deck::takeCard);
    }

    @Test
    @DisplayName("Test: reset and shuffle manual invocation")
    void testResetAndShuffleManual() {
        Deck deck = new Deck(1);
        deck.takeCard();
        deck.takeCard();

        assertDoesNotThrow(deck::resetAndShuffle);
        assertNotNull(deck.takeCard());
    }
}