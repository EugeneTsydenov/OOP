package ru.nsu.tsydenov;

/**
 * Represents the rank of a playing card (from Two to Ace).
 * <p>
 * Each rank has a display name for printing and a base point value for Blackjack.
 * </p>
 */
public enum Rank {
    /** The Two card (worth 2 points). */
    TWO("Two", 2),

    /** The Three card (worth 3 points). */
    THREE("Three", 3),

    /** The Four card (worth 4 points). */
    FOUR("Four", 4),

    /** The Five card (worth 5 points). */
    FIVE("Five", 5),

    /** The Six card (worth 6 points). */
    SIX("Six", 6),

    /** The Seven card (worth 7 points). */
    SEVEN("Seven", 7),

    /** The Eight card (worth 8 points). */
    EIGHT("Eight", 8),

    /** The Nine card (worth 9 points). */
    NINE("Nine", 9),

    /** The Ten card (worth 10 points). */
    TEN("Ten", 10),

    /** The Jack card (worth 10 points). */
    JACK("Jack", 10),

    /** The Queen card (worth 10 points). */
    QUEEN("Queen", 10),

    /** The King card (worth 10 points). */
    KING("King", 10),

    /** The Ace card (worth 11 base points). */
    ACE("Ace", 11);

    /** The readable name of the card. */
    private final String displayName;

    /** The point value of the card. */
    private final int nominal;

    /**
     * Creates a card rank with a name and point value.
     *
     * @param displayName the readable name (e.g., "Ace")
     * @param nominal the point value
     */
    Rank(String displayName, int nominal) {
        this.displayName = displayName;
        this.nominal = nominal;
    }

    /**
     * Gets a card rank by its number index (0 to 12).
     *
     * @param n the index from 0 (TWO) to 12 (ACE)
     * @return the matching {@link Rank}
     * @throws IllegalArgumentException if index is outside 0-12
     */
    public static Rank fromIndex(int n) {
        return switch (n) {
            case 0 -> TWO;
            case 1 -> THREE;
            case 2 -> FOUR;
            case 3 -> FIVE;
            case 4 -> SIX;
            case 5 -> SEVEN;
            case 6 -> EIGHT;
            case 7 -> NINE;
            case 8 -> TEN;
            case 9 -> JACK;
            case 10 -> QUEEN;
            case 11 -> KING;
            case 12 -> ACE;
            default -> throw new IllegalArgumentException("invalid index card name");
        };
    }

    /**
     * Returns the readable name of the card.
     *
     * @return the display name (e.g., "King")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the point value of the card.
     *
     * @return the nominal point value
     */
    public int getNominal() {
        return nominal;
    }
}
