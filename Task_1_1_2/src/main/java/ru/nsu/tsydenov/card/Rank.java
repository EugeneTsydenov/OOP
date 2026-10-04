package ru.nsu.tsydenov.card;

/**
 * Card ranks from Two to Ace.
 *
 */
public enum Rank {
    /** Two card. */
    TWO("Two", 2),

    /** Three card. */
    THREE("Three", 3),

    /** Four card. */
    FOUR("Four", 4),

    /** Five card. */
    FIVE("Five", 5),

    /** Six card. */
    SIX("Six", 6),

    /** Seven card. */
    SEVEN("Seven", 7),

    /** Eight card. */
    EIGHT("Eight", 8),

    /** Nine card. */
    NINE("Nine", 9),

    /** Ten card. */
    TEN("Ten", 10),

    /** Jack card. */
    JACK("Jack", 10),

    /** Queen card. */
    QUEEN("Queen", 10),

    /** King card. */
    KING("King", 10),

    /** Ace card. */
    ACE("Ace", 11);

    /** The card name. */
    private final String displayName;

    /** The card value. */
    private final int nominal;

    /**
     * Creates a card rank.
     *
     * @param displayName card name
     * @param nominal card value
     */
    Rank(String displayName, int nominal) {
        this.displayName = displayName;
        this.nominal = nominal;
    }

    /**
     * Returns a rank by its index.
     *
     * @param n rank index from 0 to 12
     * @return the rank at the given index
     * @throws IllegalArgumentException if the index is invalid
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
     * Returns the card name.
     *
     * @return card name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the card value.
     *
     * @return card value
     */
    public int getNominal() {
        return nominal;
    }
}
