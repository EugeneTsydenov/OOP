package ru.nsu.tsydenov;

import java.util.ArrayList;

/**
 * Represents a player in a Blackjack game.
 *
 * <p>
 * Manages the player's cards, hand calculations (including Ace point adjustments),
 * and win/bust checks.
 * </p>
 *
 */
public class Player {
    /** Stores the initial two cards dealt at the start of a round. */
    protected final Card[] startHand = new Card[2];

    /** The list of all cards currently held by the player. */
    protected final ArrayList<Card> hand;

    /**
     * Creates a new player with an empty hand.
     */
    public Player() {
        hand = new ArrayList<Card>();
    }

    /**
     * Deals the starting two cards to the player.
     *
     * @param card1 the first card dealt
     * @param card2 the second card dealt
     */
    public void hitStartHand(Card card1, Card card2) {
        startHand[0] = card1;
        startHand[1] = card2;
        hand.add(card1);
        hand.add(card2);
    }

    /**
     * Adds a single card to the player's hand (taking a "hit").
     *
     * @param card the card to add
     */
    public void hit(Card card) {
        hand.add(card);
    }

    /**
     * Calculates the total point value of the current hand.
     *
     * <p>
     * Automatically lowers Aces from 11 points to 1 point if the total points exceed 21.
     * </p>
     *
     * @return the calculated total hand value
     */
    public int totalHandNominal() {
        int totalNominal = 0;
        int aceCount = 0;

        for (Card card : hand) {
            int val = card.getNominal();
            totalNominal += val;
            if (card.getName() == Rank.ACE) {
                aceCount++;
            }
        }

        while (totalNominal > 21 && aceCount > 0) {
            totalNominal -= 10;
            aceCount--;
        }

        return totalNominal;
    }

    /**
     * Returns a string representation of the current hand.
     *
     * @return a string containing the list of cards
     */
    public String handString() {
        return hand.toString();
    }

    /**
     * Checks if the player has a natural Blackjack (2 cards totaling exactly 21 points).
     *
     * @return {@code true} if the player has a Blackjack, {@code false} otherwise
     */
    public boolean isBlackjack() {
        return hand.size() == 2 && totalHandNominal() == 21;
    }

    /**
     * Checks if the total value of the hand exceeds 21 points.
     *
     * @return {@code true} if the player has busted, {@code false} otherwise
     */
    public boolean isBusted() {
        return totalHandNominal() > 21;
    }

    /**
     * Clears all cards from the player's hand to prepare for a new round.
     */
    public void discardHand() {
        hand.clear();
    }
}