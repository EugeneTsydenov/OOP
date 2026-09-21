package ru.nsu.tsydenov;

public enum CardName {
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five",5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 10),
    QUEEN("Queen", 10),
    KING("King", 10),
    ACE("Ace", 11);

    private final String displayName;
    private final int nominal;

    CardName(String displayName, int nominal) {
        this.displayName = displayName;
        this.nominal = nominal;
    }

    public static CardName fromIndex(int n) {
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

    public String getDisplayName() {
        return displayName;
    }

    public int getNominal() {
        return nominal;
    }
}