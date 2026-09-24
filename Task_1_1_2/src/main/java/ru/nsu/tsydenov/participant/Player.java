package ru.nsu.tsydenov.participant;

import ru.nsu.tsydenov.card.Card;
import ru.nsu.tsydenov.hand.Hand;

/**
 * A player with a hand of cards.
 *
 */
public class Player {
    /** The player's hand. */
    private final Hand hand;

    /** Creates a player with an empty hand. */
    public Player() {
        hand = new Hand();
    }

    /**
     * Sets two starting cards.
     *
     * @param card1 first card
     * @param card2 second card
     */
    public void hitStartHand(Card card1, Card card2) {
        hand.clear();
        hand.add(card1);
        hand.add(card2);
    }

    /**
     * Adds a card to the hand.
     *
     * @param card card to add
     */
    public void hit(Card card) {
        hand.add(card);
    }

    /**
     * Returns the number of points in the hand.
     *
     * @return hand points
     */
    public int totalHandNominal() {
        return hand.total();
    }

    /**
     * Returns the hand as text.
     *
     * @return hand text
     */
    public String handString() {
        return hand.toString();
    }

    /**
     * Returns true if the hand is Blackjack.
     *
     * @return true for Blackjack
     */
    public boolean isBlackjack() {
        return hand.size() == 2 && totalHandNominal() == 21;
    }

    /**
     * Returns true if the hand has more than 21 points.
     *
     * @return true if the hand is over 21 points
     */
    public boolean isBusted() {
        return totalHandNominal() > 21;
    }

    /** Removes all cards from the hand. */
    public void discardHand() {
        hand.clear();
    }

    /**
     * Returns the player's hand.
     *
     * @return player's hand
     */
    public Hand getHand() {
        return hand;
    }
}