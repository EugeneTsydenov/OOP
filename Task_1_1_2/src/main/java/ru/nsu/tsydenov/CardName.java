package ru.nsu.tsydenov;

public enum CardName {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE(11);

    private final int nominal;

    CardName(int nominal) {
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

    public int getNominal() {
        return nominal;
    }
}