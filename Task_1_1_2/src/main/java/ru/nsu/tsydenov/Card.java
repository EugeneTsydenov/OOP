package ru.nsu.tsydenov;

/**
 * Represents a single playing card with a suit and a rank.
 */
public class Card {
    /** The suit of the card (e.g., Hearts, Spades). */
    private final Suit suit;

    /** The rank of the card (e.g., Ace, King, Two). */
    private final Rank rank;

    /**
     * Creates a new card with the given suit and rank.
     *
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns the base point value of the card.
     *
     * @return the nominal point value
     */
    public int getNominal() {
        return rank.getNominal();
    }

    /**
     * Returns the rank object of the card.
     *
     * @return the {@link Rank} of this card
     */
    public Rank getName() {
        return rank;
    }

    /**
     * Returns a readable text version of the card.
     *
     * @return a formatted string like "Ace of Spades (11)"
     */
    @Override
    public String toString() {
        return rank.getDisplayName()
                + " of " + suit.getDisplayName() + " (" + rank.getNominal() + ")";
    }
}