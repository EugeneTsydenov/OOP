package ru.nsu.tsydenov;

/**
 * Represents a playing card suit in a card game.
 *
 * <p>
 * Each suit stores its human-readable {@code displayName} and provides utility
 * methods for converting between suit instances and integer indices.
 *
 * </p>
 */
public enum Suit {
    /**
     * The Clubs suit.
     */
    CLUBS("Clubs"),

    /**
     * The Diamonds suit.
     */
    DIAMONDS("Diamonds"),

    /**
     * The Hearts suit.
     */
    HEARTS("Hearts"),

    /**
     * The Spades suit.
     */
    SPADES("Spades");

    /** The human-readable name of the suit. */
    private final String displayName;

    /**
     * Constructs a {@code Suit} with the specified display name.
     *
     * @param displayName the string representation of the suit
     */
    Suit(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the human-readable display name of the suit.
     *
     * @return the suit name (e.g., "Clubs")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the {@code Suit} corresponding to the specified integer index.
     *
     * @param n the index: 0 for CLUBS, 1 for DIAMONDS, 2 for HEARTS, 3 for SPADES
     * @return the corresponding {@link Suit}
     * @throws IllegalArgumentException if {@code n} is outside the range [0, 3]
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
     * Converts the current suit into its designated integer index.
     *
     * @return the suit index: 0 for SPADES, 1 for CLUBS, 2 for HEARTS, 3 for DIAMONDS
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