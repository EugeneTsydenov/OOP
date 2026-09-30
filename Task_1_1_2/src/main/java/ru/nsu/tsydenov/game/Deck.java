package ru.nsu.tsydenov.game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import ru.nsu.tsydenov.card.Card;
import ru.nsu.tsydenov.card.Rank;
import ru.nsu.tsydenov.card.Suit;

/**
 * A deck containing one or more sets of 52 cards.
 *
 */
public class Deck {
    /** All cards in the deck. */
    private final List<Card> cards;

    /** The index of the next card. */
    private int topIdx;

    /**
     * Creates a deck.
     *
     * @param n number of sets of cards
     * @throws IllegalArgumentException if n is less than 1
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

    /** Adds one set of 52 cards to the deck. */
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

    /** Shuffles the cards that were not drawn yet. */
    public void shuffle() {
        for (int i = cards.size() - 1; i > topIdx; i--) {
            int j = ThreadLocalRandom.current().nextInt(topIdx, i + 1);
            swap(i, j);
        }
    }

    /**
     * Swaps two cards.
     *
     * @param i index of the first card
     * @param j index of the second card
     */
    private void swap(int i, int j) {
        Card temp = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, temp);
    }

    /**
     * Takes the next card.
     *
     * @return the next card from the deck
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

    /** Starts the deck again and shuffles all cards. */
    public void resetAndShuffle() {
        topIdx = 0;
        shuffle();
    }
}