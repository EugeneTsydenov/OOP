package ru.nsu.tsydenov.card;

/**
 * Suits of playing cards.
 *
 */
public enum Suit {
    /** Clubs suit. */
    CLUBS("Clubs"),

    /** Diamonds suit. */
    DIAMONDS("Diamonds"),

    /** Hearts suit. */
    HEARTS("Hearts"),

    /** Spades suit. */
    SPADES("Spades");

    /** The suit name. */
    private final String displayName;

    /**
     * Creates a suit.
     *
     * @param displayName suit name
     */
    Suit(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the suit name.
     *
     * @return suit name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns a suit by its index.
     *
     * @param n suit index from 0 to 3
     * @return the suit at the given index
     * @throws IllegalArgumentException if the index is invalid
     */
    public static Suit fromIndex(int n) {
        return switch (n) {
            case 0 -> CLUBS;
            case 1 -> DIAMONDS;
            case 2 -> HEARTS;
            case 3 -> SPADES;
            default -> throw new IllegalArgumentException("invalid index suit");
        };
    }

    /**
     * Returns the suit index.
     *
     * @return the suit index
     */
    public int toIndex() {
        return switch (this) {
            case SPADES -> 0;
            case CLUBS -> 1;
            case HEARTS -> 2;
            case DIAMONDS -> 3;
        };
    }
}