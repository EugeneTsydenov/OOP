package ru.nsu.tsydenov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a deck containing one or more standard 52-card decks.
 * <p>
 * Tracks drawn cards using a pointer ({@code topIdx}) and allows shuffling and drawing cards.
 * </p>
 */
public class Deck {
    /** The list of all cards in the shoe. */
    private final List<Card> cards;

    /** The index of the top (next) card to draw. */
    private int topIdx;

    /**
     * Creates a new deck shoe containing {@code n} standard 52-card decks.
     *
     * @param n the number of 52-card decks to include (must be at least 1)
     * @throws IllegalArgumentException if {@code n} is less than 1
     */
    public Deck(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Number of decks must be at least 1");
        }

        cards = new ArrayList<Card>(n * 52);
        topIdx = 0;

        while (n != 0) {
            initDeck();
            n--;
        }
    }

    /**
     * Helper method that adds one standard set of 52 cards to the shoe.
     */
    private void initDeck() {
        for (int i = 0; i < 13; i++) {
            Rank rank = Rank.fromIndex(i);
            for (int j = 0; j < 4; j++) {
                Suit suit = Suit.fromIndex(j);
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
    }

    /**
     * Shuffles only the remaining (undrawn) cards in the shoe using Durstenfeld/Fisher-Yates shuffle.
     */
    public void shuffle() {
        for (int i = cards.size() - 1; i > topIdx; i--) {
            int j = ThreadLocalRandom.current().nextInt(topIdx, i + 1);
            swap(i, j);
        }
    }

    /**
     * Swaps two cards in the shoe by their indices.
     *
     * @param i the index of the first card
     * @param j the index of the second card
     */
    private void swap(int i, int j) {
        Card temp = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, temp);
    }

    /**
     * Draws the top card from the deck.
     * <p>
     * If all cards have been drawn, it automatically resets the deck pointer to zero and reshuffles.
     * </p>
     *
     * @return the drawn {@link Card}
     */
    public Card takeCard() {
        if (topIdx >= cards.size()) {
            topIdx = 0;
            shuffle();
        }

        Card card = cards.get(topIdx);
        topIdx++;
        return card;
    }

    /**
     * Resets the top card pointer to the start and reshuffles all cards.
     */
    public void resetAndShuffle() {
        topIdx = 0;
        shuffle();
    }
}