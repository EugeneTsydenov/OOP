package ru.nsu.tsydenov.hand;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.tsydenov.card.Card;
import ru.nsu.tsydenov.card.Rank;

/**
 * A set of cards held by a player.
 *
 */
public class Hand {
    private final List<Card> cards = new ArrayList<>();

    /**
     * Adds a card to the hand.
     *
     * @param card card to add
     */
    public void add(Card card) {
        cards.add(card);
    }

    /** Removes all cards from the hand. */
    public void clear() {
        cards.clear();
    }

    /**
     * Returns the number of cards in the hand.
     *
     * @return number of cards
     */
    public int size() {
        return cards.size();
    }

    /**
     * Returns a card by its index.
     *
     * @param index card index
     * @return the card at the given index
     */
    public Card get(int index) {
        return cards.get(index);
    }

    /**
     * Returns the number of points in the hand.
     * An ace is worth 11 points unless this makes the total greater than 21.
     * In that case, the ace is worth 1 point.
     *
     * @return hand points
     */
    public int total() {
        int total = 0;
        int aces = 0;
        for (Card card : cards) {
            total += card.getNominal();
            if (card.getName() == Rank.ACE) {
                aces++;
            }
        }
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }
        return total;
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
