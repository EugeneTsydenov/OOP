package ru.nsu.tsydenov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {
    private final List<Card> cards;
    private int topIdx;

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

    private void initDeck() {
        for (int i = 0; i < 13; i++) {
            CardName cardName = CardName.fromIndex(i);
            for (int j = 0; j < 4; j++) {
                Suit suit = Suit.fromIndex(j);
                Card card = new Card(suit, cardName);
                cards.add(card);
            }
        }
    }

    public void shuffle() {
        for (int i = cards.size() - 1; i > topIdx; i--) {
            int j = ThreadLocalRandom.current().nextInt(topIdx, i + 1);
            swap(i, j);
        }
    }

    private void swap(int i, int j) {
        Card temp = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, temp);
    }

    public Card takeCard() {
        if (topIdx >= cards.size()) {
            topIdx = 0;
            shuffle();
        }

        Card card = cards.get(topIdx);
        topIdx++;
        return card;
    }

    public void resetAndShuffle() {
        topIdx = 0;
        shuffle();
    }
}
