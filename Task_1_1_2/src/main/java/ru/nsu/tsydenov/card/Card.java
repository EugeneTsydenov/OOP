package ru.nsu.tsydenov.card;

/**
 * A playing card with a suit and a rank.
 *
 */
public class Card {
    /** The suit of the card. */
    private final Suit suit;

    /** The rank of the card. */
    private final Rank rank;

    /**
     * Creates a card.
     *
     * @param suit card suit
     * @param rank card rank
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns the card value.
     *
     * @return card value
     */
    public int getNominal() {
        return rank.getNominal();
    }

    /**
     * Returns the card rank.
     *
     * @return card rank
     */
    public Rank getName() {
        return rank;
    }

    /** Returns the card as text("Spades of Ace (11)"). */
    @Override
    public String toString() {
        return rank.getDisplayName()
                + " of " + suit.getDisplayName() + " (" + rank.getNominal() + ")";
    }
}